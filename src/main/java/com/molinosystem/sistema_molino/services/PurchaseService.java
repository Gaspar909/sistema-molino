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
import com.molinosystem.sistema_molino.entities.Product;
import com.molinosystem.sistema_molino.entities.Purchase;
import com.molinosystem.sistema_molino.entities.PurchaseDetails;
import com.molinosystem.sistema_molino.entities.Suply;
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
                        Product product = productService.getProductEntityById(pd.getReferenceId());
                        product.setStock(
                            BigDecimal.valueOf(product.getStock()).add(pd.getQuantity()).doubleValue()
                        );
                        itemTmp = product;
                        break;

                    case SUPLY:
                        Suply suply = suplyService.getSuplyEntityById(pd.getReferenceId());
                        suply.setStock(suply.getStock().add(pd.getQuantity()));
                        itemTmp = suply;
                        break;
                        
                    default:
                        throw new BadRequestException("Item type undefined");
                }

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
    public PurchaseCompleteDto updatePurchase(Long id, PurchaseRequest purchaseUp) {
        Purchase purchase = purchaseRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Purchase does not exist"));

        if(purchaseUp.getDescription() != null && !purchaseUp.getDescription().trim().isEmpty()){

        }
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePurchase'");
    }

    @Override
    public PurchaseCompleteDto deletePurchase(Long id) {
        Purchase purchase = purchaseRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Purchase does not exist"));
        
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePurchase'");
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
