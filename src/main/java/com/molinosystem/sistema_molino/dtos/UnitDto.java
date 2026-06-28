package com.molinosystem.sistema_molino.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class UnitDto {
    private Long id;

    private String name;

    private String description;

    private String symbol;

    private Boolean ints;
    
    private Boolean active;
}
