package com.molinosystem.sistema_molino.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "purchase_datails")

@Setter @Getter
@NoArgsConstructor
public class purchaseDetails {
    private Long Id;
    private String type;

    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @Column(name = "reference_id")
    private Long referenceId;

    private BigDecimal quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "sub_total")
    private BigDecimal subTotal;
}
