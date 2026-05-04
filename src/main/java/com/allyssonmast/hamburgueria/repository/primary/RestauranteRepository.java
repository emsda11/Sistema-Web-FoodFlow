package com.allyssonmast.hamburgueria.repository.primary;

import com.allyssonmast.hamburgueria.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    List<Restaurante> findByAtivoTrue();

    List<Restaurante> findByNomeContainingIgnoreCase(String nome);
}