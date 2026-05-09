package com.allyssonmast.hamburgueria.strategy.impl;

import com.allyssonmast.hamburgueria.model.Pagamento;
import com.allyssonmast.hamburgueria.enums.StatusPagamento;
import com.allyssonmast.hamburgueria.strategy.ProcessadorPagamentoStrategy;
import org.springframework.stereotype.Service;

@Service("CARTAO")
public class CartaoStrategy implements ProcessadorPagamentoStrategy {

    public StatusPagamento processar(Pagamento pagamento) {
        return pagamento.getValor() > 1000
                ? StatusPagamento.RECUSADO
                : StatusPagamento.PAGO;
    }
}
