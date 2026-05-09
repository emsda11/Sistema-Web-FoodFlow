package com.allyssonmast.hamburgueria.dto;

import com.allyssonmast.hamburgueria.enums.MetodoPagamento;
import com.allyssonmast.hamburgueria.enums.StatusPagamento;
import com.allyssonmast.hamburgueria.enums.TipoPagamento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoResponseDTO {

    private Long id;

    private Long pedidoId;

    private MetodoPagamento metodo;

    private StatusPagamento status;

    private Double valor;
}
