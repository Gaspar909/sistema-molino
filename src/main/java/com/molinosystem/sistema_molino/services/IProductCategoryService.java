package com.molinosystem.sistema_molino.services;

import org.springframework.data.domain.Page;

import com.molinosystem.sistema_molino.dtos.ProductCategoryDto;
import com.molinosystem.sistema_molino.requests.ProductCategoryRequest;

public interface IProductCategoryService {
    public ProductCategoryDto createProductCategory(ProductCategoryRequest newProductCategory);
    public Page<ProductCategoryDto> getAllProductCategory(int page, int pageSize);
    public ProductCategoryDto getProductCategoryById(Long id);
    public Page<ProductCategoryDto> searchProductCategory(int page, int pageSize, String search, Boolean active);
    public ProductCategoryDto updateProductCategory(Long id, ProductCategoryRequest upProductCategory);
    public ProductCategoryDto enableProductCategory(Long id);
    public ProductCategoryDto disableProductCategory(Long id);
    public ProductCategoryDto deleteProductCategory(Long id);
}
