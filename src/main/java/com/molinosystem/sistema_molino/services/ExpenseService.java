package com.molinosystem.sistema_molino.services;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.molinosystem.sistema_molino.dtos.ExpenseDto;
import com.molinosystem.sistema_molino.entities.Account;
import com.molinosystem.sistema_molino.entities.CategoryExpense;
import com.molinosystem.sistema_molino.entities.Expense;
import com.molinosystem.sistema_molino.enums.MovementType;
import com.molinosystem.sistema_molino.exceptions.BadRequestException;
import com.molinosystem.sistema_molino.exceptions.NoFoundException;
import com.molinosystem.sistema_molino.mappers.Mapper;
import com.molinosystem.sistema_molino.repositories.ExpenseRepository;
import com.molinosystem.sistema_molino.requests.AccountMovementRequest;
import com.molinosystem.sistema_molino.requests.ExpenseRequest;
import com.molinosystem.sistema_molino.requests.ExpenseSearchRequest;
import com.molinosystem.sistema_molino.requests.TransferAccountMovementRequest;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ExpenseService implements IExpenseService {
    private final ExpenseRepository expenseRepository;

    private final ExpenseCategoryService expenseCategoryService;
    private final AccountService accountService;
    private final AccountMovementService accountMovementService;

    @Override 
    @Transactional 
    public ExpenseDto createExpense(ExpenseRequest expenseRequest){
        if (expenseRequest.getIdCategory() == null || expenseRequest.getIdCategory() == 0)
            throw new BadRequestException("Category is required");

        if (expenseRequest.getDescription() == null || expenseRequest.getDescription().trim().isEmpty())
            throw new BadRequestException("Description is required");

        if (expenseRequest.getExpenseTotal() == null || expenseRequest.getExpenseTotal().equals(BigDecimal.ZERO))
            throw new BadRequestException("Total is required");

        if (expenseRequest.getAccountId() == null || expenseRequest.getAccountId() == 0)
            throw new BadRequestException("Account is required");
        
        CategoryExpense category = expenseCategoryService.getExpenseCategoryEntity(expenseRequest.getIdCategory());

        Account account = accountService.getAccountEntityById(expenseRequest.getAccountId());

        Expense newExpense = Expense.builder()
        .id(null)
        .date(null)
        .category(category)
        .description(expenseRequest.getDescription())
        .expenseTotal(expenseRequest.getExpenseTotal())
        .account(account)
        .build();

        AccountMovementRequest movementRequest = AccountMovementRequest.builder()
        .accountId(expenseRequest.getAccountId())
        .amount(expenseRequest.getExpenseTotal())
        .movementType(MovementType.EXPENSE)
        .description( "Expense: " + expenseRequest.getDescription())
        .createdAt(null)
        .userId(null)
        .build();

        accountMovementService.createAccountMovement(movementRequest);

        return Mapper.toDTO(expenseRepository.save(newExpense));
    }

    @Override 
    public Page<ExpenseDto> serchExpense(int page, int pageSize, ExpenseSearchRequest search){
        Specification<Expense> specification = Specification.where((root, query, cb) -> cb.conjunction());

        if (search.getCategory() != null && search.getCategory() > 0) {
            specification = specification.and( (root, query, cb) -> {
                return cb.equal(root.get("category").get("id"), search.getCategory());
            }
        );
        }

        if (search.getStartDate() != null) {
            specification = specification.and((root, query, cb) -> {
                return  cb.greaterThanOrEqualTo(root.get("date"), search.getStartDate());
            });
        }

        if (search.getEndDate() != null) {
            specification = specification.and((root, query, cb) -> {
                return  cb.lessThanOrEqualTo(root.get("date"), search.getStartDate());
            });
        }

        if (search.getDescription() != null && !search.getDescription().trim().isEmpty()) {
            specification = specification.and((root, query, cb) -> {
                return cb.like(cb.lower(root.get("description")), search.getDescription().toLowerCase());
            });
        }
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Expense> result = expenseRepository.findAll(specification, pageable);

        return result.map(Mapper::toDTO);
    }

    @Override
    public Page<ExpenseDto> getAllExpense(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Expense> result = expenseRepository.findAll(pageable);

        return result.map(Mapper::toDTO);
    }

    @Override
    public ExpenseDto getExpseneById(Long id) {
        Expense expense = expenseRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Expense does not exist."));

        return Mapper.toDTO(expense);
    }

    @Override
    public Expense getExpenseByIdEntity(Long id) {
        Expense expense = expenseRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Expense does not exist."));

        return expense;
    }

    @Override
    @Transactional
    public ExpenseDto updateExpense(Long id, ExpenseRequest upExpense) {
        Expense expense = expenseRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Expense does not exist."));

        if (upExpense.getDescription() != null && !upExpense.getDescription().trim().isEmpty())
            expense.setDescription(upExpense.getDescription());

        if(upExpense.getIdCategory() != null && upExpense.getIdCategory() > 0 && upExpense.getIdCategory() != expense.getCategory().getId())
            expense.setCategory(expenseCategoryService.getExpenseCategoryEntity(upExpense.getIdCategory()));

        if (upExpense.getExpenseTotal() != null && upExpense.getExpenseTotal().compareTo(BigDecimal.ZERO) > 0 && upExpense.getExpenseTotal().compareTo(expense.getExpenseTotal()) != 0){
            BigDecimal diff = upExpense.getExpenseTotal().subtract(expense.getExpenseTotal());

            AccountMovementRequest movementRequest = AccountMovementRequest.builder()
            .accountId(expense.getAccount().getId())
            .amount(diff.abs())
            .movementType(diff.compareTo(BigDecimal.ZERO) > 0 ? MovementType.INCOME : MovementType.EXPENSE)
            .description("Update Expense: " + expense.getDescription())
            .createdAt(null)
            .userId(null)
            .build();
            
            accountMovementService.createAccountMovement(movementRequest);
        }


        if (upExpense.getAccountId() != null && upExpense.getAccountId() > 0 && upExpense.getAccountId() != expense.getAccount().getId()){
            TransferAccountMovementRequest tranferRequest = TransferAccountMovementRequest.builder()
            .sourceAccountId(expense.getAccount().getId())
            .destinationAccountId(upExpense.getAccountId())
            .amount(expense.getExpenseTotal())
            .description("Update Account of Expense: " + expense.getDescription())
            .build();

            accountMovementService.transferBetweenAccounts(tranferRequest);
        }
        
        return Mapper.toDTO(expenseRepository.save(expense));
    }

    @Override
    public ExpenseDto deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Expense does not exist."));

        expenseRepository.delete(expense);

        return Mapper.toDTO(expense);
    }
}
