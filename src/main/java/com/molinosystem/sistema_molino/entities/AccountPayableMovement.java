package com.molinosystem.sistema_molino.entities;

import java.math.BigDecimal;
import java.security.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account_payable_movements")

@Setter @Getter
@NoArgsConstructor
public class AccountPayableMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_payable", nullable = false)
    private AccountPayable accountPayable;

    private BigDecimal amount;

    @Column(name = "movement_type")
    private String movementType;

    private String description;

    @Column(name = "source_id")
    private Long sourceId;

    @Column(name = "source_type")
    private String sourceType;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
