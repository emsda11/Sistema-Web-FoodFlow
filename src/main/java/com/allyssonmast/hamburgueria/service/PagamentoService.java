package com.allyssonmast.hamburgueria.service;

import com.allyssonmast.hamburgueria.dto.PagamentoRequestDTO;
import com.allyssonmast.hamburgueria.dto.PagamentoResponseDTO;
import com.allyssonmast.hamburgueria.enums.TipoPagamento;

import java.util.List;

public interface PagamentoService {
    PagamentoResponseDTO processar(PagamentoRequestDTO dto);
    List<PagamentoResponseDTO> listar();
    PagamentoResponseDTO buscarPorId(Long id);
    void deletar(Long id);
    List<PagamentoResponseDTO> buscarPorTipo(TipoPagamento tipo);
    PagamentoResponseDTO atualizar(Long id, PagamentoRequestDTO dto);
}
