package com.molinosystem.sistema_molino.requests;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class AccountMovementSearchRequest {
    private Long accountId;
    private String search;
    private Timestamp startDate;
    private Timestamp endDate;
    private Long userId;
}
