package com.allyssonmast.hamburgueria.service;

import com.allyssonmast.hamburgueria.dto.PagamentoRequestDTO;
import com.allyssonmast.hamburgueria.dto.PagamentoResponseDTO;

import java.util.List;

public interface PagamentoService {

    PagamentoResponseDTO realizarPagamento(PagamentoRequestDTO dto);

    List<PagamentoResponseDTO> listar();

    PagamentoResponseDTO buscarPorId(Long id);

    List<PagamentoResponseDTO> listarPorCliente(Long clienteId);
}
