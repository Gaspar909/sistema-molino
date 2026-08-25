package com.molinosystem.sistema_molino.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.molinosystem.sistema_molino.dtos.AccountMovementDto;
import com.molinosystem.sistema_molino.requests.AccountMovementRequest;
import com.molinosystem.sistema_molino.requests.AccountMovementSearchRequest;
import com.molinosystem.sistema_molino.requests.TransferAccountMovementRequest;
import com.molinosystem.sistema_molino.services.AccountMovementService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/api/account-movement")
@RequiredArgsConstructor
public class AccountMovementController {

    private final AccountMovementService accountMovementService;

    @GetMapping("")
    public ResponseEntity<Page<AccountMovementDto>> getAllAccountMovement(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam(required = true) Long accountId
    ) {
        return ResponseEntity.ok(accountMovementService.getAllAccountMovement(page, pageSize, accountId));
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<AccountMovementDto>> searchAccountMovement(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestBody(required = true) AccountMovementSearchRequest search
    ) {
        return ResponseEntity.ok(accountMovementService.searchAccountMovement(page, pageSize, search));
    }
    
    @PostMapping("")
    public ResponseEntity<AccountMovementDto> createAccountMovement(@RequestBody AccountMovementRequest accountMovementRequest) {
        return ResponseEntity.status(201).body(accountMovementService.createAccountMovement(accountMovementRequest));
    }

    @PostMapping("/transfer")
    public ResponseEntity<List<AccountMovementDto>> transferToAccount(@RequestBody TransferAccountMovementRequest request) {
        return ResponseEntity.status(200).body(accountMovementService.transferBetweenAccounts(request));
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<AccountMovementDto> deleteAccountMovement(@PathVariable Long id){
        return ResponseEntity.ok(accountMovementService.deleteAccountMovement(id));
    }
}
