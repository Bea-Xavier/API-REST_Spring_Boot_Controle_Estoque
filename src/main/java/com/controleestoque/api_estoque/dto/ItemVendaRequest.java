package com.controleestoque.api_estoque.dto;

import java.math.BigDecimal;

// DTO para requisição de ItemVenda, relação entre o produto e sua quantidade em estoque, para a realização de uma venda

public class ItemVendaRequest {
    private Long produtoId;
    private Integer quantidade;
    private BigDecimal precoUnitario;

    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
}
