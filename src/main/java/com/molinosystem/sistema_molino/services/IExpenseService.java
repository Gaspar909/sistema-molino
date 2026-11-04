package com.molinosystem.sistema_molino.services;

import org.springframework.data.domain.Page;

import com.molinosystem.sistema_molino.dtos.ExpenseDto;
import com.molinosystem.sistema_molino.entities.Expense;
import com.molinosystem.sistema_molino.requests.ExpenseRequest;
import com.molinosystem.sistema_molino.requests.ExpenseSearchRequest;

public interface IExpenseService  {
    ExpenseDto createExpense(ExpenseRequest expenseRequest);
    Page<ExpenseDto> getAllExpense(int page, int pageSize);
    Page<ExpenseDto> searchExpense(int page, int pageSize, ExpenseSearchRequest search);
    ExpenseDto getExpseneById(Long id);
    Expense getExpenseByIdEntity(Long id);
    ExpenseDto updateExpense(Long id, ExpenseRequest upExpense);
    ExpenseDto deleteExpense(Long id);
}
