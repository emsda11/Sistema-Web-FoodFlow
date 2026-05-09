package com.allyssonmast.hamburgueria.service;

import com.allyssonmast.hamburgueria.dto.PagamentoRequestDTO;
import com.allyssonmast.hamburgueria.dto.PagamentoResponseDTO;

public interface PagamentoService {

    PagamentoResponseDTO processar(PagamentoRequestDTO dto);

    PagamentoResponseDTO buscarPorId(Long id);
}