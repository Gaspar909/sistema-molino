package com.molinosystem.sistema_molino.services;

import org.springframework.data.domain.Page;

import com.molinosystem.sistema_molino.dtos.SuplyUsageDto;
import com.molinosystem.sistema_molino.requests.SuplyUsageRequest;
import com.molinosystem.sistema_molino.requests.SuplyUsageSearchRequest;

public interface ISuplyUsageService {
    SuplyUsageDto createSuplyUsage(SuplyUsageRequest suplyUsageRequest);
    Page<SuplyUsageDto> getAllSuplyUsage(int page, int pageSize);
    Page<SuplyUsageDto> searchSuplyUsage(SuplyUsageSearchRequest filter, int page, int pageSize);
    SuplyUsageDto getSuplyUsageById(Long id);
    SuplyUsageDto updateSuplyUsage(Long id, SuplyUsageRequest upSuplyUsage);
    SuplyUsageDto deleteSuplyUsage(Long id);
}
