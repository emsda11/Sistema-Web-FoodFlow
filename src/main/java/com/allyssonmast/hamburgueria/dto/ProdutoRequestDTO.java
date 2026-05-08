package com.allyssonmast.hamburgueria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProdutoRequestDTO {

    @NotBlank
    private String nome;

    private String descricao;

    @NotNull
    @Positive
    private Double preco;

    @NotNull
    private Boolean disponivel;

    private List<Long> categoriasIds;
}