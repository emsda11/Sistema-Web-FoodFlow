package com.allyssonmast.hamburgueria.security;

import com.allyssonmast.hamburgueria.model.Usuario;
import com.allyssonmast.hamburgueria.repository.primary.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        Usuario usuario = repository.findByUsername(username)

                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new User(

                usuario.getUsername(),

                usuario.getPassword(),

                List.of(new SimpleGrantedAuthority(usuario.getRole().name())));
    }
}