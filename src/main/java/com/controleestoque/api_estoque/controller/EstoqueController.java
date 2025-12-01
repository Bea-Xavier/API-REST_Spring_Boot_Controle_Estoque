package com.controleestoque.api_estoque.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controleestoque.api_estoque.model.Estoque;
import com.controleestoque.api_estoque.repository.EstoqueRepository;
import com.controleestoque.api_estoque.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/estoques")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;

    // GET /api/estoques
    @GetMapping
    public List<Estoque> getAllEstoques() {
        // Retorna todos os estoques com os produtos associados.
        return estoqueRepository.findAll();
    }

    // GET /api/estoques/{id}
    @GetMapping("/{id}")
    public ResponseEntity<List<Estoque>> getEstoquesByProdutoCategoria(@PathVariable Long id) {
        // Busca o produto pelo ID.
        return produtoRepository.findById(id)
                .map(produto -> {
                    // Filtra os estoques com produtos da mesma categoria.
                    List<Estoque> estoquesDaMesmaCategoria = estoqueRepository.findAll().stream()
                            .filter(estoque -> estoque.getProduto().getCategoria().getId()
                                    .equals(produto.getCategoria().getId()))
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(estoquesDaMesmaCategoria);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/estoques/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Estoque> updateEstoque(@PathVariable Long id, @RequestBody Estoque estoqueDetails) {
        // Tenta encontrar o estoque existente.
        return estoqueRepository.findById(id)
                .map(estoque -> {
                    // Atualiza a quantidade do estoque.
                    estoque.setQuantidade(estoqueDetails.getQuantidade());
                    Estoque updatedEstoque = estoqueRepository.save(estoque);
                    return ResponseEntity.ok(updatedEstoque);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}