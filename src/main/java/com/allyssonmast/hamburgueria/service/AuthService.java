package com.allyssonmast.hamburgueria.service;

import com.allyssonmast.hamburgueria.dto.RegisterClienteDTO;
import com.allyssonmast.hamburgueria.dto.RegisterRestauranteDTO;

public interface AuthService {

    void registrarCliente(RegisterClienteDTO dto);

    void registrarRestaurante(RegisterRestauranteDTO dto);
}
