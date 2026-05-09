package com.allyssonmast.hamburgueria.strategy.factory;

import com.allyssonmast.hamburgueria.exception.PaymentException;
import com.allyssonmast.hamburgueria.enums.TipoPagamento;
import com.allyssonmast.hamburgueria.strategy.ProcessadorPagamentoStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PagamentoStrategyFactory {

    @Autowired
    private Map<String, ProcessadorPagamentoStrategy> strategies;

    public ProcessadorPagamentoStrategy getStrategy(TipoPagamento tipo) {
        ProcessadorPagamentoStrategy strategy = strategies.get(tipo.name());

        if (strategy == null) {
            throw new PaymentException("Tipo de pagamento não suportado");
        }

        return strategy;
    }
}
