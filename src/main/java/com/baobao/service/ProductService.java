package com.baobao.service;

import com.baobao.dto.ProductRequest;
import com.baobao.dto.ProductResponse;
import com.baobao.model.Ingredients;
import com.baobao.model.Product;
import com.baobao.model.Property;
import com.baobao.repository.IngredientsRepository;
import com.baobao.repository.ProductRepository;
import com.baobao.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private IngredientsRepository ingredientsRepository;

    public ProductResponse criarProduto(ProductRequest request, MultipartFile imageFile) throws IOException {
        Product novo = new Product();
        preencherDadosProduto(novo, request, imageFile);
        Product salvo = productRepository.save(novo);
        return toProductResponse(salvo);
    }

    public ProductResponse atualizarProduto(UUID id, ProductRequest request, MultipartFile imageFile) throws IOException {
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isEmpty()) {
            throw new NoSuchElementException("Produto não encontrado.");
        }

        Product existente = opt.get();
        preencherDadosProduto(existente, request, imageFile);
        Product salvo = productRepository.save(existente);
        return toProductResponse(salvo);
    }

    public ProductResponse buscarProduto(UUID id) {
        Product produto = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado"));
        return toProductResponse(produto);
    }

    public void deletarProduto(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("Produto não encontrado");
        }
        productRepository.deleteById(id);
    }

    public List<ProductResponse> listarProdutos() {
        return productRepository.findAll().stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());
    }

    public byte[] buscarImagemProduto(UUID id) {
        Product produto = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado"));

        if (produto.getImagem() == null) {
            throw new NoSuchElementException("Imagem não encontrada");
        }

        return produto.getImagem();
    }

    public String buscarTipoImagem(UUID id) {
        Product produto = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado"));

        String tipo = produto.getImagemtipo();
        return (tipo == null || tipo.isBlank()) ? "application/octet-stream" : tipo;
    }

    private void preencherDadosProduto(Product produto, ProductRequest request, MultipartFile imageFile) throws IOException {
        produto.setNome(request.getNome());
        produto.setCategoria(request.getCategoria());
        produto.setDescricao(request.getDescricao());
        produto.setPreco(request.getPreco());
        produto.setDisponivel(request.isDisponivel());

        if (request.getCaracteristicas() != null) {
            Set<Property> props = new HashSet<>(propertyRepository.findAllById(request.getCaracteristicas()));
            produto.setCaracteristicas(props);
        } else {
            produto.setCaracteristicas(null);
        }

        if (request.getIngredientes() != null) {
            Set<Ingredients> ings = new HashSet<>(ingredientsRepository.findAllById(request.getIngredientes()));
            produto.setIngredientes(ings);
        } else {
            produto.setIngredientes(null);
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            produto.setImagem(imageFile.getBytes());
            String tipo = imageFile.getContentType();
            if (tipo == null || tipo.isBlank()) tipo = "application/octet-stream";
            produto.setImagemtipo(tipo);
        }
    }

    private ProductResponse toProductResponse(Product produto) {
        ProductResponse response = new ProductResponse();
        response.setId(produto.getId());
        response.setNome(produto.getNome());
        response.setCategoria(produto.getCategoria());
        response.setDescricao(produto.getDescricao());
        response.setPreco(produto.getPreco());
        response.setDisponivel(produto.isDisponivel());
        response.setImageType(produto.getImagemtipo());
        response.setImageUrl("/products/" + produto.getId() + "/image");

        if (produto.getCaracteristicas() != null) {
            response.setCaracteristicas(produto.getCaracteristicas().stream()
                    .map(Property::getNome).collect(Collectors.toSet()));
        } else {
            response.setCaracteristicas(Set.of());
        }

        if (produto.getIngredientes() != null) {
            response.setIngredientes(produto.getIngredientes().stream()
                    .map(Ingredients::getNome).collect(Collectors.toSet()));
        } else {
            response.setIngredientes(Set.of());
        }

        return response;
    }
}