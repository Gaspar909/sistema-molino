package com.molinosystem.sistema_molino.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.molinosystem.sistema_molino.dtos.ExpenseCategoryDto;
import com.molinosystem.sistema_molino.entities.CategoryExpense;
import com.molinosystem.sistema_molino.exceptions.BadRequestException;
import com.molinosystem.sistema_molino.exceptions.NoFoundException;
import com.molinosystem.sistema_molino.mappers.Mapper;
import com.molinosystem.sistema_molino.repositories.ExpenseCategoryRepository;
import com.molinosystem.sistema_molino.requests.ExpenseCategoryRequest;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ExpenseCategoryService implements IExpenseCategoryService {
    private final ExpenseCategoryRepository expenseCategoryRepository;

    @Override
    public ExpenseCategoryDto createExpenseCategory(ExpenseCategoryRequest expenseCategory) {

        if (expenseCategory == null) throw new BadRequestException("Object is required");

        if (expenseCategory.getName() == null || expenseCategory.getName().trim().isEmpty()) throw new BadRequestException("Name is required");

        if (expenseCategory.getDescription() == null || expenseCategory.getDescription().trim().isEmpty()) throw new BadRequestException("Description is required");

        CategoryExpense newCategoryExpense = CategoryExpense.builder()
        .id(null)
        .name(expenseCategory.getName())
        .description(expenseCategory.getDescription())
        .active(true)
        .build();

        return Mapper.toDTO(expenseCategoryRepository.save(newCategoryExpense));
    }

    @Override
    public Page<ExpenseCategoryDto> getAllExpenseCategory(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<CategoryExpense> result = expenseCategoryRepository.findAll(pageable);

        return result.map(Mapper::toDTO);
    }

    @Override
    public ExpenseCategoryDto getExpenseCategoryById(Long id) {
        CategoryExpense result = expenseCategoryRepository.findById(id).orElseThrow(
        () -> new NoFoundException("Category does not exist")
        );

        return Mapper.toDTO(result);
    }

    public CategoryExpense getExpenseCategoryEntity(Long id){
        CategoryExpense result = expenseCategoryRepository.findById(id).orElseThrow(
        () -> new NoFoundException("Category does not exist")
        );

        return result;
    }

    @Override
    public ExpenseCategoryDto updateExpenseCategory(Long id, ExpenseCategoryRequest upCategory) {

        CategoryExpense category = expenseCategoryRepository.findById(id).orElseThrow(
        () -> new NoFoundException("Category does not exist")
        );

        if (upCategory.getName() != null &&  !upCategory.getName().trim().isEmpty()){
            category.setName(upCategory.getName());
        }

        if(upCategory.getDescription() != null && !upCategory.getName().trim().isEmpty()){
            category.setDescription(upCategory.getDescription());
        }

        return Mapper.toDTO(expenseCategoryRepository.save(category));
    }

    @Override
    public ExpenseCategoryDto enableExpenseVCategory(Long id) {
        CategoryExpense category = expenseCategoryRepository.findById(id).orElseThrow(
        () -> new NoFoundException("Category does not exist")
        );

        if (category.getActive()) throw new BadRequestException("Category is enable alredy");

        category.setActive(true);

        return Mapper.toDTO(expenseCategoryRepository.save(category));
    }

    @Override
    public ExpenseCategoryDto disableExpenseCategory(Long id) {
        CategoryExpense category = expenseCategoryRepository.findById(id).orElseThrow(
        () -> new NoFoundException("Category does not exist")
        );

        if (!category.getActive()) throw new BadRequestException("Category is disable alredy");

        category.setActive(false);

        return Mapper.toDTO(expenseCategoryRepository.save(category));
    }

    
}

