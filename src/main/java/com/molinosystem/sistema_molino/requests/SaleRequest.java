package com.molinosystem.sistema_molino.requests;

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
public class SaleRequest {
    private Long clientId;

    private List<SaleDetailRequest> saleDetail;

}
