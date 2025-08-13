package com.baobao.model;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(schema = "bao", name = "ingredientes")
public class Ingredients {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    @ManyToMany(mappedBy = "ingredientes")
    private Set<Product> produtos;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Product> getProdutos() {
        return produtos;
    }

    public void setProdutos(Set<Product> produtos) {
        this.produtos = produtos;
    }
}
