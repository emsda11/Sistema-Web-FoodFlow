package com.allyssonmast.hamburgueria.controller;

import com.allyssonmast.hamburgueria.dto.PedidoRequestDTO;
import com.allyssonmast.hamburgueria.dto.PedidoResponseDTO;
import com.allyssonmast.hamburgueria.dto.PedidoStatusDTO;
import com.allyssonmast.hamburgueria.service.PedidoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos")
@SecurityRequirement(name = "bearerAuth")
public class PedidoController {
    @Autowired private PedidoService service;

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criar(@Valid @RequestBody PedidoRequestDTO dto){ return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto)); }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listar(){ return ResponseEntity.ok(service.listar()); }

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE', 'RESTAURANTE')")
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscar(@PathVariable Long id){ return ResponseEntity.ok(service.buscarPorId(id)); }

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoResponseDTO>> historicoCliente(@PathVariable Long clienteId){ return ResponseEntity.ok(service.historicoCliente(clienteId)); }

    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANTE')")
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorRestaurante(@PathVariable Long restauranteId){ return ResponseEntity.ok(service.listarPorRestaurante(restauranteId)); }

    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANTE')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(@PathVariable Long id, @Valid @RequestBody PedidoStatusDTO dto){ return ResponseEntity.ok(service.atualizarStatus(id, dto.getStatus())); }

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    @PostMapping("/{id}/repetir")
    public ResponseEntity<PedidoResponseDTO> repetir(@PathVariable Long id){ return ResponseEntity.status(HttpStatus.CREATED).body(service.repetirPedido(id)); }

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id){ service.cancelar(id); return ResponseEntity.noContent().build(); }
}
