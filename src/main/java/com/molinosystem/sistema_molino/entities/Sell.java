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
@Table(name = "sells")
@Getter @Setter
@NoArgsConstructor
public class Sell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long folio;

    @Column(name = "date_time")
    private Timestamp dateTime;

    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
