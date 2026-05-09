package com.allyssonmast.hamburgueria.controller;

import com.allyssonmast.hamburgueria.dto.CheckoutRequestDTO;
import com.allyssonmast.hamburgueria.dto.CheckoutResponseDTO;
import com.allyssonmast.hamburgueria.service.CheckoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

@RestController
@RequestMapping("/checkout")
@Tag(name = "Checkout", description = "Endpoints para finalização de pedidos e pagamentos")
@SecurityRequirement(name = "bearerAuth")
public class CheckoutController {

    @Autowired
    private CheckoutService service;

    @Operation(summary = "Finalizar checkout", description = "Cria o pedido e processa o pagamento em uma única operação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Checkout realizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CheckoutResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")})
    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping
    public ResponseEntity<CheckoutResponseDTO> finalizar(@Valid @RequestBody CheckoutRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.finalizar(dto));
    }
}