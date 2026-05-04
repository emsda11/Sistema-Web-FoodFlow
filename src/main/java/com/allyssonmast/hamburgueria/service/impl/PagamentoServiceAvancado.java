package com.allyssonmast.hamburgueria.service.impl;

import com.allyssonmast.hamburgueria.dto.PagamentoRequestDTO;
import com.allyssonmast.hamburgueria.dto.PagamentoResponseDTO;
import com.allyssonmast.hamburgueria.exception.PaymentException;
import com.allyssonmast.hamburgueria.enums.TipoPagamento;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("avancado")
public class PagamentoServiceAvancado extends PagamentoServiceSimples {

    @Override
    public PagamentoResponseDTO processar(PagamentoRequestDTO dto) {

        if (dto.getValor() > 5000 && dto.getTipo() == TipoPagamento.PIX) {
            throw new PaymentException("PIX acima do limite");
        }

        return super.processar(dto);
    }
}
