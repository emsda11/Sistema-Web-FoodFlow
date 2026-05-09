package com.allyssonmast.hamburgueria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurantes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurante {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private Boolean ativo = true;

    @OneToMany(
            mappedBy = "restaurante",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Produto> produtos = new ArrayList<>();

    @OneToMany(mappedBy = "restaurante")
    private List<Pedido> pedidos = new ArrayList<>();

    @OneToMany(mappedBy = "restaurante")
    private List<Avaliacao> avaliacoes = new ArrayList<>();
}
