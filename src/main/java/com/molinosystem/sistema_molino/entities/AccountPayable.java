package com.molinosystem.sistema_molino.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts_payable")

@Setter @Getter
@NoArgsConstructor
public class AccountPayable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "entity_type")
    private String entityType;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    
    private Boolean active;
}
