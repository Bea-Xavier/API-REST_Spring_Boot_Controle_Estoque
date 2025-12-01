package com.controleestoque.api_estoque.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.controleestoque.api_estoque.model.Produto;
import com.controleestoque.api_estoque.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;

import com.controleestoque.api_estoque.repository.CategoriaRepository;
import com.controleestoque.api_estoque.repository.FornecedorRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FornecedorRepository fornecedorRepository;
    // Estoque é geralmente via produto ou separadamente.

    // GET /api/produtos
    @GetMapping
    public List<Produto> getAllProdutos() {
        // Retorna a lista de produtos. Pode ser necessário configurar DTOs para evitar
        // loops infinitos com JSON.
        return produtoRepository.findAll();
    }

    // GET /api/produtos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        // Busca o produto pelo ID. Usa orElse para retornar 404 se não encontrado.
        return produtoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/produtos
    // Neste método, assumimos uqe Categporia e Fornecedor já existem.
    // e seus IDs são passados no corpo da requisição (ProdutoDTO seria ideal aqui).
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        // Valida e associa a categoria
        if (produto.getCategoria() == null || produto.getCategoria().getId() == null) {
            return ResponseEntity.badRequest().body(null); // Categoria é obrigatória
        }
        categoriaRepository.findById(produto.getCategoria().getId())
                .ifPresentOrElse(produto::setCategoria, () -> {
                    throw new IllegalArgumentException("Categoria não encontrada");
                });

        // Valida e associa os fornecedores
        if (produto.getFornecedores() != null && !produto.getFornecedores().isEmpty()) {
            produto.setFornecedores(produto.getFornecedores().stream()
                    .map(fornecedor -> fornecedorRepository.findById(fornecedor.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado")))
                    .collect(Collectors.toSet()));
        }

        // Configura o estoque (se necessário)
        if (produto.getEstoque() != null) {
            produto.getEstoque().setProduto(produto); // Relaciona o estoque ao produto
        }

        // Salva o produto
        Produto savedProduto = produtoRepository.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduto);
    }

    // PUT /api/produtos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produtoDetails) {
        // Tenta encontrar o produto existente.
        return produtoRepository.findById(id)
                .map(produto -> {
                    // Atualiza os dados do produto encontrado.
                    produto.setNome(produtoDetails.getNome());
                    Produto updatedProduto = produtoRepository.save(produto);
                    return ResponseEntity.ok(updatedProduto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/produtos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        // Tenta encontrar e deletar
        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        produtoRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Retorna código 204 (No Content).
    }

}
