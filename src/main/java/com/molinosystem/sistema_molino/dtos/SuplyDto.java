package com.molinosystem.sistema_molino.dtos;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Builder

public class SuplyDto {
    private Long id;

    private String name;

    private String description;

    private BigDecimal stock;
    
    private BigDecimal price;

    private Boolean active;
}
