package com.allyssonmast.hamburgueria.dto;

import com.allyssonmast.hamburgueria.enums.TipoPagamento;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagamentoRequestDTO {

    @Min(1)
    private double valor;

    @NotNull
    private TipoPagamento tipo;

    @NotNull
    private Long clienteId;

    @NotBlank
    private String descricao;

    private List<Long> categoriasIds;
}
