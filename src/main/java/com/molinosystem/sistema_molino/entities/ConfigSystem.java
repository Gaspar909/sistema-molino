package com.molinosystem.sistema_molino.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "config_system")

@Setter @Getter
@NoArgsConstructor
public class ConfigSystem {
    private Long id = 1L;

    @JoinColumn(name = "sell_account_default_id")
    private Account accountDefault;
}
