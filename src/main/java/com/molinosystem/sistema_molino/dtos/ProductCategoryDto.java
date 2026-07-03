package com.molinosystem.sistema_molino.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Builder
public class ProductCategoryDto {
    private Long id;
    private String name;
    private String description;
    private Boolean active;
}
