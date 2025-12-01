package com.controleestoque.api_estoque.model;

import java.math.BigDecimal;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinTable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "tb_produtos")
public class Produto { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Chave primária (PK).

    private String nome;

    private BigDecimal preco;

    // --- Relacionamento 1:1 (One-To-One) ---
    // Mapeamento: Um produto tem UM registro de estoque (e vice-versa).
    // 'mappedBy' indica que a chave estrangeira está na classe Estoque.
    // Cascade.ALL: Operações (como salvar) em Produto afetam Estoque.
    @OneToOne(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Estoque estoque;

    // --- Relacionamento N:1 (Many-To-One) ---
    // Mapeamento: Muitos produtos têm UMA categoria.
    // É o lado 'N' (Muitos), que contém a chave estrangeira (FK).
    @ManyToOne(fetch = FetchType.LAZY) // LAZY: Carrega a categoria apenas quando for solicitada.
    @JoinColumn(name = "categoria_id", nullable = false) // Define a FK na tabela tb_produtos.
    private Categoria categoria;

    // --- Relacionamento N:M (Many-To-Many) ---
    // Mapeamento: Muitos produtos têm MUITOS fornecedores (e vice-versa).
    // Define a tabela intermediária tb_produto_fornecedor e as colunas de união.
    @ManyToMany
    @JoinTable(
        name = "tb_produto_fornecedor", // Nome da tabela de junção.
        joinColumns = @JoinColumn(name = "produto_id"), // FK desta entidade na tabela de junção.
        inverseJoinColumns = @JoinColumn(name = "fornecedor_id") // FK da outra entidade.
    )
    private Set<Fornecedor> fornecedores;

    // Relação de produtos itensVenda
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ItensVenda> itensVendas;

    // Construtores, Getters e Setters...
    public Produto() {}

    public Produto(String nome, BigDecimal preco, Estoque estoque, Categoria categoria, Set<Fornecedor> fornecedores, Set<ItensVenda> itensVendas) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.categoria = categoria;
        this.fornecedores = fornecedores;
        this.itensVendas = itensVendas;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public Estoque getEstoque() { return estoque; }
    public void setEstoque(Estoque estoque) { this.estoque = estoque; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public Set<Fornecedor> getFornecedores() { return fornecedores; }
    public void setFornecedores(Set<Fornecedor> fornecedores) { this.fornecedores = fornecedores; }
    public Set<ItensVenda> getItensVendas() { return itensVendas; }
    public void setItensVendas(Set<ItensVenda> itensVendas) { this.itensVendas = itensVendas; }
    
}
