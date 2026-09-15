package com.molinosystem.sistema_molino.services;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.molinosystem.sistema_molino.dtos.PurchaseCompleteDto;
import com.molinosystem.sistema_molino.dtos.PurchaseDto;
import com.molinosystem.sistema_molino.entities.Account;
import com.molinosystem.sistema_molino.entities.Item;
import com.molinosystem.sistema_molino.entities.Purchase;
import com.molinosystem.sistema_molino.entities.PurchaseDetails;
import com.molinosystem.sistema_molino.entities.User;
import com.molinosystem.sistema_molino.enums.MovementType;
import com.molinosystem.sistema_molino.exceptions.BadRequestException;
import com.molinosystem.sistema_molino.exceptions.NoFoundException;
import com.molinosystem.sistema_molino.mappers.Mapper;
import com.molinosystem.sistema_molino.repositories.PurchaseDetailRepository;
import com.molinosystem.sistema_molino.repositories.PurchaseRespository;
import com.molinosystem.sistema_molino.repositories.UserRepository;
import com.molinosystem.sistema_molino.requests.AccountMovementRequest;
import com.molinosystem.sistema_molino.requests.PurchaseDetailRequest;
import com.molinosystem.sistema_molino.requests.PurchaseRequest;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class PurchaseService implements IPurchaseService{
    PurchaseRespository purchaseRepository;
    PurchaseDetailRepository purchaseDetailRepository;
    UserRepository userRepository;

    ProductService productService;
    SuplyService suplyService;
    AccountService accountService;
    AccountMovementService accountMovementService;

    @Override
    @Transactional 
    public PurchaseCompleteDto createPurchase(PurchaseRequest purchaseRequest) {
        BigDecimal total = BigDecimal.ZERO;
        Account account = accountService.getAccountEntityById(purchaseRequest.getAccountId());

        Purchase newPurchase = Purchase.builder()
            .id(null)
            .description(purchaseRequest.getDescription())
            .total(total)
            .account(account)
            .user(getCurrentUser())
            .build();

        for(PurchaseDetailRequest pd : purchaseRequest.getDetails()){
            if (pd != null) {
                Item itemTmp;

                switch (pd.getType()) {
                    case PRODUCT:
                        itemTmp = productService.getProductEntityById(pd.getReferenceId());
                        break;

                    case SUPLY:
                        itemTmp = suplyService.getSuplyEntityById(pd.getReferenceId());
                        break;
                        
                    default:
                        throw new BadRequestException("Item type undefined");
                }

                itemTmp.setStock(itemTmp.getStock().add(pd.getQuantity()));

                PurchaseDetails newDetail = PurchaseDetails.builder()
                .id(null)
                .purchase(null)
                .quantity(pd.getQuantity())
                .unitPrice(pd.getUnitPrice())
                .subTotal(pd.getQuantity().multiply(pd.getUnitPrice()))
                .type(pd.getType())
                .reference(itemTmp)
                .build();

                newPurchase.addDetail(newDetail);

                total = total.add(newDetail.getSubTotal());
            }
        }

        newPurchase.setTotal(total);
        newPurchase = purchaseRepository.save(newPurchase);

            AccountMovementRequest movementRequest = AccountMovementRequest.builder()
            .accountId(purchaseRequest.getAccountId())
            .amount(total)
            .movementType(MovementType.EXPENSE)
            .description("Purchase: " + newPurchase.getDescription())
            .createdAt(null)
            .userId(null)
            .build();

            accountMovementService.createAccountMovement(movementRequest);

            return Mapper.toCompleteDto(newPurchase);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseDto> getAllPurchase(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Purchase> result = purchaseRepository.findAll(pageable);
    
        return result.map(Mapper::toDTO);
    }

    @Override
    public PurchaseCompleteDto getPurchaseById(Long id) {
        Purchase purchase = purchaseRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Purchase does not exist"));

        return Mapper.toCompleteDto(purchase);
    }

    @Override
    @Transactional 
    public PurchaseCompleteDto updatePurchase(Long id, PurchaseRequest purchaseUp) {
        Purchase purchase = purchaseRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Purchase does not exist"));

        BigDecimal oldTotal = purchase.getTotal();
        BigDecimal total = oldTotal;

        if(purchaseUp.getDescription() != null && !purchaseUp.getDescription().trim().isEmpty()){
            purchase.setDescription(purchaseUp.getDescription());
        }

        if (purchase.getDetails() != null) {
            for(PurchaseDetails pd : purchase.getDetails()){
                Item item = pd.getReference();

                BigDecimal restoreStock = item.getStock().subtract(pd.getQuantity());

                item.setStock(restoreStock);
            }

            purchase.getDetails().clear();
            total = BigDecimal.ZERO;

            for( PurchaseDetailRequest pd : purchaseUp.getDetails()){
                if (pd != null) {
                    BigDecimal totalPrice = pd.getQuantity().multiply(pd.getUnitPrice());
                    Item itemTmp;

                    switch (pd.getType()) {
                        case PRODUCT:
                            itemTmp = productService.getProductEntityById(pd.getReferenceId());
                            break;

                        case SUPLY:
                            itemTmp = suplyService.getSuplyEntityById(pd.getReferenceId());
                            break;
                            
                        default:
                            throw new BadRequestException("Item type undefined");
                    }

                    PurchaseDetails newDetail = PurchaseDetails.builder()
                    .id(null)
                    .type(pd.getType())
                    .purchase(purchase)
                    .reference(itemTmp)
                    .quantity(pd.getQuantity())
                    .unitPrice(pd.getUnitPrice())
                    .subTotal(totalPrice)
                    .build();

                    purchase.addDetail(newDetail);

                    itemTmp.setStock(itemTmp.getStock().add(pd.getQuantity()));

                    total = total.add(newDetail.getSubTotal());
                }
            }
        }

        BigDecimal diff = total.subtract(oldTotal);

        if (diff.compareTo(BigDecimal.ZERO) != 0) {
            AccountMovementRequest movementRequest = AccountMovementRequest.builder()
            .accountId(purchase.getAccount().getId())
            .amount(diff.abs())
            .movementType(diff.compareTo(BigDecimal.ZERO) > 0 ? MovementType.EXPENSE : MovementType.INCOME)
            .description("Update Sale: " + purchase.getDescription())
            .createdAt(null)
            .userId(null)
            .build();

            accountMovementService.createAccountMovement(movementRequest);
        }

        purchase.setTotal(total);
        
        return Mapper.toCompleteDto(purchase);
    }

    @Override
    @Transactional 
    public PurchaseCompleteDto deletePurchase(Long id) {
        Purchase purchase = purchaseRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Purchase does not exist"));
        
        if (purchase.getDetails() != null){
            for (PurchaseDetails pd : purchase.getDetails()){
                Item item = pd.getReference();
                item.setStock(item.getStock().add(pd.getQuantity()));
            }
        }

        AccountMovementRequest movementRequest = AccountMovementRequest.builder()
        .accountId(purchase.getAccount().getId())
        .amount(purchase.getTotal())
        .movementType(MovementType.INCOME)
        .description("Delete purchase: " + purchase.getDescription())
        .createdAt(null)
        .userId(null)
        .build();

        accountMovementService.createAccountMovement(movementRequest);

        purchaseRepository.delete(purchase);

        
        return Mapper.toCompleteDto(purchase);
    }

    private User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Please Log in");
        }

        String username = authentication.getName();
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User no found: " + username));
    }
}
