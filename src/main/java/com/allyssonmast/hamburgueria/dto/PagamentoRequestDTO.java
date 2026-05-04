package com.allyssonmast.hamburgueria.dto;

import com.allyssonmast.hamburgueria.enums.MetodoPagamento;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoRequestDTO {

    @NotNull(message = "O ID do pedido é obrigatório")
    private Long pedidoId;

    @NotNull(message = "O método de pagamento é obrigatório")
    private MetodoPagamento metodo;
}