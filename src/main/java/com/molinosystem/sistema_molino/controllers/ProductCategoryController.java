package com.molinosystem.sistema_molino.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.molinosystem.sistema_molino.dtos.ProductCategoryDto;
import com.molinosystem.sistema_molino.requests.ProductCategoryRequest;
import com.molinosystem.sistema_molino.services.ProductCategoryService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("api/product-category")
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;

    @GetMapping
    public ResponseEntity<Page<ProductCategoryDto>> getProductCategory(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(productCategoryService.getAllProductCategory(page, pageSize));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryDto> getProductCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(productCategoryService.getProductCategoryById(id));
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<ProductCategoryDto>> searchProductCategory(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam String search,
        @RequestParam(required = false) Boolean active
    ){
        return ResponseEntity.ok(productCategoryService.searchProductCategory(page, pageSize, search, active));
    }

    @PostMapping("")
    public ResponseEntity<ProductCategoryDto> createProductCategory(@RequestBody ProductCategoryRequest newPorductCategory) {
        return ResponseEntity.status(201).body(productCategoryService.createProductCategory(newPorductCategory));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ProductCategoryDto> updateProductCategory(@PathVariable Long id, @RequestBody ProductCategoryRequest upProductCategory) {
        return ResponseEntity.ok(productCategoryService.updateProductCategory(id, upProductCategory));
    }
    
    @PutMapping("enable/{id}")
    public ResponseEntity<ProductCategoryDto> enableProductCategory(@PathVariable Long id) {
        return ResponseEntity.ok(productCategoryService.enableProductCategory(id));
    }

    @DeleteMapping("disable/{id}")
    public ResponseEntity<ProductCategoryDto> disableProductCategory(@PathVariable Long id){
        return ResponseEntity.ok(productCategoryService.disableProductCategory(id));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ProductCategoryDto> deleteProductCategory(@PathVariable Long id){
        return ResponseEntity.ok(productCategoryService.deleteProductCategory(id));
    }
}
