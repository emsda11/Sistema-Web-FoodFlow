package com.allyssonmast.hamburgueria.service;

import com.allyssonmast.hamburgueria.dto.PedidoRequestDTO;
import com.allyssonmast.hamburgueria.dto.PedidoResponseDTO;
import com.allyssonmast.hamburgueria.enums.StatusPedido;

import java.util.List;

public interface PedidoService {
    PedidoResponseDTO criar(PedidoRequestDTO dto);
    List<PedidoResponseDTO> listar();
    PedidoResponseDTO buscarPorId(Long id);
    List<PedidoResponseDTO> historicoCliente(Long clienteId);
    List<PedidoResponseDTO> listarPorRestaurante(Long restauranteId);
    PedidoResponseDTO atualizarStatus(Long id, StatusPedido status);
    PedidoResponseDTO repetirPedido(Long id);
    void cancelar(Long id);
}
