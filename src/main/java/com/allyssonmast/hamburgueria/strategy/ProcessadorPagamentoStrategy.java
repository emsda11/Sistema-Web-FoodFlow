package com.allyssonmast.hamburgueria.strategy;

import com.allyssonmast.hamburgueria.model.Pagamento;
import com.allyssonmast.hamburgueria.enums.StatusPagamento;

public interface ProcessadorPagamentoStrategy {
    StatusPagamento processar(Pagamento pagamento);
}
