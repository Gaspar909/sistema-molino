package com.molinosystem.sistema_molino.services;

import org.springframework.data.domain.Page;

import com.molinosystem.sistema_molino.dtos.ExpenseCategoryDto;
import com.molinosystem.sistema_molino.entities.CategoryExpense;
import com.molinosystem.sistema_molino.requests.ExpenseCategoryRequest;

public interface IExpenseCategoryService {
    ExpenseCategoryDto createExpenseCategory(ExpenseCategoryRequest expenseCategory);

    Page<ExpenseCategoryDto> getAllExpenseCategory(int page, int pageSize);

    ExpenseCategoryDto getExpenseCategoryById (Long id);

    CategoryExpense getExpenseCategoryEntity(Long id);

    ExpenseCategoryDto updateExpenseCategory(Long id, ExpenseCategoryRequest upCategory);

    ExpenseCategoryDto enableExpenseVCategory(Long id);

    ExpenseCategoryDto disableExpenseCategory (Long id);
}
