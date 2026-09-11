package com.molinosystem.sistema_molino.dtos;

import java.math.BigDecimal;

import com.molinosystem.sistema_molino.enums.ItemType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor 
@AllArgsConstructor 
@Setter  @Getter 
@Builder 
public class PurchaseDetailsDto {
    private Long id;

    private ItemType type;

    private Long purchaseId;

    private Long referenceId;

    private String referenceName;

    private BigDecimal quantity;

    private BigDecimal unitPrice;
    
    private BigDecimal subtotal;
}
