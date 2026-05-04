package com.allyssonmast.hamburgueria.service.impl;

import com.allyssonmast.hamburgueria.dto.PagamentoRequestDTO;
import com.allyssonmast.hamburgueria.dto.PagamentoResponseDTO;
import com.allyssonmast.hamburgueria.enums.StatusPagamento;
import com.allyssonmast.hamburgueria.enums.TipoPagamento;
import com.allyssonmast.hamburgueria.exception.NotFoundException;
import com.allyssonmast.hamburgueria.exception.PaymentException;
import com.allyssonmast.hamburgueria.model.*;
import com.allyssonmast.hamburgueria.model.audit.AuditLog;
import com.allyssonmast.hamburgueria.repository.audit.AuditLogRepository;
import com.allyssonmast.hamburgueria.repository.primary.CategoriaRepository;
import com.allyssonmast.hamburgueria.repository.primary.ClienteRepository;
import com.allyssonmast.hamburgueria.repository.primary.PagamentoRepository;
import com.allyssonmast.hamburgueria.service.PagamentoService;
import com.allyssonmast.hamburgueria.strategy.factory.PagamentoStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Qualifier("simples")
public class PagamentoServiceSimples implements PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AuditLogRepository auditRepository;

    @Autowired
    private PagamentoStrategyFactory factory;

    @Override
    public PagamentoResponseDTO processar(PagamentoRequestDTO dto) {

        validarValor(dto.getValor());

        Cliente cliente = clienteRepository.findById(dto.getClienteId()).orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

        List<CategoriaPagamento> categorias = categoriaRepository.findAllById(dto.getCategoriasIds());

        Pagamento pagamento = new Pagamento();

        pagamento.setValor(dto.getValor());
        pagamento.setTipo(dto.getTipo());
        pagamento.setDescricao(dto.getDescricao());

        pagamento.setCliente(cliente);

        pagamento.setCategorias(categorias);

        StatusPagamento status = factory.getStrategy(dto.getTipo()).processar(pagamento);

        pagamento.setStatus(status);

        Pagamento salvo = repository.save(pagamento);

        salvarLogAuditoria("CRIACAO_PAGAMENTO", salvo.getId());

        return toDTO(salvo);
    }

    @Override
    public PagamentoResponseDTO atualizar(Long id, PagamentoRequestDTO dto) {

        validarValor(dto.getValor());

        Pagamento pagamento = repository.findById(id).orElseThrow(() -> new NotFoundException("Pagamento não encontrado"));

        Cliente cliente = clienteRepository.findById(dto.getClienteId()).orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

        List<CategoriaPagamento> categorias = categoriaRepository.findAllById(dto.getCategoriasIds());

        pagamento.setValor(dto.getValor());

        pagamento.setTipo(dto.getTipo());

        pagamento.setDescricao(dto.getDescricao());

        pagamento.setCliente(cliente);

        pagamento.setCategorias(categorias);

        StatusPagamento status = factory.getStrategy(dto.getTipo()).processar(pagamento);

        pagamento.setStatus(status);

        Pagamento atualizado = repository.save(pagamento);

        salvarLogAuditoria("ATUALIZACAO_PAGAMENTO", atualizado.getId());

        return toDTO(atualizado);
    }

    @Override
    public List<PagamentoResponseDTO> listar() {

        return repository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public PagamentoResponseDTO buscarPorId(Long id) {

        Pagamento pagamento = repository.buscarComCliente(id).orElseThrow(() -> new NotFoundException("Pagamento não encontrado"));

        return toDTO(pagamento);
    }

    @Override
    public List<PagamentoResponseDTO> buscarPorTipo(TipoPagamento tipo) {

        return repository.findByTipo(tipo).stream().map(this::toDTO).toList();
    }

    @Override
    public void deletar(Long id) {

        if (!repository.existsById(id)) {
            throw new NotFoundException("Pagamento não encontrado");
        }

        repository.deleteById(id);

        salvarLogAuditoria("DELECAO_PAGAMENTO", id);
    }

    private void validarValor(double valor) {

        if (valor <= 0) {
            throw new PaymentException("Valor inválido");
        }
    }

    private void salvarLogAuditoria(String acao, Long recursoId) {

        AuditLog log = new AuditLog();

        log.setAcao(acao);

        log.setRecursoId(recursoId);

        log.setDataHora(LocalDateTime.now());

        auditRepository.save(log);
    }

    private PagamentoResponseDTO toDTO(Pagamento pagamento) {

        PagamentoResponseDTO dto = new PagamentoResponseDTO();

        dto.setId(pagamento.getId());

        dto.setValor(pagamento.getValor());

        dto.setTipo(pagamento.getTipo());

        dto.setStatus(pagamento.getStatus());

        return dto;
    }
}
