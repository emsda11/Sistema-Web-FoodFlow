package com.allyssonmast.hamburgueria.strategy.impl;

import com.allyssonmast.hamburgueria.model.Pagamento;
import com.allyssonmast.hamburgueria.enums.StatusPagamento;
import com.allyssonmast.hamburgueria.strategy.ProcessadorPagamentoStrategy;
import org.springframework.stereotype.Service;

@Service("PIX")
public class PixStrategy implements ProcessadorPagamentoStrategy {

    public StatusPagamento processar(Pagamento pagamento) {
        return StatusPagamento.PROCESSADO;
    }
}
