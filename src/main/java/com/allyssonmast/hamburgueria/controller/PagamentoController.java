package com.allyssonmast.hamburgueria.controller;

import com.allyssonmast.hamburgueria.dto.PagamentoRequestDTO;
import com.allyssonmast.hamburgueria.dto.PagamentoResponseDTO;
import com.allyssonmast.hamburgueria.enums.TipoPagamento;
import com.allyssonmast.hamburgueria.service.PagamentoService;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@Tag(name = "Pagamentos", description = "Endpoints para gerenciamento e processamento de pagamentos")
@SecurityRequirement(name = "bearerAuth")
public class PagamentoController {

    @Autowired
    @Qualifier("simples") // ou avancado
    private PagamentoService service;

    @Operation(summary = "Criar pagamento", description = "Cria e processa um novo pagamento. Apenas ADMIN pode acessar.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Pagamento criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagamentoResponseDTO.class))), @ApiResponse(responseCode = "400", description = "Dados inválidos"), @ApiResponse(responseCode = "401", description = "Não autenticado"), @ApiResponse(responseCode = "403", description = "Acesso negado")})
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> criar(

            @Valid @RequestBody PagamentoRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.processar(dto));
    }

    @Operation(summary = "Atualizar pagamento", description = "Atualiza os dados de um pagamento existente.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Pagamento atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagamentoResponseDTO.class))), @ApiResponse(responseCode = "400", description = "Dados inválidos"), @ApiResponse(responseCode = "404", description = "Pagamento não encontrado"), @ApiResponse(responseCode = "401", description = "Não autenticado")})
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> atualizar(

            @Parameter(description = "ID do pagamento", example = "1") @PathVariable Long id,

            @Valid @RequestBody PagamentoRequestDTO dto) {

        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @Operation(summary = "Listar pagamentos", description = "Retorna todos os pagamentos cadastrados.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista retornada com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PagamentoResponseDTO.class)))), @ApiResponse(responseCode = "401", description = "Não autenticado"), @ApiResponse(responseCode = "403", description = "Acesso negado")})
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','ATTENDANT')")
    @GetMapping
    public ResponseEntity<List<PagamentoResponseDTO>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Buscar pagamento por ID", description = "Retorna um pagamento específico pelo ID.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Pagamento encontrado"), @ApiResponse(responseCode = "404", description = "Pagamento não encontrado"), @ApiResponse(responseCode = "401", description = "Não autenticado")})
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','ATTENDANT')")
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> buscar(

            @Parameter(description = "ID do pagamento", example = "1") @PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Buscar pagamentos por tipo", description = "Retorna pagamentos filtrados pelo tipo.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Pagamentos encontrados"), @ApiResponse(responseCode = "400", description = "Tipo inválido"), @ApiResponse(responseCode = "401", description = "Não autenticado")})
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','ATTENDANT')")
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<PagamentoResponseDTO>> buscarPorTipo(

            @Parameter(description = "Tipo do pagamento", example = "PIX") @PathVariable TipoPagamento tipo) {

        return ResponseEntity.ok(service.buscarPorTipo(tipo));
    }

    @Operation(summary = "Remover pagamento", description = "Remove um pagamento pelo ID. Apenas ADMIN pode acessar.")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Pagamento removido com sucesso"), @ApiResponse(responseCode = "404", description = "Pagamento não encontrado"), @ApiResponse(responseCode = "401", description = "Não autenticado"), @ApiResponse(responseCode = "403", description = "Acesso negado")})
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(

            @Parameter(description = "ID do pagamento", example = "1") @PathVariable Long id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}