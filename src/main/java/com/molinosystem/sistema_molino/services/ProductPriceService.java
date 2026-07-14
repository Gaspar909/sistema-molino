package com.molinosystem.sistema_molino.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.molinosystem.sistema_molino.dtos.ProductPriceDto;
import com.molinosystem.sistema_molino.entities.ProductPrice;
import com.molinosystem.sistema_molino.exceptions.BadRequestException;
import com.molinosystem.sistema_molino.exceptions.NoFoundException;
import com.molinosystem.sistema_molino.mappers.Mapper;
import com.molinosystem.sistema_molino.repositories.ProductPriceRepository;
import com.molinosystem.sistema_molino.requests.ProductPriceRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductPriceService implements IProductPriceService{

    private final ProductPriceRepository productPriceRepository;

    private final ProductService productService;

    @Override
    public ProductPriceDto createProductPrice(ProductPriceRequest newProductPriceRequest) {
        ProductPrice newProductPrice = ProductPrice.builder()
        .id(null)
        .product(productService.getProductEntityById(newProductPriceRequest.getProductId()))
        .name(newProductPriceRequest.getName())
        .price(newProductPriceRequest.getPrice())
        .active(newProductPriceRequest.getActive())
        .build();

        return Mapper.toDTO(productPriceRepository.save(newProductPrice));
    }

    @Override
    public Page<ProductPriceDto> getProductPriceByProduct(Long id, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<ProductPrice> result = productPriceRepository.findByProductId(id, pageable);

        return result.map(Mapper::toDTO);
    }

    @Override
    public ProductPriceDto getProductPriceById(Long id) {
        return Mapper.toDTO(
            productPriceRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Price does not exist"))
        );
    }

    @Override
    public ProductPriceDto updateProducPrice(Long id, ProductPriceRequest upProductPrice) {
        ProductPrice productPrice = productPriceRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Price does not exist"));

        productPrice.setName(upProductPrice.getName());
        productPrice.setPrice(upProductPrice.getPrice());
        productPrice.setProduct(null);


        return Mapper.toDTO(productPriceRepository.save(productPrice));
    }

    @Override
    public ProductPriceDto enableProductPice(Long id) {
        ProductPrice productPrice = productPriceRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Price does not exist"));

        if(productPrice.getActive()) throw new BadRequestException("Price is alredy enable");

        productPrice.setActive(true);

        return Mapper.toDTO(productPriceRepository.save(productPrice));
    }

    @Override
    public ProductPriceDto disableProductPrice(Long id) {
        ProductPrice productPrice = productPriceRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Price does not exist"));

        if(!productPrice.getActive()) throw new BadRequestException("Price is alredy disable");

        productPrice.setActive(false);

        return Mapper.toDTO(productPriceRepository.save(productPrice));
    }

    @Override
    public ProductPriceDto deleteProductPrice(Long id) {
        ProductPrice productPrice = productPriceRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Price does not exist"));

        productPriceRepository.delete(productPrice);

        return Mapper.toDTO(productPrice);
    }

}
