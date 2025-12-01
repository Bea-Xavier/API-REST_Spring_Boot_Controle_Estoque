package com.controleestoque.api_estoque.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "tb_itens_venda")
public class ItensVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relação com produto
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    // Relação com venda
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venda_id", nullable = false)
    @JsonBackReference
    private Venda venda;

    private Integer quantidade;
    private BigDecimal precoUnitario;

    public ItensVenda() {}

    public ItensVenda(Produto produto, Venda venda, Integer quantidade, BigDecimal precoUnitario) {
        this.produto = produto;
        this.venda = venda;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
    public Venda getVenda() { return venda; }
    public void setVenda(Venda venda) { this.venda = venda; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }

}
