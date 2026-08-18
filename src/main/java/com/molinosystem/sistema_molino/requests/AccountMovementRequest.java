package com.molinosystem.sistema_molino.requests;

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
public class AccountMovementRequest {
    private Long accountId;
    private BigDecimal amount;
    private String movementType;
    private String description;
    private Timestamp createdAt;
    private Long userId;
}
