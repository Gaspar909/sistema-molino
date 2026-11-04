package com.molinosystem.sistema_molino.dtos;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor 
@NoArgsConstructor 
@Setter @Getter 
@Builder 
public class ExpenseDto {
    private Long id;
    
    private Timestamp date;

    private Long categoryId;

    private String categoryName;

    private String description;

    private BigDecimal expenseTotal;

    private Long accountId;
    
    private String accountName;
}
