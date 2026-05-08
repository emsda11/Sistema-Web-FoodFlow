package com.allyssonmast.hamburgueria.service;

import com.allyssonmast.hamburgueria.dto.ProdutoRequestDTO;
import com.allyssonmast.hamburgueria.dto.ProdutoResponseDTO;

import java.util.List;

public interface ProdutoService {

    List<ProdutoResponseDTO> listar();

    ProdutoResponseDTO buscarPorId(Long id);

    ProdutoResponseDTO criar(ProdutoRequestDTO dto);

    ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto);

    void deletar(Long id);

    List<ProdutoResponseDTO> buscarPorNome(String nome);

    List<ProdutoResponseDTO> meusProdutos();

    List<ProdutoResponseDTO> listarPorRestaurante(Long restauranteId);
}
