package com.molinosystem.sistema_molino.services;


import org.springframework.data.domain.Page;

import com.molinosystem.sistema_molino.dtos.ProductPriceDto;
import com.molinosystem.sistema_molino.requests.ProductPriceRequest;

public interface IProductPriceService {
    public ProductPriceDto createProductPrice(ProductPriceRequest newProductPriceRequest);
    public Page<ProductPriceDto> getProductPriceByProduct(Long id, int page, int pageSize);
    public ProductPriceDto getProductPriceById(Long id);
    public ProductPriceDto updateProducPrice(Long id, ProductPriceRequest upProductPrice);
    public ProductPriceDto enableProductPice(Long id);
    public ProductPriceDto disableProductPrice (Long id);
    public ProductPriceDto deleteProductPrice (Long id);
}
