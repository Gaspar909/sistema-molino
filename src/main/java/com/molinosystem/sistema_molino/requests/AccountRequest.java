package com.molinosystem.sistema_molino.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor 
@Getter
public class AccountRequest {
    private String name;
    private String description;
}
