package com.molinosystem.sistema_molino.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.molinosystem.sistema_molino.dtos.ExpenseDto;
import com.molinosystem.sistema_molino.requests.ExpenseRequest;
import com.molinosystem.sistema_molino.services.ExpenseService;

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
@RequestMapping("/api/expenses")
@RequiredArgsConstructor 
public class ExpenseController {
    private final ExpenseService expenseService;

    @GetMapping("")
    public ResponseEntity<Page<ExpenseDto>> getAllExpense (
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam (defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(expenseService.getAllExpense(page, pageSize));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.getExpseneById(id));
    }

    @PostMapping("")
    public ResponseEntity<ExpenseDto> createExpense(@RequestBody ExpenseRequest newExpense) {
        return ResponseEntity.status(200).body(expenseService.createExpense(newExpense));
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable Long id, @RequestBody ExpenseRequest uExpense) {
        return ResponseEntity.ok(expenseService.updateExpense(id, uExpense));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ExpenseDto> deleteExpense(@PathVariable Long id){
        return ResponseEntity.ok(expenseService.deleteExpense(id));
    }
}
