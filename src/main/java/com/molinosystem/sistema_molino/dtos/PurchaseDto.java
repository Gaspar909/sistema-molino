package com.molinosystem.sistema_molino.dtos;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor 
@AllArgsConstructor
@Setter  @Getter 
@Builder 
public class PurchaseDto {
    private Long id;

    private Timestamp dateTime;

    private String description;

    private BigDecimal total;

    private Long accountId;

    private String accountName;

    private String userName;
}
