package com.molinosystem.sistema_molino.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.molinosystem.sistema_molino.dtos.AccountDto;
import com.molinosystem.sistema_molino.requests.AccountRequest;
import com.molinosystem.sistema_molino.services.AccountService;

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
@RequestMapping("api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("")
    public ResponseEntity<Page<AccountDto>> getAllAcconts(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(accountService.getAllAccounts(page, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping("/serch")
    public ResponseEntity<Page<AccountDto>> searchAccount(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam String search,
        @RequestParam Boolean isActive
    ) {
        return ResponseEntity.ok(accountService.searchAccount(page, pageSize, search, isActive));
    }
    
    @PostMapping("")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountRequest accountRequest) {
        return ResponseEntity.status(200).body(accountService.createAccount(accountRequest));
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id, @RequestBody AccountRequest upAccount) {
        return ResponseEntity.ok(accountService.updateAccount(id, upAccount));
    }

    @PutMapping("enable/{id}")
    public ResponseEntity<AccountDto> enableAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.enableAccount(id));
    }

    @DeleteMapping("disable/{id}")
    public ResponseEntity<AccountDto> disableAccount (@PathVariable Long id){
        return ResponseEntity.ok(accountService.disableAccount(id));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<AccountDto> deleteAccount (@PathVariable Long id){
        return ResponseEntity.ok(accountService.deleteAccount(id));
    }
}
