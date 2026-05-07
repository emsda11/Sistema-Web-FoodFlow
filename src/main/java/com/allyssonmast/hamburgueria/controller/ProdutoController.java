package com.allyssonmast.hamburgueria.controller;

import com.allyssonmast.hamburgueria.dto.ProdutoRequestDTO;
import com.allyssonmast.hamburgueria.dto.ProdutoResponseDTO;
import com.allyssonmast.hamburgueria.service.ProdutoService;
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
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
@SecurityRequirement(name = "bearerAuth")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Operation(summary = "Listar produtos", description = "Retorna todos os produtos cadastrados")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista retornada com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProdutoResponseDTO.class))))})
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Produto encontrado"), @ApiResponse(responseCode = "404", description = "Produto não encontrado")})
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(
            @Parameter(description = "ID do produto", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Buscar produtos por nome", description = "Busca produtos pelo nome")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")})
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    @GetMapping("/buscar")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorNome(
            @Parameter(description = "Nome do produto", example = "hamburguer") @RequestParam String nome) {
        return ResponseEntity.ok(service.buscarPorNome(nome));
    }

    @Operation(summary = "Criar produto", description = "Cria um novo produto")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Produto criado com sucesso"), @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANTE')")
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@Valid @RequestBody ProdutoRequestDTO dto) {
        return ResponseEntity.ok(service.criar(dto));
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"), @ApiResponse(responseCode = "404", description = "Produto não encontrado"), @ApiResponse(responseCode = "403", description = "Acesso negado")})
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANTE')")
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @Parameter(description = "ID do produto", example = "1") @PathVariable Long id,
            @Valid @RequestBody ProdutoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @Operation(summary = "Remover produto", description = "Remove um produto pelo ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Produto removido com sucesso"), @ApiResponse(responseCode = "404", description = "Produto não encontrado")})
    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANTE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do produto", example = "1") @PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
