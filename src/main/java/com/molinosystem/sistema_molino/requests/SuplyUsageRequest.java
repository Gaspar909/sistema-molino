package com.molinosystem.sistema_molino.requests;

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
public class SuplyUsageRequest {
    private Long suplyId;
    private BigDecimal amount;
    private Timestamp dateTime;
}
