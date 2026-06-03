package com.molinosystem.sistema_molino.mappers;
import com.molinosystem.sistema_molino.dtos.UserDto;
import com.molinosystem.sistema_molino.entities.User;


public class Mapper {
    //Mapper User to UserDto
    public static UserDto toDTO (User u){
        if (u == null) return null;

        return UserDto.builder()
        .id(u.getId())
        .name(u.getName())
        .userName(u.getUserName())
        .rol(u.getRol())
        .active(u.isActive())
        .build();
    }
}
