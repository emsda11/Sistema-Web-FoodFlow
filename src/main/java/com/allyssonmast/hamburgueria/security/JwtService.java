package com.allyssonmast.hamburgueria.security;

import com.allyssonmast.hamburgueria.model.Usuario;
import com.allyssonmast.hamburgueria.repository.primary.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET = "minha-chave-super-secreta-com-mais-de-32-caracteres";

    private Key key;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(UserDetails user) {

        Usuario usuario = usuarioRepository.findByUsername(user.getUsername()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String role = user.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse("ROLE_CLIENTE");

        return Jwts.builder()

                .setSubject(user.getUsername())

                .claim("id", usuario.getId())

                .claim("role", role)

                .setIssuedAt(new Date())

                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))

                .signWith(key, SignatureAlgorithm.HS256)

                .compact();
    }

    public String extractUsername(String token) {

        return Jwts.parserBuilder()

                .setSigningKey(key)

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