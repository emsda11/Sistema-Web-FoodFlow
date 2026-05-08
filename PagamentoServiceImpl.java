package com.allyssonmast.hamburgueria.service.impl;

import com.allyssonmast.hamburgueria.dto.PagamentoRequestDTO;
import com.allyssonmast.hamburgueria.dto.PagamentoResponseDTO;
import com.allyssonmast.hamburgueria.exception.NotFoundException;
import com.allyssonmast.hamburgueria.model.Pagamento;
import com.allyssonmast.hamburgueria.model.Pedido;
import com.allyssonmast.hamburgueria.repository.primary.PagamentoRepository;
import com.allyssonmast.hamburgueria.repository.primary.PedidoRepository;
import com.allyssonmast.hamburgueria.service.AuditService;
import com.allyssonmast.hamburgueria.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagamentoServiceImpl implements PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private AuditService auditService;

    @Override
    public PagamentoResponseDTO realizarPagamento(PagamentoRequestDTO dto) {

        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        Pagamento pagamento = new Pagamento();

        pagamento.setPedido(pedido);
        pagamento.setMetodo(dto.getMetodo());
        pagamento.setValor(pedido.getValorTotal());
        pagamento.setDataPagamento(LocalDateTime.now());
        pagamento.setStatus("PROCESSADO");

        Pagamento salvo = pagamentoRepository.save(pagamento);

        auditService.registrar("Pagamento", salvo.getId(), "CREATE");

        return toDTO(salvo);
    }

    @Override
    public List<PagamentoResponseDTO> listar() {
        return pagamentoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public PagamentoResponseDTO buscarPorId(Long id) {

        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pagamento não encontrado"));

        return toDTO(pagamento);
    }

    @Override
    public List<PagamentoResponseDTO> listarPorCliente(Long clienteId) {

        return pagamentoRepository.findAll()
                .stream()
                .filter(p -> p.getPedido().getCliente().getId().equals(clienteId))
                .map(this::toDTO)
                .toList();
    }

    private PagamentoResponseDTO toDTO(Pagamento pagamento) {

        PagamentoResponseDTO dto = new PagamentoResponseDTO();

        dto.setId(pagamento.getId());
        dto.setPedidoId(pagamento.getPedido().getId());
        dto.setMetodo(pagamento.getMetodo());
        dto.setValor(pagamento.getValor());
        dto.setStatus(pagamento.getStatus());
        dto.setDataPagamento(pagamento.getDataPagamento());

        return dto;
    }
}
