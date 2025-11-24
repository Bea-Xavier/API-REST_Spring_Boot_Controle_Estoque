package com.controleestoque.api_estoque.model;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_vendas")
public class Venda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String data;
    private BigDecimal valorTotal;

    //Vendas com produtos
    @ManyToMany
    @JoinTable(
        name = "tb_itensVenda", // Nome da tabela de junção.
        joinColumns = @JoinColumn(name = "venda_id"), // FK desta entidade na tabela de junção.
        inverseJoinColumns = @JoinColumn(name = "produto_id") // FK da outra entidade.
    )
    private Set<Produto> produtos;

    @ManyToOne(fetch = FetchType.LAZY) // LAZY: Carrega a categoria apenas quando for solicitada.
    @JoinColumn(name = "cliente_id", nullable = false) // Define a FK na tabela tb_produtos.
    private Cliente cliente;

    public Venda() {}

    public Venda(String data, BigDecimal valorTotal, Set<Produto> produtos, Cliente cliente) {
        this.data = data;
        this.valorTotal = valorTotal;
        this.produtos = produtos;
        this.cliente = cliente;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    public Set<Produto> getProdutos() { return produtos; }
    public void setProdutos(Set<Produto> produtos) { this.produtos = produtos; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    
}
