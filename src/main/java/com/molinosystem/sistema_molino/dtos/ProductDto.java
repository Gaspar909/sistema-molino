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
public class ProductDto {
    private Long id;
    private String barcode;
    private String name;
    private String description;
    private Double stock;
    private Boolean active;
    
    private Long unitId;
    private String unitSymbol;
    private Boolean ints;

    private Long categoryId;
    private String categoryName;
}
