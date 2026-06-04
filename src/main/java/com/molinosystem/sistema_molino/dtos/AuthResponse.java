package com.molinosystem.sistema_molino.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class AuthResponse {
    private String token;
    private Long id;
    private String userName;
    private String name;
    private String rol;
}
