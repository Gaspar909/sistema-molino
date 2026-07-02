package com.molinosystem.sistema_molino.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class UnitRequest {
    private String name;
    private String description;
    private String symbol;
    private Boolean ints;
}
