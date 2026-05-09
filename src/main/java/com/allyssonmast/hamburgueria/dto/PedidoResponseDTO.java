package com.allyssonmast.hamburgueria.dto;

import com.allyssonmast.hamburgueria.enums.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoResponseDTO {
    private Long id;
    private Long clienteId;
    private String clienteNome;
    private Long restauranteId;
    private String restauranteNome;
    private StatusPedido status;
    private Double valorTotal;
    private LocalDateTime criadoEm;
    private List<ItemPedidoResponseDTO> itens;
}
