package com.molinosystem.sistema_molino.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.molinosystem.sistema_molino.dtos.AccountDto;
import com.molinosystem.sistema_molino.dtos.AccountMovementDto;
import com.molinosystem.sistema_molino.dtos.ProductCategoryDto;
import com.molinosystem.sistema_molino.dtos.ProductDto;
import com.molinosystem.sistema_molino.dtos.ProductPriceDto;
import com.molinosystem.sistema_molino.dtos.PurchaseCompleteDto;
import com.molinosystem.sistema_molino.dtos.PurchaseDetailsDto;
import com.molinosystem.sistema_molino.dtos.PurchaseDto;
import com.molinosystem.sistema_molino.dtos.SaleCompleteDto;
import com.molinosystem.sistema_molino.dtos.SaleDetailDto;
import com.molinosystem.sistema_molino.dtos.SaleDto;
import com.molinosystem.sistema_molino.dtos.SuplyDto;
import com.molinosystem.sistema_molino.dtos.SuplyUsageDto;
import com.molinosystem.sistema_molino.dtos.UnitDto;
import com.molinosystem.sistema_molino.dtos.UserDto;
import com.molinosystem.sistema_molino.entities.Account;
import com.molinosystem.sistema_molino.entities.AccountMovement;
import com.molinosystem.sistema_molino.entities.Product;
import com.molinosystem.sistema_molino.entities.ProductCategory;
import com.molinosystem.sistema_molino.entities.ProductPrice;
import com.molinosystem.sistema_molino.entities.Purchase;
import com.molinosystem.sistema_molino.entities.PurchaseDetails;
import com.molinosystem.sistema_molino.entities.Sale;
import com.molinosystem.sistema_molino.entities.SaleDetail;
import com.molinosystem.sistema_molino.entities.Suply;
import com.molinosystem.sistema_molino.entities.SuplyUsage;
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

    public static SaleDetailDto toDTO(SaleDetail s){
        if (s == null) return null;
        
        return SaleDetailDto.builder()
        .id(s.getId())
        .saleId(s.getSale().getId())
        .productId(s.getProduct().getId())
        .productName(s.getProduct().getName())
        .productBarcode(s.getProduct().getBarCode())
        .quantity(s.getQuantity())
        .price(s.getPrice())
        .totalPrice(s.getTotalPrice())
        .build();
    }

    public static SaleDto toDTO(Sale s){
        if (s == null) return null;

        return SaleDto.builder()
        .id(s.getId())
        .folio(s.getFolio())
        .dateTime(s.getDateTime())
        .total(s.getTotal())
        .clientId(s.getClient() != null ? s.getClient().getId() : null)
        .clientName(s.getClient() != null ? s.getClient().getName() : null)
        .userId(s.getUser() != null ? s.getUser().getId() : null)
        .userName(s.getUser() != null ? s.getUser().getUserName() : null )
        .accountId(s.getAccount() != null ? s.getAccount().getId() : null)
        .accountName(s.getAccount() != null  ? s.getAccount().getName() : null)
        .build();
    }

    public static SaleCompleteDto toCompleteDto(Sale s){
        if (s == null) return null;

        List<SaleDetailDto> details = null;
        if (s.getDetails() != null) {
            details = s.getDetails().stream().map(Mapper::toDTO)
            .collect(Collectors.toList());
        }
        
        return SaleCompleteDto.builder()
        .id(s.getId())
        .folio(s.getFolio())
        .dateTime(s.getDateTime())
        .total(s.getTotal())
        .clientId(s.getClient() != null ? s.getClient().getId(): null)
        .clientName(s.getClient() != null ? s.getClient().getName() : null)
        .userId(s.getUser() != null ? s.getUser().getId() : null)
        .userName(s.getUser() != null ? s.getUser().getName() : null)
        .accountId(s.getAccount() != null ? s.getAccount().getId() : null)
        .accountName(s.getAccount() != null  ? s.getAccount().getName() : null)
        .saleDetail(details)
        .build();
    }

    public static SuplyDto toDTO(Suply s){
        if (s == null) return null;

        return SuplyDto.builder()
        .id(s.getId())
        .name(s.getName())
        .description(s.getDescription())
        .stock(s.getStock())
        .price(s.getPrice())
        .active(s.getActive())
        .build();
    }

    public static SuplyUsageDto toDTO(SuplyUsage s){
        if(s == null) return null;

        return SuplyUsageDto.builder()
        .id(s.getId())
        .suplyId(s.getSuply().getId())
        .amount(s.getAmount())
        .dateTime(s.getDateTime())
        .build();
    }

    public static AccountDto toDTO(Account a){
        if(a == null) return null;

        return AccountDto.builder()
        .id(a.getId())
        .name(a.getName())
        .balance(a.getBalance())
        .description(a.getDescription())
        .active(a.getActive())
        .build();
    }

    public static AccountMovementDto toDTO(AccountMovement a){
        if (a == null) return null;

        return AccountMovementDto.builder()
        .id(a.getId())
        .accoutnId(a.getAccount().getId())
        .accountName(a.getAccount().getName())
        .amount(a.getAmount())
        .movementType(a.getMovementType())
        .description(a.getDescription())
        .createdAt(a.getCreatedAt())
        .userName(a.getUser().getName())
        .build();
    }

    public static PurchaseDto toDTO(Purchase p){
        if (p == null) return null;

        return PurchaseDto.builder()
            .id(p.getId())
            .dateTime(p.getDateTime())
            .description(p.getDescription())
            .total(p.getTotal())
            .accountId(p.getAccount().getId())
            .accountName(p.getAccount().getName())
            .userName(p.getUser().getName())
            .build();
    }

    public static PurchaseDetailsDto toDTO(PurchaseDetails p){
        if (p== null) return null;

        return PurchaseDetailsDto.builder()
        .id(p.getId())
        .type(p.getType())
        .purchaseId(p.getPurchase().getId())
        .referenceId(p.getReference().getId())
        .referenceName(p.getReference().getName())
        .quantity(p.getQuantity())
        .unitPrice(p.getUnitPrice())
        .subtotal(p.getSubTotal())
        .build();
    }
    
    public static PurchaseCompleteDto toCompleteDto(Purchase p){
        if (p == null) return null;

        List<PurchaseDetailsDto> details = null;
        if (p.getDetails() != null) {
            details = p.getDetails().stream().map(Mapper::toDTO)
            .collect(Collectors.toList());
        }

        return PurchaseCompleteDto.builder()
            .id(p.getId())
            .dateTime(p.getDateTime())
            .description(p.getDescription())
            .total(p.getTotal())
            .accountId(p.getAccount().getId())
            .accountName(p.getAccount().getName())
            .userName(p.getUser().getName())
            .details(details)
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
