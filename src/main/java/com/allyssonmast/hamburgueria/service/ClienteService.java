package com.allyssonmast.hamburgueria.service;

import com.allyssonmast.hamburgueria.dto.ClienteRequestDTO;
import com.allyssonmast.hamburgueria.dto.ClienteResponseDTO;

import java.util.List;

public interface ClienteService {

    List<ClienteResponseDTO> listar();

    ClienteResponseDTO buscarPorId(Long id);

    ClienteResponseDTO atualizar(Long id, ClienteRequestDTO cliente);

    void deletar(Long id);
}