package com.molinosystem.sistema_molino.dtos;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
@Builder
public class SaleCompleteDto {
    private Long id;
    private String folio;
    private Timestamp dateTime;
    private BigDecimal total;
    private Long clientId;
    private String clientName;
    private Long userId;
    private String userName;

    private List<SaleDetailDto> saleDetail;
}
