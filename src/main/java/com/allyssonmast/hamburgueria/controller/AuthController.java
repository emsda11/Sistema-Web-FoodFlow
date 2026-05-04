package com.allyssonmast.hamburgueria.controller;

import com.allyssonmast.hamburgueria.dto.*;
import com.allyssonmast.hamburgueria.security.JwtService;
import com.allyssonmast.hamburgueria.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid  @RequestBody AuthRequestDTO dto) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()));

        UserDetails user =
                userService.loadUserByUsername(dto.getUsername());

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(
                new AuthResponseDTO(token));
    }

    @PostMapping("/register/cliente")
    public ResponseEntity<Void> registrarCliente(@Valid @RequestBody RegisterClienteDTO dto) {

        authService.registrarCliente(dto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register/restaurante")
    public ResponseEntity<Void> registrarRestaurante(@Valid @RequestBody RegisterRestauranteDTO dto) {

        authService.registrarRestaurante(dto);

        return ResponseEntity.ok().build();
    }
}