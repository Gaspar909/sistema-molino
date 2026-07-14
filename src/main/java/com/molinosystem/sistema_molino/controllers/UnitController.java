package com.molinosystem.sistema_molino.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.molinosystem.sistema_molino.dtos.UnitDto;
import com.molinosystem.sistema_molino.requests.UnitRequest;
import com.molinosystem.sistema_molino.services.UnitService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/unit")
@RequiredArgsConstructor
public class UnitController {
    private final UnitService unitService;

    @GetMapping
    public ResponseEntity<Page<UnitDto>> getUnits(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int pageSize) {
        
        return ResponseEntity.ok(unitService.getAllUnit(page, pageSize));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UnitDto> getMethodName(@PathVariable Long id) {
        return ResponseEntity.ok(unitService.getUnitById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UnitDto>> getSearchUnit(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam String search) {
        return ResponseEntity.ok(unitService.searchUnit(search, page, pageSize));
    }
    
    @PostMapping
    public ResponseEntity<UnitDto> createUnit(@RequestBody UnitRequest newUnit) {
        return ResponseEntity.status(200).body(unitService.createUnitDto(newUnit));
    }
    
    @PutMapping
    public ResponseEntity<UnitDto> updateUnit (@PathVariable Long id, @RequestBody UnitRequest upUnit) { 
        return ResponseEntity.ok(unitService.updateUnit(id, upUnit));
    }

    @PutMapping("enable/{id}")
    public ResponseEntity<UnitDto> enableUnit(@PathVariable Long id) {
        return ResponseEntity.ok(unitService.enableUnit(id));
    }

    @DeleteMapping("disable/{id}")
    public ResponseEntity<UnitDto> disableUnit (@PathVariable Long id){
        return ResponseEntity.ok(unitService.disableUnit(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UnitDto> deleteUnit (@PathVariable Long id){
        return ResponseEntity.ok(unitService.deleteUnit(id));
    }
}
