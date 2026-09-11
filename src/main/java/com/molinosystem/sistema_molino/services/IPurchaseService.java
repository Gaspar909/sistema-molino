package com.molinosystem.sistema_molino.services;

import org.springframework.data.domain.Page;

import com.molinosystem.sistema_molino.dtos.PurchaseCompleteDto;
import com.molinosystem.sistema_molino.dtos.PurchaseDto;
import com.molinosystem.sistema_molino.requests.PurchaseRequest;

public interface IPurchaseService {
    PurchaseCompleteDto createPurchase(PurchaseRequest purchaseRequest);
    Page<PurchaseDto> getAllPurchase (int page, int pageSize);
    PurchaseCompleteDto getPurchaseById(Long id);
    PurchaseCompleteDto updatePurchase(Long id, PurchaseRequest pruchaseUp);
    PurchaseCompleteDto deletePurchase(Long id);
}
