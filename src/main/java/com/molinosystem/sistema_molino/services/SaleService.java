package com.molinosystem.sistema_molino.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.molinosystem.sistema_molino.dtos.SaleCompleteDto;
import com.molinosystem.sistema_molino.dtos.SaleDto;
import com.molinosystem.sistema_molino.entities.Sale;
import com.molinosystem.sistema_molino.entities.SaleDetail;
import com.molinosystem.sistema_molino.entities.User;
import com.molinosystem.sistema_molino.exceptions.NoFoundException;
import com.molinosystem.sistema_molino.mappers.Mapper;
import com.molinosystem.sistema_molino.repositories.SaleRepository;
import com.molinosystem.sistema_molino.repositories.UserRepository;
import com.molinosystem.sistema_molino.requests.SaleDetailRequest;
import com.molinosystem.sistema_molino.requests.SaleRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SaleService implements ISaleService {
    private final SaleRepository saleRepository;
    private final UserRepository userRepository;

    private final ProductService productService;
    private final ProductPriceService preoductPriceService;

    @Override
    @Transactional
    public SaleDto createSale(SaleRequest saleRequest) {
        BigDecimal total = BigDecimal.ZERO;

        Sale newSale = Sale.builder()
        .id(null)
        .folio(generateFolio())
        .total(total)
        .client(saleRequest.getClientId() != null ? null : null)
        .user(getCurrentUser())
        .build();
        
        for (SaleDetailRequest sd : saleRequest.getSaleDetail()){
            if (sd != null){
                BigDecimal price = preoductPriceService.getProductPriceById(sd.getPriceId()).getPrice();
                SaleDetail newDetail = SaleDetail.builder()
                .id(null)
                .sale(null)
                .product(productService.getProductEntityById(sd.getProductId()))
                .quantity(sd.getQuantity())
                .price(price)
                .totalPrice(price.multiply(sd.getQuantity()))
                .build();
                newSale.addDetail(newDetail);
                total = total.add(newDetail.getTotalPrice());
            }
        }

        newSale.setTotal(total);

        return Mapper.toDTO(saleRepository.save(newSale));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SaleDto> getAllSales(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Sale> result = saleRepository.findAll(pageable);

        return result.map(Mapper::toDTO);
    }
    @Override
    public Page<SaleDto> searchSales(int page, int pageSize) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchSales'");
    }
    @Override
    @Transactional (readOnly = true)
    public SaleCompleteDto getSaleComplete(Long id) {
        Sale sale = saleRepository.getReferenceById(id);
        return Mapper.toCompleteDto(sale);
    }

    @Override
    @Transactional
    public SaleCompleteDto updateSale(Long id, SaleRequest saleUp) {
        Sale sale = saleRepository.findById(id)
        .orElseThrow( () -> new NoFoundException("Sale does not exist"));

        sale.getDetails().clear();

        BigDecimal total = BigDecimal.ZERO;

        for (SaleDetailRequest sd : saleUp.getSaleDetail()){
            if (sd != null){
                BigDecimal price = preoductPriceService.getProductPriceById(sd.getPriceId()).getPrice();
                BigDecimal totalPrice = price.multiply(sd.getQuantity());
                
                SaleDetail newDetail = SaleDetail.builder()
                .id(null)
                .sale(null)
                .product(productService.getProductEntityById(sd.getProductId()))
                .quantity(sd.getQuantity())
                .price(price)
                .totalPrice(totalPrice)
                .build();
                
                sale.addDetail(newDetail);
                
                total = total.add(newDetail.getTotalPrice());
            }
        }

        sale.setTotal(total);

        return Mapper.toCompleteDto(saleRepository.save(sale));
    }

    @Override
    @Transactional
    public SaleDto deleteSale(Long id) {
        Sale sale = saleRepository.findById(id)
        .orElseThrow( () -> new NoFoundException("Sale does not exist"));
        
        saleRepository.delete(sale);

        return Mapper.toDTO(sale);
    }

    private String generateFolio(){
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Long lastId = saleRepository.findTopByOrderByIdDesc()
        .map(Sale::getId)
        .orElse(0L);

        Long numberPart = lastId + 1;

        return String.format("VTA-%s-%04X", datePart, numberPart);
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
