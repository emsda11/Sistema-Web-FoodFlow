package com.allyssonmast.hamburgueria.dto;

import com.allyssonmast.hamburgueria.enums.StatusPedido;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoStatusDTO {
    @NotNull(message = "Status é obrigatório")
    private StatusPedido status;
}
