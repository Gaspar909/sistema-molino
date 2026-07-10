package com.molinosystem.sistema_molino.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Builder
public class ProductRequest {
    private String barcode;

    private String name;
    
    private String descripotion;
    
    private Double stock;

    
    private Long unitId;
    
    private Long categoryId;

    private boolean active;
}
