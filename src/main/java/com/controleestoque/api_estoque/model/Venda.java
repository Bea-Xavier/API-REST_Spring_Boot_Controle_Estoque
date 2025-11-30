package com.controleestoque.api_estoque.model;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_vendas")
public class Venda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String data;
    private BigDecimal valorTotal;

    // Relação com clientes
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // Relação com o itensVenda
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ItensVenda> itensVenda;

    public Venda() {}

    public Venda(String data, BigDecimal valorTotal, Set<Produto> produtos, Cliente cliente) {
        this.data = data;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Set<ItensVenda> getItensVenda() { return itensVenda; }
    public void setItensVenda(Set<ItensVenda> itensVenda) { this.itensVenda = itensVenda; }

    // Helper 
    public void addItem(ItensVenda item) {
        itensVenda.add(item);
        item.setVenda(this);
    }

    public void removeItem(ItensVenda item) {
        itensVenda.remove(item);
        item.setVenda(null);
    }
    
}
