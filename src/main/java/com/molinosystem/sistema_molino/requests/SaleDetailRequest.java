package com.molinosystem.sistema_molino.requests;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter  @Getter
@Builder
public class SaleDetailRequest {
    private Long idSale;
    private Long productId;
    private Long priceId;
    private BigDecimal quantity;
}
