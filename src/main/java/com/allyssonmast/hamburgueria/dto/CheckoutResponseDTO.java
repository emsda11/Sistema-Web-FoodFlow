package com.allyssonmast.hamburgueria.dto;

import com.allyssonmast.hamburgueria.enums.StatusPagamento;
import com.allyssonmast.hamburgueria.enums.StatusPedido;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutResponseDTO {

    private Long pedidoId;

    private Long pagamentoId;

    private StatusPedido statusPedido;

    private StatusPagamento statusPagamento;

    private Double valorTotal;
}