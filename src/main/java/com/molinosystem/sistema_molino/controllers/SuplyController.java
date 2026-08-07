package com.molinosystem.sistema_molino.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.molinosystem.sistema_molino.dtos.SuplyDto;
import com.molinosystem.sistema_molino.requests.SuplyRequest;
import com.molinosystem.sistema_molino.services.SuplyService;

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
@RequestMapping("/api/suply")
@RequiredArgsConstructor
public class SuplyController {
    private final SuplyService suplyService;

    @GetMapping("")
    public ResponseEntity<Page<SuplyDto>> getAllSuplies(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(suplyService.getAllSuplies(page, pageSize));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SuplyDto> getSuplyById(@PathVariable Long id) {
        return ResponseEntity.ok(suplyService.getSuplyById(id));
    }

    @PostMapping("")
    public ResponseEntity<SuplyDto> createSuply(@RequestBody SuplyRequest suplyRequest) {
        return ResponseEntity.status(200).body(suplyService.createSuply(suplyRequest));
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<SuplyDto> updateSuply(@PathVariable Long id, @RequestBody SuplyRequest upSuply) {
        return ResponseEntity.ok(suplyService.updateSuply(id, upSuply));
    }

    @PutMapping("enable/{id}")
    public ResponseEntity<SuplyDto> enableSuply(@PathVariable Long id) {
        return ResponseEntity.ok(suplyService.enableSuply(id));
    }

    @DeleteMapping("disable/{id}")
    public ResponseEntity<SuplyDto> disableSuply (@PathVariable Long id){
        return ResponseEntity.ok(suplyService.disableSuply(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuplyDto> deleteSuply(@PathVariable Long id){
        return ResponseEntity.ok(suplyService.deleteSuply(id));
    }
}
