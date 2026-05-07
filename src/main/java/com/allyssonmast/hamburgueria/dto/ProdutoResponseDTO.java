package com.allyssonmast.hamburgueria.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProdutoResponseDTO {

    private Long id;

    private String nome;

    private String descricao;

    private Double preco;

    private Boolean disponivel;

    private String restauranteNome;

    private List<String> categorias;
}