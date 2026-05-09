package com.allyssonmast.hamburgueria.dto;

import com.allyssonmast.hamburgueria.enums.MetodoPagamento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CheckoutRequestDTO {

    @NotNull(message = "ID do cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "ID do restaurante é obrigatório")
    private Long restauranteId;

    @Valid
    @NotEmpty(message = "O checkout precisa ter ao menos um item")
    private List<ItemPedidoRequestDTO> itens = new ArrayList<>();

    @NotNull(message = "O método de pagamento é obrigatório")
    private MetodoPagamento metodoPagamento;
}