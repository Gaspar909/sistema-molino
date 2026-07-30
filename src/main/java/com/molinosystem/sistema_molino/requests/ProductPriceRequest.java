package com.molinosystem.sistema_molino.requests;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class ProductPriceRequest {
    private Long productId;
    private String name;
    private BigDecimal price;
    private Boolean active;
}
