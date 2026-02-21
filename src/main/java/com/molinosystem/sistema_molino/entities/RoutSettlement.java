package com.molinosystem.sistema_molino.entities;

import java.math.BigDecimal;
import java.security.Timestamp;

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
@Table(name = "rout_settlement")

@Setter @Getter
@NoArgsConstructor
public class RoutSettlement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "delivery_id", nullable = false)
    private User delivery;

    @Column(name = "expected_cash")
    private BigDecimal expectedCash;

    @Column(name = "actual_cash")
    private BigDecimal actualCash;

    private BigDecimal difference;
    
    private Timestamp date;
}
