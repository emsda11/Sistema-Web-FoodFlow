package com.allyssonmast.hamburgueria.service;

import com.allyssonmast.hamburgueria.dto.ClienteRequestDTO;
import com.allyssonmast.hamburgueria.dto.ClienteResponseDTO;

import java.util.List;

public interface ClienteService {

    List<ClienteResponseDTO> listar();

    ClienteResponseDTO buscarPorUsername(String username);

    ClienteResponseDTO buscarPorId(Long id);

    ClienteResponseDTO atualizar(Long id, ClienteRequestDTO cliente);

    ClienteResponseDTO atualizarMeuPerfil(String username, ClienteRequestDTO dto);

    void deletar(Long id);

    void deletarMeuPerfil(String username);
}