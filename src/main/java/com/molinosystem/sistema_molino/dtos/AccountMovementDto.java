package com.molinosystem.sistema_molino.dtos;

import java.math.BigDecimal;
import java.security.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor 
@NoArgsConstructor
@Setter @Getter
@Builder
public class AccountMovementDto {
    private Long id;
    private Long accoutn_id;
    private BigDecimal amount;
    private String movementType;
    private String description;
    private Timestamp createdAt;
    private String userName;
}
