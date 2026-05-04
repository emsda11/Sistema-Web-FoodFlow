package com.allyssonmast.hamburgueria.service;

import com.allyssonmast.hamburgueria.dto.RestauranteRequestDTO;
import com.allyssonmast.hamburgueria.dto.RestauranteResponseDTO;

import java.util.List;

public interface RestauranteService {

    List<RestauranteResponseDTO> listar();

    RestauranteResponseDTO buscarPorId(Long id);

    List<RestauranteResponseDTO> buscarPorNome(String nome);

    List<RestauranteResponseDTO> listarAtivos();

    RestauranteResponseDTO atualizar(Long id, RestauranteRequestDTO dto);
}
