package com.molinosystem.sistema_molino.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.molinosystem.sistema_molino.dtos.ProductPriceDto;
import com.molinosystem.sistema_molino.requests.ProductPriceRequest;
import com.molinosystem.sistema_molino.services.ProductPriceService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/product-price")
public class ProductPriceController {

    @Autowired
    ProductPriceService productPriceService;

    @GetMapping("")
    public ResponseEntity<Page<ProductPriceDto>> getProductPriceByProductId(
        @RequestParam Long productId, 
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int pageSize
        ) {
        return ResponseEntity.ok(productPriceService.getProductPriceByProduct(productId, page, pageSize));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductPriceDto> getProductPriceById(@RequestParam Long id) {
        return ResponseEntity.ok(productPriceService.getProductPriceById(id));
    }
    
    @PostMapping("")
    public ResponseEntity<ProductPriceDto> createProductPrice(@RequestBody ProductPriceRequest newProductPrice) {
        return ResponseEntity.status(200).body(productPriceService.createProductPrice(newProductPrice));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProductPriceDto> updateProductPrice(@PathVariable Long id, @RequestBody ProductPriceRequest upProductPrice) {
        return ResponseEntity.ok(productPriceService.updateProducPrice(id, upProductPrice));
    }

    @PutMapping("enable/{id}")
    public ResponseEntity<ProductPriceDto> enableProductPrice(@PathVariable Long id) {
        return ResponseEntity.ok(productPriceService.enableProductPice(id));
    }

    @DeleteMapping("disable/{id}")
    public ResponseEntity<ProductPriceDto> disableProductPrice(@PathVariable Long id){
        return ResponseEntity.ok(productPriceService.disableProductPrice(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductPriceDto> deleteProductPrice(@PathVariable Long id){
        return ResponseEntity.ok(productPriceService.deleteProductPrice(id));
    }
}
