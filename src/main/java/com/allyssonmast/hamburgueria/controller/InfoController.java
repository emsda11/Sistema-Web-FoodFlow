package com.allyssonmast.hamburgueria.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/info")
@Tag(name = "Informações", description = "Endpoints de informações gerais da aplicação")
public class InfoController {

    @Operation(summary = "Informações da API", description = "Retorna informações básicas sobre a aplicação FoodFlow")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Informações retornadas com sucesso")})
    @GetMapping
    public ResponseEntity<?> info() {

        return ResponseEntity.ok(Map.of("sistema", "FoodFlow API", "versao", "1.0"));
    }
}