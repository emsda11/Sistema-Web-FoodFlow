package com.allyssonmast.hamburgueria.controller;

import com.allyssonmast.hamburgueria.dto.AuditLogResponseDTO;
import com.allyssonmast.hamburgueria.service.AuditService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auditoria")
@Tag(name = "Auditoria", description = "Endpoints responsáveis pelo gerenciamento de logs de auditoria")
public class AuditController {

    @Autowired
    private AuditService service;

    @Operation(summary = "Listar logs de auditoria", description = "Retorna todos os registros de auditoria do sistema", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Logs retornados com sucesso"), @ApiResponse(responseCode = "403", description = "Acesso negado"), @ApiResponse(responseCode = "401", description = "Usuário não autenticado")})
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AuditLogResponseDTO>> listar() {

        List<AuditLogResponseDTO> logs = service.listar();

        return ResponseEntity.ok(logs);
    }
}