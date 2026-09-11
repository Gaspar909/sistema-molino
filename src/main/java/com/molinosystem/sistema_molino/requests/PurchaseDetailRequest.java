package com.molinosystem.sistema_molino.requests;

import java.math.BigDecimal;

import com.molinosystem.sistema_molino.enums.ItemType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor 
@NoArgsConstructor 
@Setter @Getter 
public class PurchaseDetailRequest {
    private ItemType type;
    private Long referenceId;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
}
