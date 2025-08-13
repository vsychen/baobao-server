package com.baobao.controller;

import com.baobao.dto.ProductRequest;
import com.baobao.dto.ProductResponse;
import com.baobao.model.Ingredients;
import com.baobao.model.Product;
import com.baobao.model.Property;
import com.baobao.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductResponse> listar() {
        return productService.listarProdutos();
    }

    @PostMapping(value="/admin/save", consumes={"multipart/form-data"})
    public ResponseEntity<ProductResponse> novoProduto(
            @RequestPart("data") ProductRequest request,
            @RequestPart("image") MultipartFile imageFile)
            throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.criarProduto(request, imageFile));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> procurarProduto(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.buscarProduto(id));
    }

    @PutMapping(value="/admin/save/{id}", consumes={"multipart/form-data"})
    public ResponseEntity<ProductResponse> atualizarProduto(
            @PathVariable UUID id,
            @RequestPart("data") ProductRequest request,
            @RequestPart("image") MultipartFile imageFile)
            throws IOException {
        return ResponseEntity.ok(productService.atualizarProduto(id, request, imageFile));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable UUID id) {
        productService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImagemProduto(@PathVariable UUID id) {
        byte[] imageData = productService.buscarImagemProduto(id);
        String tipo = productService.buscarTipoImagem(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(tipo))
                .body(imageData);
    }

    private ProductResponse toProductResponse(Product produto) {
        ProductResponse response = new ProductResponse();
        response.setId(produto.getId());
        response.setNome(produto.getNome());
        response.setCategoria(produto.getCategoria());
        response.setDescricao(produto.getDescricao());
        response.setPreco(produto.getPreco());
        response.setDisponivel(produto.isDisponivel());
        response.setImageUrl("/products/" + produto.getId() + "/image");
        response.setImageType(produto.getImagemtipo());
        response.setCaracteristicas(
                produto.getCaracteristicas() == null ? Set.of() :
                        produto.getCaracteristicas().stream()
                                .map(Property::getNome)
                                .collect(Collectors.toSet())
        );
        response.setIngredientes(
                produto.getIngredientes() == null ? Set.of() :
                        produto.getIngredientes().stream()
                                .map(Ingredients::getNome)
                                .collect(Collectors.toSet())
        );
        return response;
    }
}
