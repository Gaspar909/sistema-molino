package com.molinosystem.sistema_molino.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "suply_usage")

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuplyUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "suply_id", nullable = false)
    private Suply suply;
    
    private BigDecimal amount;

    @Column(name = "date_time")
    private Timestamp dateTime;

    @PrePersist
    protected void onCreate() {
    this.dateTime = Timestamp.valueOf(LocalDateTime.now());
    }
}
