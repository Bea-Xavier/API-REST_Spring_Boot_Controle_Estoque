package com.controleestoque.api_estoque.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    // POST /api/estoques
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Estoque> createEstoque(@RequestBody Estoque estoque) {
        // Verifica se o produto associado existe.
        if (estoque.getProduto() == null || estoque.getProduto().getId() == null) {
            return ResponseEntity.badRequest().build(); // Produto é obrigatório.
        }

        return produtoRepository.findById(estoque.getProduto().getId())
                .map(produto -> {
                    estoque.setProduto(produto); // Associa o produto gerenciado.
                    Estoque savedEstoque = estoqueRepository.save(estoque);
                    return ResponseEntity.status(HttpStatus.CREATED).body(savedEstoque);
                })
                .orElse(ResponseEntity.badRequest().build());
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

    // DELETE /api/estoques/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstoque(@PathVariable Long id) {
        // Tenta encontrar e deletar o estoque.
        if (!estoqueRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        estoqueRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Retorna código 204 (No Content).
    }
}