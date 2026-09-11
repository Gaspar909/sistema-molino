package com.molinosystem.sistema_molino.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "product")
@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder 
public class Product extends  Item{
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category;

    @Column(name = "bar_code")
    private String barCode;

    private Double stock;

    @ManyToOne
    @JoinColumn(name = "unitId", nullable = false)
    private Unit unit;
}
