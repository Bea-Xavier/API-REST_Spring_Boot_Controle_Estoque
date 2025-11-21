package com.controleestoque.api_estoque.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.controleestoque.api_estoque.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
	// Compatibilidade com chamada existente em ProdutoController:
	// O controller chama `findBy(id)` — aqui fornecemos um helper que delega a
	// `findById(id)` já oferecida pelo JpaRepository.
	default java.util.Optional<Categoria> findBy(Long id) {
		return findById(id);
	}

}
