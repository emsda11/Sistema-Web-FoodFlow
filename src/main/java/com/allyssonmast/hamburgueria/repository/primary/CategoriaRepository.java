package com.allyssonmast.hamburgueria.repository.primary;

import com.allyssonmast.hamburgueria.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}