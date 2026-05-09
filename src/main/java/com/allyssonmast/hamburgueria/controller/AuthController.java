package com.allyssonmast.hamburgueria.controller;

import com.allyssonmast.hamburgueria.dto.*;
import com.allyssonmast.hamburgueria.security.JwtService;
import com.allyssonmast.hamburgueria.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints responsáveis por autenticação e cadastro de usuários")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthService authService;

    @Operation(summary = "Realizar login", description = "Autentica um usuário e retorna um token JWT")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Login realizado com sucesso"), @ApiResponse(responseCode = "401", description = "Usuário ou senha inválidos")})
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO dto) {

        authManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        UserDetails user = userService.loadUserByUsername(dto.getUsername());

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @Operation(summary = "Registrar cliente", description = "Realiza o cadastro de um novo cliente no sistema")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cliente registrado com sucesso"), @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    @PostMapping("/register/cliente")
    public ResponseEntity<Void> registrarCliente(@Valid @RequestBody RegisterClienteDTO dto) {

        authService.registrarCliente(dto);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Registrar restaurante", description = "Realiza o cadastro de um novo restaurante no sistema")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Restaurante registrado com sucesso"), @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    @PostMapping("/register/restaurante")
    public ResponseEntity<Void> registrarRestaurante(@Valid @RequestBody RegisterRestauranteDTO dto) {

        authService.registrarRestaurante(dto);

        return ResponseEntity.ok().build();
    }
}