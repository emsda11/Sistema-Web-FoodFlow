package com.allyssonmast.hamburgueria.controller;

import com.allyssonmast.hamburgueria.dto.PedidoRequestDTO;
import com.allyssonmast.hamburgueria.dto.PedidoResponseDTO;
import com.allyssonmast.hamburgueria.dto.PedidoStatusDTO;
import com.allyssonmast.hamburgueria.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Pedidos", description = "Endpoints responsáveis pelo gerenciamento de pedidos")
@SecurityRequirement(name = "bearerAuth")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @Operation(summary = "Criar pedido", description = "Realiza a criação de um novo pedido")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"), @ApiResponse(responseCode = "400", description = "Dados inválidos"), @ApiResponse(responseCode = "401", description = "Não autenticado")})
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criar(@Valid @RequestBody PedidoRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @Operation(summary = "Listar pedidos", description = "Retorna todos os pedidos cadastrados")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Pedidos retornados com sucesso"), @ApiResponse(responseCode = "403", description = "Acesso negado")})
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Buscar pedido por ID", description = "Retorna um pedido específico")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Pedido encontrado"), @ApiResponse(responseCode = "404", description = "Pedido não encontrado")})
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE', 'RESTAURANTE')")
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscar(@PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Histórico de pedidos do cliente", description = "Retorna o histórico de pedidos de um cliente")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoResponseDTO>> historicoCliente(@PathVariable Long clienteId) {

        return ResponseEntity.ok(service.historicoCliente(clienteId));
    }

    @Operation(summary = "Pedidos do restaurante", description = "Lista os pedidos associados a um restaurante")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANTE')")
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorRestaurante(@PathVariable Long restauranteId) {

        return ResponseEntity.ok(service.listarPorRestaurante(restauranteId));
    }

    @Operation(summary = "Atualizar status do pedido", description = "Atualiza o status de um pedido")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Status atualizado"), @ApiResponse(responseCode = "404", description = "Pedido não encontrado")})
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANTE')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(@PathVariable Long id, @Valid @RequestBody PedidoStatusDTO dto) {

        return ResponseEntity.ok(service.atualizarStatus(id, dto.getStatus()));
    }

    @Operation(summary = "Repetir pedido", description = "Cria um novo pedido baseado em um pedido anterior")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    @PostMapping("/{id}/repetir")
    public ResponseEntity<PedidoResponseDTO> repetir(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.repetirPedido(id));
    }

    @Operation(summary = "Cancelar pedido", description = "Realiza o cancelamento de um pedido")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Pedido cancelado"), @ApiResponse(responseCode = "404", description = "Pedido não encontrado")})
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {

        service.cancelar(id);

        return ResponseEntity.noContent().build();
    }
}