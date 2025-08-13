package com.baobao.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(schema = "bao", name = "produtos")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String categoria;
    private String descricao;
    @Column(precision=12, scale=2)
    private BigDecimal preco;
    private boolean disponivel;
    @Column(name = "imagem", columnDefinition = "bytea")
    private byte[] imagem;
    private String imagemtipo;
    @ManyToMany
    @JoinTable(
            schema = "bao",
            name = "produtos_caracteristicas",
            joinColumns = @JoinColumn(name = "produtos_id"),
            inverseJoinColumns = @JoinColumn(name = "caracteristicas_id")
    )
    private Set<Property> caracteristicas;
    @ManyToMany
    @JoinTable(
            schema = "bao",
            name = "produtos_ingredientes",
            joinColumns = @JoinColumn(name = "produtos_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredientes_id")
    )
    private Set<Ingredients> ingredientes;

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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getImagemtipo() { return imagemtipo; }

    public void setImagemtipo(String imagemtipo) { this.imagemtipo = imagemtipo; }

    public Set<Property> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(Set<Property> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public Set<Ingredients> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(Set<Ingredients> ingredientes) {
        this.ingredientes = ingredientes;
    }
}
