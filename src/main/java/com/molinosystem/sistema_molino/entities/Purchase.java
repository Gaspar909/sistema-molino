package com.molinosystem.sistema_molino.entities;

import java.math.BigDecimal;
import java.security.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "purchases")

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_time")
    private Timestamp dateTime;

    private String description;
    
    private BigDecimal total;
}
