package com.allyssonmast.hamburgueria.service.impl;

import com.allyssonmast.hamburgueria.dto.RegisterClienteDTO;
import com.allyssonmast.hamburgueria.dto.RegisterRestauranteDTO;
import com.allyssonmast.hamburgueria.enums.Role;
import com.allyssonmast.hamburgueria.exception.BusinessException;
import com.allyssonmast.hamburgueria.model.Cliente;
import com.allyssonmast.hamburgueria.model.Restaurante;
import com.allyssonmast.hamburgueria.model.Usuario;
import com.allyssonmast.hamburgueria.repository.primary.ClienteRepository;
import com.allyssonmast.hamburgueria.repository.primary.RestauranteRepository;
import com.allyssonmast.hamburgueria.repository.primary.UsuarioRepository;
import com.allyssonmast.hamburgueria.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registrarCliente(
            RegisterClienteDTO dto
    ) {

        validarUsername(dto.getUsername());

        Usuario usuario = new Usuario();

        usuario.setUsername(dto.getUsername());

        usuario.setPassword(
                passwordEncoder.encode(
                        dto.getPassword()
                )
        );

        usuario.setRole(Role.ROLE_CLIENTE);

        Usuario usuarioSalvo =
                usuarioRepository.save(usuario);

        Cliente cliente = new Cliente();

        cliente.setUsuario(usuarioSalvo);

        cliente.setNome(dto.getNome());

        cliente.setEmail(dto.getEmail());

        clienteRepository.save(cliente);
    }

    @Override
    public void registrarRestaurante(
            RegisterRestauranteDTO dto
    ) {

        validarUsername(dto.getUsername());

        Usuario usuario = new Usuario();

        usuario.setUsername(dto.getUsername());

        usuario.setPassword(
                passwordEncoder.encode(
                        dto.getPassword()
                )
        );

        usuario.setRole(
                Role.ROLE_RESTAURANTE
        );

        Usuario usuarioSalvo =
                usuarioRepository.save(usuario);

        Restaurante restaurante =
                new Restaurante();

        restaurante.setUsuario(usuarioSalvo);

        restaurante.setNome(dto.getNome());

        restaurante.setDescricao(
                dto.getDescricao()
        );

        restaurante.setEndereco(
                dto.getEndereco()
        );

        restaurante.setAtivo(true);

        restauranteRepository.save(restaurante);
    }

    private void validarUsername(
            String username
    ) {

        boolean existe =
                usuarioRepository
                        .findByUsername(username)
                        .isPresent();

        if (existe) {

            throw new BusinessException(
                    "Username já está em uso"
            );
        }
    }
}
