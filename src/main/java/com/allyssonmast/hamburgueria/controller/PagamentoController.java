package com.allyssonmast.hamburgueria.controller;

import com.allyssonmast.hamburgueria.dto.PagamentoRequestDTO;
import com.allyssonmast.hamburgueria.dto.PagamentoResponseDTO;
import com.allyssonmast.hamburgueria.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
@Tag(name = "Pagamentos", description = "Endpoints para gerenciamento e processamento de pagamentos")
@SecurityRequirement(name = "bearerAuth")
public class PagamentoController {

    @Autowired
    @Qualifier("simples") // ou avancado
    private PagamentoService service;

    @Operation(summary = "Processar pagamento", description = "Processa um novo pagamento. Apenas CLIENTE pode acessar.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Pagamento criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagamentoResponseDTO.class))), @ApiResponse(responseCode = "400", description = "Dados inválidos"), @ApiResponse(responseCode = "401", description = "Não autenticado"), @ApiResponse(responseCode = "403", description = "Acesso negado")})
    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> processar(

            @Valid @RequestBody PagamentoRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.processar(dto));
    }

    @Operation(summary = "Buscar pagamento por ID", description = "Retorna um pagamento específico pelo ID.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Pagamento encontrado"), @ApiResponse(responseCode = "404", description = "Pagamento não encontrado"), @ApiResponse(responseCode = "401", description = "Não autenticado")})
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> buscarPorId(

            @Parameter(description = "ID do pagamento", example = "1") @PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }
}