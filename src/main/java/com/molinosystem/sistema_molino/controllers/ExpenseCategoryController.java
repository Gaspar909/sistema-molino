package com.molinosystem.sistema_molino.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.molinosystem.sistema_molino.dtos.ExpenseCategoryDto;
import com.molinosystem.sistema_molino.requests.ExpenseCategoryRequest;
import com.molinosystem.sistema_molino.services.ExpenseCategoryService;

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
@RequestMapping("/api/expense-category")
@RequiredArgsConstructor 
public class ExpenseCategoryController {
    private final ExpenseCategoryService expenseCategoryService;

    @GetMapping
    public ResponseEntity<Page<ExpenseCategoryDto>> getAllCategories(
        @RequestParam(defaultValue = "0") int page,  
        @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(expenseCategoryService.getAllExpenseCategory(page, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseCategoryDto> getExpenseCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseCategoryService.getExpenseCategoryById(id));
    }
    
    @PostMapping
    public ResponseEntity<ExpenseCategoryDto> createExpenseCategory (@RequestBody ExpenseCategoryRequest category) {
        return ResponseEntity.status(200).body(expenseCategoryService.createExpenseCategory(category));
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<ExpenseCategoryDto> updateExpenseCategory(@PathVariable Long id, @RequestBody ExpenseCategoryRequest upCategory) {
        return ResponseEntity.ok(expenseCategoryService.updateExpenseCategory(id, upCategory));
    }

    @PutMapping("enable/{id}")
    public ResponseEntity<ExpenseCategoryDto> enableExpenseCategory(@PathVariable Long id) {
        return ResponseEntity.ok(expenseCategoryService.enableExpenseVCategory(id));
    }

    @DeleteMapping ("disable/{id}")
    public ResponseEntity<ExpenseCategoryDto> disableExpenseCategory(@PathVariable Long id){
        return ResponseEntity.ok(expenseCategoryService.disableExpenseCategory(id));
    }

    @DeleteMapping ("delete/{id}")
    public ResponseEntity<ExpenseCategoryDto> deleteExpenseCategory(@PathVariable Long id){
        return ResponseEntity.ok(expenseCategoryService.deleteExpenseCategory(id));
    }
}
