package com.molinosystem.sistema_molino.services;

import jakarta.persistence.criteria.Expression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.molinosystem.sistema_molino.dtos.ProductCategoryDto;
import com.molinosystem.sistema_molino.entities.ProductCategory;
import com.molinosystem.sistema_molino.exceptions.BadRequestException;
import com.molinosystem.sistema_molino.exceptions.NoFoundException;
import com.molinosystem.sistema_molino.mappers.Mapper;
import com.molinosystem.sistema_molino.repositories.ProductCategoryRepository;
import com.molinosystem.sistema_molino.requests.ProductCategoryRequest;

public class ProductCategoryService implements IProductCategoryService {
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategoryDto createProductCategory(ProductCategoryRequest newProductCategory) {
        ProductCategory newItem = ProductCategory.builder()
        .id(null)
        .name(newProductCategory.getName())
        .description(newProductCategory.getDescription())
        .active(true)
        .build();

        return Mapper.toDTO(productCategoryRepository.save(newItem));
    }

    @Override
    public Page<ProductCategoryDto> getAllProductCategory(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<ProductCategory> result = productCategoryRepository.findAll(pageable);

        return result.map(Mapper::toDTO);
    }

    @Override
    public ProductCategoryDto getProductCategoryById(Long id) {
        ProductCategory result = productCategoryRepository.getReferenceById(id);
        return Mapper.toDTO(result);
    }

    @Override
    public Page<ProductCategoryDto> searchProductCategory(int page, int pageSize, String search, Boolean active) {
        Specification specification = Specification.where((roos, query, cb) -> cb.conjunction());
        if(search != null && !search.isEmpty()){
            specification = specification.and((root, query, cb) -> {
                Expression<String> concatExpression = cb.concat(cb.concat(root.get("name"), " "), root.get("description"));
                return cb.like(cb.lower(concatExpression), "%" + search.toLowerCase() + "%");
            });
        }
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<ProductCategory> result = productCategoryRepository.findAll(specification, pageable);

        return result.map(Mapper::toDTO);
    }

    @Override
    public ProductCategoryDto updateProductCategory(Long id, ProductCategoryRequest upProductCategory) {
        ProductCategory productCategory = productCategoryRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Category is no exist"));

        productCategory.setName(upProductCategory.getName());
        productCategory.setDescription(upProductCategory.getDescription());

        return Mapper.toDTO(productCategoryRepository.save(productCategory));
    }

    @Override
    public ProductCategoryDto enableProductCategory(Long id) {
        ProductCategory productCategory = productCategoryRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Category is no exist"));

        if (productCategory.getActive()) throw new BadRequestException("Category is alredy enable");

        productCategory.setActive(true);

        return Mapper.toDTO(productCategoryRepository.save(productCategory));
    }

    @Override
    public ProductCategoryDto disableProductCategory(Long id) {
        ProductCategory productCategory = productCategoryRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Category is no exist"));

        if (!productCategory.getActive()) throw new BadRequestException("Category is alredy disable");

        productCategory.setActive(false);

        return Mapper.toDTO(productCategoryRepository.save(productCategory));
    }

    @Override
    public ProductCategoryDto deleteProductCategory(Long id) {
        ProductCategory productCategory = productCategoryRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Category is no exist"));

        productCategoryRepository.delete(productCategory);
        
        return Mapper.toDTO(productCategoryRepository.save(productCategory));
    }

}
