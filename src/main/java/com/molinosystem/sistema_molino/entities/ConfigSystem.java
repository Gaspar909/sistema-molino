package com.molinosystem.sistema_molino.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "config_system")

@Setter @Getter
@NoArgsConstructor
public class ConfigSystem {
    @Id
    private Long id = 1L;

    @OneToOne
    @JoinColumn(name = "sell_account_default_id")
    private Account accountDefault;
}
