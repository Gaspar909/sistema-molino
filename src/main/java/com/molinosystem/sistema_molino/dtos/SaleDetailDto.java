package com.molinosystem.sistema_molino.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class SaleDetailDto {
    private Long id;

    private Long saleId;

    private Long productId;

    private String productName;

    private String productBarcode;

    private BigDecimal quantity;

    private BigDecimal price;

    private BigDecimal totalPrice;
}
