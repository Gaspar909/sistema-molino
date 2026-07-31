package com.molinosystem.sistema_molino.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.molinosystem.sistema_molino.dtos.SaleCompleteDto;
import com.molinosystem.sistema_molino.dtos.SaleDto;
import com.molinosystem.sistema_molino.requests.SaleRequest;
import com.molinosystem.sistema_molino.services.SaleService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/api/sale")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;

    @GetMapping("")
    public ResponseEntity<Page<SaleDto>> getSale(@RequestParam int page, int pageSize) {
        return ResponseEntity.ok(saleService.getAllSales(page, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleCompleteDto> getSaleComplete(@RequestParam Long id) {
        return ResponseEntity.ok(saleService.getSaleComplete(id));
    }
    
    @PostMapping("")
    public ResponseEntity<SaleDto> createSale(@RequestBody SaleRequest newSale) {
        return ResponseEntity.status(200).body(saleService.createSale(newSale));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SaleCompleteDto> updateSale(@RequestParam Long id, @RequestBody SaleRequest upSale) {
        return ResponseEntity.ok(saleService.updateSale(id, upSale));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<SaleDto> deleteSale(@RequestParam Long id){
        return ResponseEntity.ok(saleService.deleteSale(id));
    }
}
