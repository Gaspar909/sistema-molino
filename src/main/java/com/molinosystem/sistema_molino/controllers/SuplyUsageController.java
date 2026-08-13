package com.molinosystem.sistema_molino.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.molinosystem.sistema_molino.dtos.SuplyUsageDto;
import com.molinosystem.sistema_molino.requests.SuplyUsageRequest;
import com.molinosystem.sistema_molino.requests.SuplyUsageSearchRequest;
import com.molinosystem.sistema_molino.services.SuplyUsageService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping ("api/suply-usage")
@RequiredArgsConstructor
public class SuplyUsageController {
    private final SuplyUsageService suplyUsageService;

    @GetMapping("")
    public ResponseEntity<Page<SuplyUsageDto>> getAllSuplyUsage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(suplyUsageService.getAllSuplyUsage(page, pageSize));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SuplyUsageDto> getSuplyUsageById(@PathVariable Long id) {
        return ResponseEntity.ok(suplyUsageService.getSuplyUsageById(id));
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<SuplyUsageDto>> searchSuplyUsage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam (defaultValue = "10") int pageSize,
        @RequestBody SuplyUsageSearchRequest filter) {
        return ResponseEntity.ok(suplyUsageService.searchSuplyUsage(filter, page, pageSize));
    }
    
    @PostMapping("")
    public ResponseEntity<SuplyUsageDto> createSuplyUsage(@RequestBody SuplyUsageRequest newSuplyUsage) {
        return ResponseEntity.status(200).body(suplyUsageService.createSuplyUsage(newSuplyUsage));
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<SuplyUsageDto> updateSuplyUsage(@PathVariable Long id, @RequestBody SuplyUsageRequest upSuplyUsage) {
        return ResponseEntity.ok(suplyUsageService.updateSuplyUsage(id, upSuplyUsage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuplyUsageDto> deleteSuplyUsage(@PathVariable Long id){
        return ResponseEntity.ok(suplyUsageService.deleteSuplyUsage(id));
    }
}
