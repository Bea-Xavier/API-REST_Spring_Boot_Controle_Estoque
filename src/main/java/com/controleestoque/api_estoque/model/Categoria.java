package com.controleestoque.api_estoque.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    // --- Relacionamento 1:N (One-To-Many) ---
    // É o lado '1' do relacionamento. 'mappedBy' aponta para o campo em Produto. 
    
    // @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    // private List<Produto> produtos;

    // Construtores, Getters e Setters...
    public Categoria() {}

    // public Categoria(String nome, List<Produto> produtos) {
    //     this.nome = nome;
    //     this.produtos = produtos;
    // }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    // public List<Produto> geProdutos() { return produtos; }
    // public void setProdutos(List<Produtos> produtos) { this.produtos = produtos; }
    
}
