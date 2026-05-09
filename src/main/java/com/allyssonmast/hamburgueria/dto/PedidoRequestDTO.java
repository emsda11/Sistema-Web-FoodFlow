package com.allyssonmast.hamburgueria.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PedidoRequestDTO {
    @NotNull(message = "ID do cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "ID do restaurante é obrigatório")
    private Long restauranteId;

    @Valid
    @NotEmpty(message = "O pedido precisa ter ao menos um item")
    private List<ItemPedidoRequestDTO> itens = new ArrayList<>();
}
