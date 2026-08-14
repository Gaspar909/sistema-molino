package com.molinosystem.sistema_molino.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
@Builder
public class AccountDto {
    private Long id;
    private String name;
    private BigDecimal balance;
    private String description;
    private Boolean active;
}
