package com.molinosystem.sistema_molino.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rout_settlement_details")

@Setter @Getter
@NoArgsConstructor
public class RoutSettlementDetails {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rout_settlement_id", nullable = false)
    private RoutSettlement routSettlement;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity_out", nullable = false)
    private BigDecimal quantityOut;

    @Column(name = "quantity_return", nullable = true)
    private BigDecimal quantityReturn;

    @Column(name = "price_unit")
    private BigDecimal priceUnit;

    @Column(name = "price_total")
    private BigDecimal priceTotal;
}
