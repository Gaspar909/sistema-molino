package com.molinosystem.sistema_molino.entities;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity 
@Table(name = "items")

@AllArgsConstructor
@NoArgsConstructor 
@Setter @Getter 
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public class Item {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    private String description;

    private BigDecimal stock;

    private Boolean active;
}
