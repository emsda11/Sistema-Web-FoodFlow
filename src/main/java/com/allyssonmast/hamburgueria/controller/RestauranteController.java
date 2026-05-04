package com.allyssonmast.hamburgueria.controller;

import com.allyssonmast.hamburgueria.dto.RestauranteRequestDTO;
import com.allyssonmast.hamburgueria.dto.RestauranteResponseDTO;
import com.allyssonmast.hamburgueria.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
@Tag(name = "Restaurantes", description = "Endpoints para gerenciamento de restaurantes")
@SecurityRequirement(name = "bearerAuth")
public class RestauranteController {

    @Autowired
    private RestauranteService service;

    @Operation(summary = "Listar restaurantes", description = "Retorna todos os restaurantes cadastrados")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista retornada com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RestauranteResponseDTO.class))))})
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<RestauranteResponseDTO>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Listar restaurantes ativos", description = "Retorna apenas restaurantes ativos")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")})
    @GetMapping("/ativos")
    public ResponseEntity<List<RestauranteResponseDTO>> listarAtivos() {

        return ResponseEntity.ok(service.listarAtivos());
    }

    @Operation(summary = "Buscar restaurante por ID", description = "Retorna um restaurante específico")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Restaurante encontrado"), @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")})
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarPorId(
            @Parameter(description = "ID do restaurante", example = "1") @PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Atualizar restaurante", description = "Atualiza os dados de um restaurante")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso"), @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"), @ApiResponse(responseCode = "403", description = "Acesso negado")})
    @PreAuthorize("hasRole('RESTAURANTE')")
    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> atualizar(
            @Parameter(description = "ID do restaurante", example = "1") @PathVariable Long id,
            @Valid @RequestBody RestauranteRequestDTO dto) {

        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @Operation(summary = "Buscar restaurantes por nome", description = "Busca restaurantes pelo nome")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")})
    @GetMapping("/buscar")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarPorNome(
            @Parameter(description = "Nome do restaurante", example = "pizza") @RequestParam String nome) {

        return ResponseEntity.ok(service.buscarPorNome(nome));
    }
}