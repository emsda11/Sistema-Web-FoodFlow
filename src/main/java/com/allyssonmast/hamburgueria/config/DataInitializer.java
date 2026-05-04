package com.allyssonmast.hamburgueria.config;

import com.allyssonmast.hamburgueria.enums.Role;
import com.allyssonmast.hamburgueria.model.Usuario;
import com.allyssonmast.hamburgueria.repository.primary.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(

            UsuarioRepository repository,

            PasswordEncoder encoder) {

        return args -> {

            if (repository.findByUsername("admin").isEmpty()) {

                Usuario admin = new Usuario();

                admin.setUsername("admin");

                admin.setPassword(

                        encoder.encode("123456"));

                admin.setRole(Role.ROLE_ADMIN);

                repository.save(admin);
            }

            if (repository.findByUsername("manager").isEmpty()) {

                Usuario manager = new Usuario();

                manager.setUsername("manager");

                manager.setPassword(

                        encoder.encode("123456"));

                manager.setRole(Role.ROLE_RESTAURANTE);

                repository.save(manager);
            }

            if (repository.findByUsername("attendant").isEmpty()) {

                Usuario attendant = new Usuario();

                attendant.setUsername("attendant");

                attendant.setPassword(

                        encoder.encode("123456"));

                attendant.setRole(Role.ROLE_CLIENTE);

                repository.save(attendant);
            }
        };
    }
}