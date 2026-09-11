package com.molinosystem.sistema_molino.dtos;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor 
@NoArgsConstructor 
@Setter @Getter 
@Builder 
public class PurchaseCompleteDto {
    private Long id;

    private Timestamp dateTime;

    private String description;

    private BigDecimal total;

    private Long accountId;

    private String accountName;
    
    private String userName;

    private List<PurchaseDetailsDto> details;
}
