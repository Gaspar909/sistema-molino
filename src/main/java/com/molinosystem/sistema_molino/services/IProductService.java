package com.molinosystem.sistema_molino.services;

import org.springframework.data.domain.Page;

import com.molinosystem.sistema_molino.dtos.ProductDto;
import com.molinosystem.sistema_molino.entities.Product;
import com.molinosystem.sistema_molino.requests.ProductRequest;
import com.molinosystem.sistema_molino.requests.ProductSearchRequest;

public interface IProductService {
    public ProductDto createProduct(ProductRequest productRequest);
    public Page<ProductDto> getAllProducts(int page, int pageSize);
    public Page<ProductDto> searchProduct (ProductSearchRequest searchRequest);
    public ProductDto getProductById(Long id);
    public Product getProductEntityById(Long id);
    public ProductDto updateProduct (Long id, ProductRequest productRquest);
    public ProductDto enableProduct (Long id);
    public ProductDto disableProduct(Long id);
    public ProductDto deleteProduct(Long id);
}
