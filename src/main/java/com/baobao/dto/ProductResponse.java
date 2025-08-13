package com.baobao.dto;

import com.baobao.model.Product;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public class ProductResponse {
    private UUID id;
    private String nome;
    private String categoria;
    private String descricao;
    private BigDecimal preco;
    private Boolean disponivel;
    private String imageUrl; // Ex: endpoint para buscar a imagem
    private String imageType;
    private Set<String> caracteristicas;
    private Set<String> ingredientes;

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
    public Boolean getDisponivel() {
        return disponivel;
    }
    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getImageType() { return imageType; }
    public void setImageType(String imageType) { this.imageType = imageType; }
    public Set<String> getCaracteristicas() {
        return caracteristicas;
    }
    public void setCaracteristicas(Set<String> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }
    public Set<String> getIngredientes() {
        return ingredientes;
    }
    public void setIngredientes(Set<String> ingredientes) {
        this.ingredientes = ingredientes;
    }
}