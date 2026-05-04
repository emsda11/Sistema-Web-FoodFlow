package com.allyssonmast.hamburgueria.repository.primary;

import com.allyssonmast.hamburgueria.model.Pagamento;
import com.allyssonmast.hamburgueria.enums.TipoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    List<Pagamento> findByTipo(TipoPagamento tipo);

    @Query("SELECT p FROM Pagamento p WHERE p.valor > :valor")
    List<Pagamento> buscarPagamentosAcimaValor(double valor);

    @Query(value = "SELECT * FROM pagamento WHERE tipo = :tipo", nativeQuery = true)
    List<Pagamento> buscarPorTipoNativo(String tipo);

    @Query("SELECT p FROM Pagamento p JOIN FETCH p.cliente WHERE p.id = :id")
    Optional<Pagamento> buscarComCliente(Long id);
}
