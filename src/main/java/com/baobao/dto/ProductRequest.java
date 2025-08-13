package com.baobao.dto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public class ProductRequest {
    private UUID id;
    private String nome;
    private String categoria;
    private String descricao;
    private BigDecimal preco;
    private Boolean disponivel;
    private Set<UUID> caracteristicas;
    private Set<UUID> ingredientes;

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
    public Boolean isDisponivel() {
        return disponivel;
    }
    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }
    public Set<UUID> getCaracteristicas() {
        return caracteristicas;
    }
    public void setCaracteristicas(Set<UUID> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }
    public Set<UUID> getIngredientes() {
        return ingredientes;
    }
    public void setIngredientes(Set<UUID> ingredientes) {
        this.ingredientes = ingredientes;
    }
}