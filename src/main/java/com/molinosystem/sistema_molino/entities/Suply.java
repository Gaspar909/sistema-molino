package com.molinosystem.sistema_molino.entities;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "suplies")

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder 
public class Suply extends Item{
    private BigDecimal stock;
    private BigDecimal price;
}
