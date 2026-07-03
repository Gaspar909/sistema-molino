package com.molinosystem.sistema_molino.mappers;
import com.molinosystem.sistema_molino.dtos.ProductCategoryDto;
import com.molinosystem.sistema_molino.dtos.UnitDto;
import com.molinosystem.sistema_molino.dtos.UserDto;
import com.molinosystem.sistema_molino.entities.ProductCategory;
import com.molinosystem.sistema_molino.entities.Unit;
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

    public static UnitDto toDTO (Unit u){
        if (u == null) return null;

        return UnitDto.builder()
        .id(u.getId())
        .name(u.getName())
        .description(u.getDescription())
        .symbol(u.getSymbol())
        .ints(u.getInts())
        .active(u.getActive())
        .build();
    }

    public static ProductCategoryDto toDTO(ProductCategory p){
        if (p == null) return null;

        return ProductCategoryDto.builder()
        .id(p.getId())
        .name(p.getName())
        .description(p.getDescription())
        .active(p.getActive())
        .build();
    }
}
