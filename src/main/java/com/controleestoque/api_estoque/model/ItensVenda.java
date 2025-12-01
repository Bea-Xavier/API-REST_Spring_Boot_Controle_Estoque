package com.controleestoque.api_estoque.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_itens_venda")
public class ItensVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "produto_id", nullable = false)
    // Se você quiser que o produto não traga itens ao serializar, pode usar JsonBackReference aqui.
    @JsonBackReference(value = "produto-itens")
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venda_id", nullable = false)
    @JsonBackReference(value = "venda-itens")
    private Venda venda;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unitario", precision = 18, scale = 2, nullable = false)
    private BigDecimal precoUnitario;

    public ItensVenda() {}

    public ItensVenda(Produto produto, Venda venda, Integer quantidade, BigDecimal precoUnitario) {
        this.produto = produto;
        this.venda = venda;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    // getters / setters
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
