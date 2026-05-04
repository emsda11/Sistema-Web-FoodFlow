package com.allyssonmast.hamburgueria.controller;

import com.allyssonmast.hamburgueria.dto.ClienteRequestDTO;
import com.allyssonmast.hamburgueria.dto.ClienteResponseDTO;
import com.allyssonmast.hamburgueria.service.ClienteService;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@Tag(
        name = "Clientes",
        description = "Endpoints para gerenciamento de clientes"
)
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Operation(
            summary = "Listar clientes",
            description = "Retorna todos os clientes cadastrados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista retornada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = ClienteResponseDTO.class
                                    )
                            )
                    )
            )
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/me")
    public ResponseEntity<ClienteResponseDTO> buscarMeuPerfil(Authentication authentication) {

        return ResponseEntity.ok(
                service.buscarPorUsername(authentication.getName())
        );
    }

    @Operation(
            summary = "Buscar cliente por ID",
            description = "Retorna um cliente específico pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente encontrado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado"
            )
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscar(

            @Parameter(
                    description = "ID do cliente",
                    example = "1"
            )
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(
            summary = "Atualizar cliente",
            description = "Atualiza os dados de um cliente existente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente atualizado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado"
            )
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(

            @Parameter(
                    description = "ID do cliente",
                    example = "1"
            )
            @PathVariable Long id,

            @Valid
            @RequestBody
            ClienteRequestDTO cliente
    ) {

        return ResponseEntity.ok(service.atualizar(id, cliente));
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @PutMapping("/me")
    public ResponseEntity<ClienteResponseDTO> atualizarMeuPerfil(
            Authentication authentication, @Valid @RequestBody ClienteRequestDTO dto) {

        return ResponseEntity.ok(
                service.atualizarMeuPerfil(
                        authentication.getName(),
                        dto));
    }

    @Operation(
            summary = "Remover cliente",
            description = "Remove um cliente pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Cliente removido com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado"
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(

            @Parameter(
                    description = "ID do cliente",
                    example = "1"
            )
            @PathVariable Long id
    ) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @DeleteMapping("/me")
    public ResponseEntity<Void> deletarMeuPerfil(Authentication authentication) {

        service.deletarMeuPerfil(authentication.getName());

        return ResponseEntity
                .noContent()
                .build();
    }
}