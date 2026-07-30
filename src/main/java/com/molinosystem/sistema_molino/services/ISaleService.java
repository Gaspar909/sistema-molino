package com.molinosystem.sistema_molino.services;

import org.springframework.data.domain.Page;

import com.molinosystem.sistema_molino.dtos.SaleCompleteDto;
import com.molinosystem.sistema_molino.dtos.SaleDto;
import com.molinosystem.sistema_molino.requests.SaleRequest;

public interface ISaleService {
    public SaleDto createSale(SaleRequest saleRequest);
    public Page<SaleDto> getAllSales(int page, int pageSize);
    public Page<SaleDto> searchSales (int page, int pageSize);
    public SaleCompleteDto getSaleComplete(Long id);
    public SaleCompleteDto updateSale(Long id, SaleRequest saleUp);
    public SaleDto deleteSale(Long id);
}
