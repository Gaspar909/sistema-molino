package com.molinosystem.sistema_molino.dtos;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.molinosystem.sistema_molino.enums.MovementType;

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
    private Long accoutnId;
    private String accountName;
    private BigDecimal amount;
    private MovementType movementType;
    private String description;
    private Timestamp createdAt;
    private String userName;
}
