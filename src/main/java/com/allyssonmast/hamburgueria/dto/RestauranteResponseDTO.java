package com.allyssonmast.hamburgueria.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteResponseDTO {

    private Long id;

    private String nome;

    private String descricao;

    private String endereco;

    private Boolean ativo;
}
