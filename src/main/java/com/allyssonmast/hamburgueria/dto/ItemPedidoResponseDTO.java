package com.allyssonmast.hamburgueria.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoResponseDTO {

    private Long produtoId;

    private String produtoNome;

    private Integer quantidade;

    private Double precoUnitario;

    private Double subtotal;
}