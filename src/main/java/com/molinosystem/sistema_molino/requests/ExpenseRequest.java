package com.molinosystem.sistema_molino.requests;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor 
@AllArgsConstructor 
@Setter @Getter 
public class ExpenseRequest {
    private Long idCategory;

    private String description;

    private BigDecimal expenseTotal;

    private Long accountId;
}
