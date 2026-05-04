package com.allyssonmast.hamburgueria.service.impl;

import com.allyssonmast.hamburgueria.dto.PagamentoRequestDTO;
import com.allyssonmast.hamburgueria.dto.PagamentoResponseDTO;
import com.allyssonmast.hamburgueria.enums.StatusPagamento;
import com.allyssonmast.hamburgueria.exception.NotFoundException;
import com.allyssonmast.hamburgueria.exception.PaymentException;
import com.allyssonmast.hamburgueria.model.Pagamento;
import com.allyssonmast.hamburgueria.model.Pedido;
import com.allyssonmast.hamburgueria.repository.primary.PagamentoRepository;
import com.allyssonmast.hamburgueria.repository.primary.PedidoRepository;
import com.allyssonmast.hamburgueria.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("simples")
public class PagamentoServiceSimples implements PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public PagamentoResponseDTO processar(PagamentoRequestDTO dto) {

        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        validarPedido(pedido);

        Pagamento pagamento = new Pagamento();

        pagamento.setPedido(pedido);

        pagamento.setMetodo(dto.getMetodo());

        pagamento.setValor(pedido.getValorTotal());

        pagamento.setStatus(StatusPagamento.PROCESSADO);

        Pagamento salvo = pagamentoRepository.save(pagamento);

        return toDTO(salvo);
    }

    @Override
    public PagamentoResponseDTO buscarPorId(Long id) {

        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pagamento não encontrado"));

        return toDTO(pagamento);
    }

    private void validarPedido(Pedido pedido) {

        if (pedido.getPagamento() != null) {
            throw new PaymentException("Pedido já possui pagamento");
        }

        if ("CANCELADO".equals(pedido.getStatus().name())) {

            throw new PaymentException("Não é possível pagar um pedido cancelado");
        }
    }

    private PagamentoResponseDTO toDTO(Pagamento pagamento) {

        PagamentoResponseDTO dto = new PagamentoResponseDTO();

        dto.setId(pagamento.getId());

        dto.setPedidoId(pagamento.getPedido().getId());

        dto.setMetodo(pagamento.getMetodo());

        dto.setStatus(pagamento.getStatus());

        dto.setValor(pagamento.getValor());

        return dto;
    }
}