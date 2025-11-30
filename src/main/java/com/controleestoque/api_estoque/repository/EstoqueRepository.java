package com.controleestoque.api_estoque.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.controleestoque.api_estoque.model.Estoque;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
     Optional<Estoque> findByProdutoId(Long produtoId);
}

