package com.allyssonmast.hamburgueria.service;

import com.allyssonmast.hamburgueria.dto.CheckoutRequestDTO;
import com.allyssonmast.hamburgueria.dto.CheckoutResponseDTO;

public interface CheckoutService {

    CheckoutResponseDTO finalizar(CheckoutRequestDTO dto);
}