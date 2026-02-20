package com.molinosystem.sistema_molino.entities;

import java.security.Timestamp;

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
@Table(name = "inventory_adjustments")

@Setter @Getter
@NoArgsConstructor
public class InventoryAdjustment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference_id")
    private Long referenceId;

    private int quantity;

    @Column(name = "adjustment_type")
    private String adjustmentType;

    private String observation;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "user_id")
    private User user;
}
