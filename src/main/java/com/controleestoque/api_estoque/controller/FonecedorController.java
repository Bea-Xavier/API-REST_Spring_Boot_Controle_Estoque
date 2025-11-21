package com.controleestoque.api_estoque.controller;

import java.util.List;

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

import com.controleestoque.api_estoque.model.Fornecedor;
import com.controleestoque.api_estoque.repository.FornecedorRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fornecedores")
@RequiredArgsConstructor
public class FonecedorController {
    
    private final FornecedorRepository fornecedorRepository;

    @GetMapping
    public List<Fornecedor> getAllFornecedores() {
        return fornecedorRepository.findAll();
    }

    // GET /api/fornecedores/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> getFornecedorById(@PathVariable Long id) {
        // Busca a categoria pelo ID. Usa orElse para retornar 404 se não encontrada.
        return fornecedorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Retorna código 201 (Created).
    public Fornecedor createFonecedor(@RequestBody Fornecedor fornecedor) {
        // Salva um novo fornecedor no banco de dados.
        return fornecedorRepository.save(fornecedor);
    }

    // PUT /api/fornecedores/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Fornecedor> updateFornecedor(@PathVariable Long id, @RequestBody Fornecedor fornecedorDetails) {
        // Tenta encontrar o fornecedor existente.
        return fornecedorRepository.findById(id)
                .map(fornecedor -> {
                    // Atualiza os dados do fornecedor encontrado.
                    fornecedor.setNome(fornecedorDetails.getNome());
                    Fornecedor updatFornecedor = fornecedorRepository.save(fornecedor);
                    return ResponseEntity.ok(updatFornecedor);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/fornecedores/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFornecedor(@PathVariable Long id) {
        // Tenta encontrar e deletar
        if (!fornecedorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        fornecedorRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Retorna código 204 (No Content).
    }

}
