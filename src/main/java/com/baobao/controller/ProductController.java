package com.baobao.controller;

import com.baobao.dto.ProductRequest;
import com.baobao.dto.ProductResponse;
import com.baobao.model.Product;
import com.baobao.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> listar() {
        return productRepository.findAll();
    }

    @PostMapping(value="/admin/save", consumes={"multipart/form-data"})
    public ResponseEntity<Product> novoProduto(
            @RequestPart("data") ProductRequest request,
            @RequestPart("image") MultipartFile imageFile)
            throws IOException {
        Product novo = new Product();
        novo.setNome(request.getNome());
        novo.setCategoria(request.getCategoria());
        novo.setDescricao(request.getDescricao());
        novo.setPreco(request.getPreco());
        novo.setDisponivel(request.isDisponivel());

        if (imageFile != null && !imageFile.isEmpty()) {
            novo.setImagem(imageFile.getBytes());
            String tipo = imageFile.getContentType();
            if (tipo == null || tipo.isBlank()) tipo = "application.octet-stream";
            novo.setImagemtipo(tipo);
        }

        Product produto = productRepository.save(novo);
        return new ResponseEntity<>(produto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> procurarProduto(@PathVariable UUID id) {
        Optional<Product> opt = productRepository.findById(id);
        return opt.map(value -> ResponseEntity.ok()
                        .body(toProductResponse(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value="/admin/save/{id}", consumes={"multipart/form-data"})
    public ResponseEntity<ProductResponse> atualizarProduto(
            @PathVariable UUID id,
            @RequestPart("data") ProductRequest request,
            @RequestPart("image") MultipartFile imageFile)
            throws IOException {
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product velho = opt.get();
        velho.setNome(request.getNome());
        velho.setCategoria(request.getCategoria());
        velho.setDescricao(request.getDescricao());
        velho.setPreco(request.getPreco());
        velho.setDisponivel(request.isDisponivel());

        if (imageFile != null && !imageFile.isEmpty()) {
            velho.setImagem(imageFile.getBytes());
            String tipo = imageFile.getContentType();
            if (tipo == null || tipo.isBlank()) tipo = "application.octet-stream";
            velho.setImagemtipo(tipo);
        }

        Product produto = productRepository.save(velho);
        return ResponseEntity.ok()
                .body(toProductResponse(produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable UUID id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImagemProduto(@PathVariable UUID id) {
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isEmpty() || opt.get().getImagem() == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] imageData = opt.get().getImagem();
        String tipo = opt.get().getImagemtipo();
        if (tipo == null || tipo.isBlank()) tipo = "application/octet-stream";
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(tipo)).body(imageData);
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
        return response;
    }
}
