package com.molinosystem.sistema_molino.services;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.molinosystem.sistema_molino.dtos.SaleCompleteDto;
import com.molinosystem.sistema_molino.dtos.SaleDto;
import com.molinosystem.sistema_molino.entities.Product;
import com.molinosystem.sistema_molino.entities.Sale;
import com.molinosystem.sistema_molino.entities.SaleDetail;
import com.molinosystem.sistema_molino.entities.User;
import com.molinosystem.sistema_molino.exceptions.BadRequestException;
import com.molinosystem.sistema_molino.exceptions.NoFoundException;
import com.molinosystem.sistema_molino.mappers.Mapper;
import com.molinosystem.sistema_molino.repositories.SaleRepository;
import com.molinosystem.sistema_molino.repositories.UserRepository;
import com.molinosystem.sistema_molino.requests.SaleDetailRequest;
import com.molinosystem.sistema_molino.requests.SaleRequest;
import com.molinosystem.sistema_molino.requests.SaleSearchRequest;

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

                Product product = productService.getProductEntityById(sd.getProductId());
                if (sd.getQuantity().compareTo(BigDecimal.valueOf(product.getStock())) > 0) 
                    throw new BadRequestException(
                        "Insufficient stock for " + product.getName() + ": available " 
                        + product.getStock() + ", requested " + sd.getQuantity()
                    );

                SaleDetail newDetail = SaleDetail.builder()
                .id(null)
                .sale(null)
                .product(product)
                .quantity(sd.getQuantity())
                .price(price)
                .totalPrice(price.multiply(sd.getQuantity()))
                .build();

                newSale.addDetail(newDetail);
                
                double newStock = BigDecimal.valueOf(product.getStock())
                    .subtract(sd.getQuantity())
                    .doubleValue();

                product.setStock(newStock);
                //productService.updateProduct(product); <- NO HACE FALTA: @Transactional lo guarda automaticamente

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
    public Page<SaleDto> searchSales(int page, int pageSize, SaleSearchRequest search) {
        Specification<Sale> specification = Specification.where((root, query, cb) -> cb.conjunction());

        if(search.getFolio() != null && !search.getFolio().isEmpty()){
            specification = specification.and(
                (root, query, cb) -> 
                cb.like(cb.lower(root.get("folio")), "%" + search.getFolio() + "%")
            );
        }

        if (search.getStartDate() != null) {
            specification = specification.and(
                (root, query, cb) -> 
                cb.greaterThanOrEqualTo(root.get("dateTime"), search.getStartDate())
            );
        }

        if (search.getEndDate() != null){
            specification = specification.and(
                (root, query, cb) -> 
                cb.lessThanOrEqualTo(root.get("dateTime"), search.getEndDate())
            );
        }

        if(search.getClientId() != null){
            specification = specification.and(
                (root, query, cb) -> 
                cb.equal(root.get("client").get("id"), search.getClientId())
            );
        }

        if (search.getUserId() != null) {
            specification = specification.and((
                root, query, cb) ->
                cb.equal(root.get("user").get("id"), search.getUserId())
            );
        }

        Pageable pageable = PageRequest.of(page, pageSize);
        
        Page<Sale> result = saleRepository.findAll(specification, pageable);

        return result.map(Mapper::toDTO);
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

        if(sale.getDetails() != null){
            for(SaleDetail saleDetail : sale.getDetails()){
                if (saleDetail != null) {
                    Product product = saleDetail.getProduct();
                    double restoreStock = BigDecimal.valueOf(product.getStock())
                    .add(saleDetail.getQuantity())
                    .doubleValue();

                    product.setStock(restoreStock);
                }
            }
        }
        
        saleRepository.delete(sale);

        return Mapper.toDTO(sale);
    }

    
    private static final String ALPHANUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();

    private String generateFolio(){
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String homoclave = generateHomoclave(2);
        Long lastId = saleRepository.findTopByOrderByIdDesc()
        .map(sale -> sale.getId())
        .orElse(0L);

        Long numberPart = lastId + 1;

        return String.format("VTA-%s-%04X-%s", datePart, numberPart, homoclave);
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

    private String generateHomoclave(int length){
        StringBuilder sb = new StringBuilder(length);
        for (int i=0; i<length ; i++){
            int index = RANDOM.nextInt(ALPHANUMERIC.length());
            sb.append(ALPHANUMERIC.charAt(index));
        }
        return sb.toString();
    }
}
