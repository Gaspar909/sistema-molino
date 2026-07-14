package com.molinosystem.sistema_molino.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.molinosystem.sistema_molino.dtos.ProductDto;
import com.molinosystem.sistema_molino.requests.ProductRequest;
import com.molinosystem.sistema_molino.requests.ProductSearchRequest;
import com.molinosystem.sistema_molino.services.ProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("")
    public ResponseEntity<Page<ProductDto>> getAllProducts(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(productService.getAllProducts(page, pageSize));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@RequestParam Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> searchProducts(@RequestBody ProductSearchRequest searchRequest) {
        return ResponseEntity.ok(productService.searchProduct(searchRequest));
    }
    
    @PostMapping("")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductRequest preodRequest) {
        return ResponseEntity.status(200).body(productService.createProduct(preodRequest));
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.updateProduct(id, productRequest));
    }

    @PutMapping("enable/{id}")
    public ResponseEntity<ProductDto>  enableProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.enableProduct(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> disableProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.disableProduct(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
