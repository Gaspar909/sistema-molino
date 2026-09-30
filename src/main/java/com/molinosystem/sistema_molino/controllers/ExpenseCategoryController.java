package com.molinosystem.sistema_molino.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.molinosystem.sistema_molino.dtos.ExpenseCategoryDto;
import com.molinosystem.sistema_molino.requests.ExpenseCategoryRequest;
import com.molinosystem.sistema_molino.services.ExpenseCategoryService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController 
@RequestMapping("/api/expense-category")
@RequiredArgsConstructor 
public class ExpenseCategoryController {
    private final ExpenseCategoryService expenseCategoryService;

    @GetMapping("")
    public ResponseEntity<Page<ExpenseCategoryDto>> getAllCategories(
        @RequestParam(defaultValue = "0") int page,  
        @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(expenseCategoryService.getAllExpenseCategory(page, pageSize));
    }
    
    @PostMapping("path")
    public ResponseEntity<ExpenseCategoryDto> createExpenseCategory (@RequestBody ExpenseCategoryRequest category) {
        return ResponseEntity.status(200).body(expenseCategoryService.createExpenseCategory(category));
    }
    
}
