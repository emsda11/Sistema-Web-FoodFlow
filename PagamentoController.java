package com.allyssonmast.hamburgueria.controller;

import com.allyssonmast.hamburgueria.dto.PagamentoRequestDTO;
import com.allyssonmast.hamburgueria.dto.PagamentoResponseDTO;
import com.allyssonmast.hamburgueria.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> realizarPagamento(
            @Valid @RequestBody PagamentoRequestDTO dto
    ) {

        return ResponseEntity.ok(
                pagamentoService.realizarPagamento(dto)
        );
    }

    @GetMapping
    public ResponseEntity<List<PagamentoResponseDTO>> listar() {

        return ResponseEntity.ok(
                pagamentoService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                pagamentoService.buscarPorId(id)
        );
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PagamentoResponseDTO>> listarPorCliente(
            @PathVariable Long clienteId
    ) {

        return ResponseEntity.ok(
                pagamentoService.listarPorCliente(clienteId)
        );
    }
}
