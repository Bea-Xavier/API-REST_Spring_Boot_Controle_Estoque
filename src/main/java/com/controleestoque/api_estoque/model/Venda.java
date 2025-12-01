package com.controleestoque.api_estoque.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "tb_vendas")
public class Venda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String data;
    private BigDecimal valorTotal;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "venda-itens")
    private Set<ItensVenda> itensVenda = new HashSet<>();

    public Venda() {}

    public Venda(String data, BigDecimal valorTotal, Cliente cliente) {
        this.data = data;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
    }

    // getters / setters...
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

    public void addItem(ItensVenda item) {
        itensVenda.add(item);
        item.setVenda(this);
    }

    public void removeItem(ItensVenda item) {
        itensVenda.remove(item);
        item.setVenda(null);
    }
}
