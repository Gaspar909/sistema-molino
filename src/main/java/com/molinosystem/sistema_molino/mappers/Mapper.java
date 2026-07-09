package com.molinosystem.sistema_molino.mappers;

import com.molinosystem.sistema_molino.dtos.ProductCategoryDto;
import com.molinosystem.sistema_molino.dtos.ProductDto;
import com.molinosystem.sistema_molino.dtos.ProductPriceDto;
import com.molinosystem.sistema_molino.dtos.UnitDto;
import com.molinosystem.sistema_molino.dtos.UserDto;
import com.molinosystem.sistema_molino.entities.Product;
import com.molinosystem.sistema_molino.entities.ProductCategory;
import com.molinosystem.sistema_molino.entities.ProductPrice;
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

    public static ProductPriceDto toDTO(ProductPrice p){
        if (p== null) return null;

        return ProductPriceDto.builder()
        .id(p.getId())
        .productId(p.getProduct().getId())
        .name(p.getName())
        .price(p.getPrice())
        .active(p.getActive())
        .build();
    }

    public static ProductDto toDTO(Product p){
        if (p == null) return null;

        return ProductDto.builder()
        .id(p.getId())
        .barcode(p.getBarCode())
        .name(p.getName())
        .description(p.getDescription())
        .stock(p.getStock())
        .active(p.getActive())

        .unitId(p.getUnit() != null ? p.getUnit().getId() : null)
        .unitSymbol(p.getUnit() != null ? p.getUnit().getSymbol() : null)
        .ints(p.getUnit() != null ? p.getUnit().getInts() : null)
        
        .categoryId(p.getCategory() != null ? p.getCategory().getId() : null)
        .categoryName(p.getCategory() != null ? p.getCategory().getName() : null)
        .build();
    }

    public static User toEntitty(UserDto u){
        if (u == null) return null;

        return User.builder()
        .id(u.getId())
        .name(u.getName())
        .userName(u.getUserName())
        .password("")
        .rol(u.getRol())
        .active(u.isActive())
        .build();
    }

    public static Unit toEntity(UnitDto u){
        if (u == null) return null;

        return Unit.builder()
        .id(u.getId())
        .name(u.getName())
        .description(u.getDescription())
        .symbol(u.getSymbol())
        .ints(u.getInts())
        .active(u.getActive())
        .build();
    }

    public static ProductCategory toEntity(ProductCategoryDto p){
        if (p == null) return null;

        return ProductCategory.builder()
        .id(p.getId())
        .name(p.getName())
        .description(p.getDescription())
        .active(p.getActive())
        .build();
    }

    public static ProductPrice toEntity(ProductPriceDto p, Product product){
        if (p == null) return null;

        return ProductPrice.builder()
        .id(p.getId())
        .product(product)
        .name(p.getName())
        .price(p.getPrice())
        .active(p.getActive())
        .build();
    }

    public static Product toEntity(ProductDto p, ProductCategory category, Unit unit){
        if (p == null) return null;

        return Product.builder()
        .id(p.getId())
        .barCode(p.getBarcode())
        .category(category)
        .name(p.getName())
        .description(p.getDescription())
        .stock(p.getStock())
        .unit(unit)
        .active(p.getActive())
        .build();
    }
}
