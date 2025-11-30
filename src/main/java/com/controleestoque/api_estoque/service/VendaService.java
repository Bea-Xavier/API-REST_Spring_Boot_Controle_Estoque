package com.controleestoque.api_estoque.service;

// Lógica para gerenciar o processo de venda, incluindo validação de cliente, verificação e baixa de estoque, e registro da venda

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.RequiredArgsConstructor;

import com.controleestoque.api_estoque.dto.VendaRequest;
import com.controleestoque.api_estoque.dto.ItemVendaRequest;
import com.controleestoque.api_estoque.model.*;
import com.controleestoque.api_estoque.repository.*;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;
    private final ClienteRepository clienteRepository;

    @Transactional
    public Venda registrarVenda(VendaRequest req) {
        // 1) valida cliente
        Cliente cliente = clienteRepository.findById(req.getClienteId())
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Cliente não encontrado"));

        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setData(LocalDate.now().toString());
        venda.setValorTotal(BigDecimal.ZERO);

        BigDecimal total = BigDecimal.ZERO;

        // 2) para cada item: verifica estoque, baixa, cria ItensVenda
        for (ItemVendaRequest itemReq : req.getItens()) {
            Produto produto = produtoRepository.findById(itemReq.getProdutoId())
                    .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST,
                            "Produto não encontrado: " + itemReq.getProdutoId()));

            Estoque estoque = estoqueRepository.findByProdutoId(produto.getId())
                    .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST,
                            "Estoque não encontrado para produto: " + produto.getId()));

            if (itemReq.getQuantidade() == null || itemReq.getQuantidade() <= 0) {
                throw new ResponseStatusException(BAD_REQUEST, "Quantidade inválida para produto: " + produto.getId());
            }

            if (estoque.getQuantidade() < itemReq.getQuantidade()) {
                // lança exception -> rollback automático por @Transactional
                throw new ResponseStatusException(BAD_REQUEST,
                        "Estoque insuficiente para produto: " + produto.getNome() + " (id=" + produto.getId() + ")");
            }

            // baixa no estoque
            estoque.setQuantidade(estoque.getQuantidade() - itemReq.getQuantidade());
            estoqueRepository.save(estoque);

            BigDecimal precoUnit = produto.getPreco();
            BigDecimal itemTotal = precoUnit.multiply(BigDecimal.valueOf(itemReq.getQuantidade()));
            total = total.add(itemTotal);

            // cria item venda
            ItensVenda item = new ItensVenda();
            item.setProduto(produto);
            item.setQuantidade(itemReq.getQuantidade());
            item.setPrecoUnitario(precoUnit);
            item.setVenda(venda);

            venda.getItensVenda().add(item);
        }

        venda.setValorTotal(total);

        // salva venda (cascade salva os ItensVenda)
        Venda saved = vendaRepository.save(venda);

        return saved;
    }
}
