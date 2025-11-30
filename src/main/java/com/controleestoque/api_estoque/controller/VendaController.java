package com.controleestoque.api_estoque.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.controleestoque.api_estoque.dto.VendaRequest;
import com.controleestoque.api_estoque.model.Venda;
import com.controleestoque.api_estoque.repository.VendaRepository;
import com.controleestoque.api_estoque.service.VendaService;

@RestController
@RequestMapping("/api/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;
    private final VendaRepository vendaRepository;

    @PostMapping
    public ResponseEntity<Venda> registrarVenda(@RequestBody VendaRequest req) {
        Venda venda = vendaService.registrarVenda(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(venda);
    }

    @GetMapping
    public List<Venda> getAll() {
        return vendaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> getById(@PathVariable Long id) {
        return vendaRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
