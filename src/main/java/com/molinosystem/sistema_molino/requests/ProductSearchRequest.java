package com.molinosystem.sistema_molino.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class ProductSearchRequest {
    private int page;
    private int pageSize;
    private String search;
    private Boolean active;

    private Long unitId;
    private Long categoryId;
}
