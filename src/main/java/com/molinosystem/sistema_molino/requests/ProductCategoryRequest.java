package com.molinosystem.sistema_molino.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class ProductCategoryRequest {
    private String name;
    private String description;
}
