package com.molinosystem.sistema_molino.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.molinosystem.sistema_molino.dtos.PurchaseCompleteDto;
import com.molinosystem.sistema_molino.dtos.PurchaseDto;
import com.molinosystem.sistema_molino.requests.PurchaseRequest;
import com.molinosystem.sistema_molino.services.PurchaseService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController 
@RequestMapping ("/api/purchase")
@RequiredArgsConstructor 
public class PurchaseController {
    private final PurchaseService purchaseService;

    @GetMapping("")
    public ResponseEntity<Page<PurchaseDto>> getAllPurchases(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(purchaseService.getAllPurchase(page, pageSize));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseCompleteDto> getPurchaseById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseService.getPurchaseById(id));
    }

    @PostMapping("")
    public ResponseEntity<PurchaseCompleteDto> createPurchase(@RequestBody PurchaseRequest newPurchase) {
        return ResponseEntity.status(200).body(purchaseService.createPurchase(newPurchase));
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<PurchaseCompleteDto> updatePurchase(@PathVariable Long id, @RequestBody PurchaseRequest purchaseUp) {
        return ResponseEntity.ok(purchaseService.updatePurchase(id, purchaseUp));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PurchaseCompleteDto> deletePurchase(@PathVariable Long id){
        return ResponseEntity.ok(purchaseService.deletePurchase(id));
    }
}
