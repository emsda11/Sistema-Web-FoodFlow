package com.allyssonmast.hamburgueria.service.impl;

import com.allyssonmast.hamburgueria.dto.*;
import com.allyssonmast.hamburgueria.service.AuditService;
import com.allyssonmast.hamburgueria.service.CheckoutService;
import com.allyssonmast.hamburgueria.service.PagamentoService;
import com.allyssonmast.hamburgueria.service.PedidoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    @Qualifier("simples")
    private PagamentoService pagamentoService;

    @Autowired
    private AuditService auditService;

    @Override
    @Transactional
    public CheckoutResponseDTO finalizar(CheckoutRequestDTO dto) {

        PedidoRequestDTO pedidoRequest = new PedidoRequestDTO();

        pedidoRequest.setClienteId(dto.getClienteId());
        pedidoRequest.setRestauranteId(dto.getRestauranteId());
        pedidoRequest.setItens(dto.getItens());

        PedidoResponseDTO pedido = pedidoService.criar(pedidoRequest);

        PagamentoRequestDTO pagamentoRequest = new PagamentoRequestDTO();

        pagamentoRequest.setPedidoId(pedido.getId());
        pagamentoRequest.setMetodo(dto.getMetodoPagamento());

        PagamentoResponseDTO pagamento = pagamentoService.processar(pagamentoRequest);

        CheckoutResponseDTO response = new CheckoutResponseDTO();

        response.setPedidoId(pedido.getId());
        response.setPagamentoId(pagamento.getId());
        response.setStatusPedido(pedido.getStatus());
        response.setStatusPagamento(pagamento.getStatus());
        response.setValorTotal(pedido.getValorTotal());

        auditService.registrar(
                "Checkout",
                pedido.getId(),
                "FINALIZADO"
        );

        return response;
    }
}