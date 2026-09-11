package com.molinosystem.sistema_molino.requests;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor 
@NoArgsConstructor 
@Setter @Getter
public class PurchaseRequest {
    private String description;

    private Long accountId;

    private List<PurchaseDetailRequest> details;
}
