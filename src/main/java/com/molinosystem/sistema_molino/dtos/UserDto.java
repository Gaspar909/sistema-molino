package com.molinosystem.sistema_molino.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class UserDto {
    private Long id;

    private String name;

    private String userName;

    private String rol;
    
    private boolean active;
}
