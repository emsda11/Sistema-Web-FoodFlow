package com.allyssonmast.hamburgueria.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET = "minha-chave-super-secreta-com-mais-de-32-caracteres"; //o Certo é armazenar em aplicaçoes como o Vault

    private Key getKey() {

        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(UserDetails user) {

        String role = user.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_CLIENTE");

        return Jwts.builder()

                .setSubject(user.getUsername())

                .claim("role", role)

                .setIssuedAt(new Date())

                .setExpiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60)
                )

                .signWith(getKey(), SignatureAlgorithm.HS256)

                .compact();
    }

    public String extractUsername(String token) {

        return Jwts.parserBuilder()

                .setSigningKey(getKey())

                .build()

                .parseClaimsJws(token)

                .getBody()

                .getSubject();
    }

    public boolean isValid(String token, UserDetails user) {

        String username = extractUsername(token);

        return username.equals(user.getUsername());
    }
}
