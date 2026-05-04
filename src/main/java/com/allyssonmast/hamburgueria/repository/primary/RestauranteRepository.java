package com.allyssonmast.hamburgueria.repository.primary;


import com.allyssonmast.hamburgueria.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> { }