package com.molinosystem.sistema_molino.services;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.molinosystem.sistema_molino.dtos.SuplyUsageDto;
import com.molinosystem.sistema_molino.entities.Suply;
import com.molinosystem.sistema_molino.entities.SuplyUsage;
import com.molinosystem.sistema_molino.exceptions.BadRequestException;
import com.molinosystem.sistema_molino.exceptions.NoFoundException;
import com.molinosystem.sistema_molino.mappers.Mapper;
import com.molinosystem.sistema_molino.repositories.SuplyUsageRepository;
import com.molinosystem.sistema_molino.requests.SuplyUsageRequest;
import com.molinosystem.sistema_molino.requests.SuplyUsageSearchRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SuplyUsageService implements ISuplyUsageService{
    private final SuplyUsageRepository suplyUsageRepository;
    private final SuplyService suplyService;

    @Override
    @Transactional
    public SuplyUsageDto createSuplyUsage(SuplyUsageRequest suplyUsageRequest) {
        if (suplyUsageRequest == null) throw new BadRequestException("Null value");

        if(suplyUsageRequest.getAmount() == null || suplyUsageRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0)
            throw new BadRequestException("The usage amount must be greater than zero");

        Suply suply = suplyService.getSuplyEntityById(suplyUsageRequest.getSuplyId());

        if(suply.getStock().compareTo(suplyUsageRequest.getAmount()) < 0) throw new BadRequestException("Insufficient stock for " + suply.getName());

        SuplyUsage newSuplyUsage = SuplyUsage.builder()
        .id(null)
        .suply(suply)
        .amount(suplyUsageRequest.getAmount())
        .build();

        suply.setStock(suply.getStock().subtract(suplyUsageRequest.getAmount()));
        
        return Mapper.toDTO(suplyUsageRepository.save(newSuplyUsage));
    }

    @Override
    public Page<SuplyUsageDto> getAllSuplyUsage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<SuplyUsage> result = suplyUsageRepository.findAll(pageable);
        
        return result.map(Mapper::toDTO);
    }

    @Override
    public Page<SuplyUsageDto> searchSuplyUsage(SuplyUsageSearchRequest filter, int page, int pageSize) {
        Specification<SuplyUsage> specification = Specification.where( (root, query, cb) -> cb.conjunction());

        if (filter.getSuplyId() != null){
            specification.and((root, query, cb) -> cb.equal(root.get("suply").get("id"), filter.getSuplyId())
            );
        }

        if(filter.getStartDate() != null){
            specification.and((root, query, cb) -> 
            cb.lessThanOrEqualTo(root.get("dateTime"), filter.getStartDate())
            );
        }

        if(filter.getEndDate() != null){
            specification.and((root, query, cb) -> 
            cb.lessThanOrEqualTo(root.get("dateTime"), filter.getEndDate())
            );
        }

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<SuplyUsage> result = suplyUsageRepository.findAll(specification, pageable);

        return result.map(Mapper :: toDTO);
    }

    @Override
    public SuplyUsageDto getSuplyUsageById(Long id) {
        SuplyUsage suplyUsage = suplyUsageRepository.findById(id).orElseThrow(() -> new NoFoundException("Suply Usage does not exist"));

        return Mapper.toDTO(suplyUsage);
    }

    @Override
    @Transactional
    public SuplyUsageDto updateSuplyUsage(Long id, SuplyUsageRequest upSuplyUsage) {
        SuplyUsage suplyUsage = suplyUsageRepository.findById(id).orElseThrow(() -> new NoFoundException("Suply Usage does not exist"));
        
        Suply suply = suplyUsage.getSuply();

        if(upSuplyUsage.getAmount() != null && upSuplyUsage.getAmount().compareTo(BigDecimal.ZERO) >= 0){
            BigDecimal newStock = suply.getStock().subtract(upSuplyUsage.getAmount().subtract(suplyUsage.getAmount()));
            
            if(newStock.compareTo(BigDecimal.ZERO) < 0) throw new BadRequestException("Insufficient stock for " + suply.getName());
            
            suplyUsage.setAmount(upSuplyUsage.getAmount());

            suply.setStock(newStock);
        }

        return Mapper.toDTO(suplyUsageRepository.save(suplyUsage));
    }

    @Override
    @Transactional
    public SuplyUsageDto deleteSuplyUsage(Long id) {
        SuplyUsage suplyUsage = suplyUsageRepository.findById(id).orElseThrow(() -> new NoFoundException("Suply Usage does not exist"));

        suplyUsageRepository.delete(suplyUsage);

        return Mapper.toDTO(suplyUsage);
    }

}
