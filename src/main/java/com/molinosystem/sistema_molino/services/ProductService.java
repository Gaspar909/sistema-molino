package com.molinosystem.sistema_molino.services;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.molinosystem.sistema_molino.dtos.ProductCategoryDto;
import com.molinosystem.sistema_molino.dtos.ProductDto;
import com.molinosystem.sistema_molino.dtos.UnitDto;
import com.molinosystem.sistema_molino.entities.Product;
import com.molinosystem.sistema_molino.exceptions.BadRequestException;
import com.molinosystem.sistema_molino.exceptions.NoFoundException;
import com.molinosystem.sistema_molino.mappers.Mapper;
import com.molinosystem.sistema_molino.repositories.ProductRepository;
import com.molinosystem.sistema_molino.requests.ProductRequest;
import com.molinosystem.sistema_molino.requests.ProductSearchRequest;

import jakarta.persistence.criteria.Expression;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    
    private final ProductRepository productRepository;

    
    private final UnitService unitService;

    
    private final ProductCategoryService productCategoryService;

    @Override
    public ProductDto createProduct(ProductRequest productRequest) {

        UnitDto unitTmp = unitService.getUnitById(productRequest.getUnitId());
        ProductCategoryDto productCategoryTmp = productCategoryService.getProductCategoryById(productRequest.getCategoryId());
        
        if(productRepository.existsByBarCode(productRequest.getBarcode()))
            throw new BadRequestException("This Barcode is alresdy exist");

        Product newProduct = Product.builder()
        .id(null)
        .barCode(productRequest.getBarcode())
        .name(productRequest.getName())
        .description(productRequest.getDescripotion())
        .stock( productRequest.getStock() != null || productRequest.getStock().compareTo(BigDecimal.ZERO) < 0 ? 
            productRequest.getStock() : BigDecimal.ZERO)
        .unit(Mapper.toEntity(unitTmp))
        .category(Mapper.toEntity(productCategoryTmp))
        .active(true)
        .build();
        
        return Mapper.toDTO(productRepository.save(newProduct));
    }

    @Override
    public Page<ProductDto> getAllProducts(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Product> result = productRepository.findAll(pageable);

        return result.map(Mapper::toDTO);
    }

    @Override
    public Page<ProductDto> searchProduct(ProductSearchRequest searchRequest) {

        Specification<Product> specification = Specification.where((root, query, cb) -> cb.conjunction());

        if(searchRequest.getSearch() != null && !searchRequest.getSearch().isEmpty() ){
            specification = specification.and((root, query, cb) -> {
                Expression<String> concatExpression = 
                cb.concat(cb.concat(root.get("name"), " "), root.get("barcode"));

                return cb.like(cb.lower(concatExpression), "%" + searchRequest.getSearch().toLowerCase() + "%");
            });
        }

        if(searchRequest.getActive() != null ){
            specification = specification.and((root, query, cb) -> {
                return cb.equal(root.get("active"), searchRequest.getActive());
            });
        }

        if (searchRequest.getUnitId() != null && searchRequest.getUnitId() > 0) {
            specification = specification.and((root, query, cb) ->
            {
                return cb.equal(root.get("unit").get("id"), searchRequest.getUnitId());
            }
        );
        }

        if(searchRequest.getCategoryId() != null && searchRequest.getCategoryId() > 0){
            specification = specification.and((root, query, cb) -> {
                return cb.equal(root.get("category").get("id"), searchRequest.getCategoryId());
            });
        }

        Pageable pageable = PageRequest.of(searchRequest.getPage(), searchRequest.getPageSize());

        Page<Product> result = productRepository.findAll(specification, pageable);

        return result.map(Mapper::toDTO);
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).
        orElseThrow( () -> new NoFoundException("product is not exist"));

        return Mapper.toDTO(product);
    }

    
    @Override
    public Product getProductEntityById(Long id) {
        Product product = productRepository.findById(id).
        orElseThrow( () -> new NoFoundException("product is not exist"));

        return product;
    }

    @Override
    public ProductDto updateProduct(Long id, ProductRequest productRquest) {
        Product product = productRepository.findById(id).
        orElseThrow( () -> new NoFoundException("product is not exist"));

        product.setName(productRquest.getName());
        product.setBarCode(productRquest.getBarcode());
        product.setDescription(productRquest.getDescripotion());
        product.setStock(productRquest.getStock());

        return Mapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductDto enableProduct(Long id) {
        Product product = productRepository.findById(id).
        orElseThrow( () -> new NoFoundException("product is not exist"));

        if (product.getActive()) throw new BadRequestException("product is alredy enable");
        
        product.setActive(true);

        return Mapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductDto disableProduct(Long id) {
        Product product = productRepository.findById(id).
        orElseThrow( () -> new NoFoundException("product is not exist"));

        if (!product.getActive()) throw new BadRequestException("product is alredy disable");
        
        product.setActive(false);

        return Mapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductDto deleteProduct(Long id) {
        Product product = productRepository.findById(id).
        orElseThrow( () -> new NoFoundException("product is not exist"));

        productRepository.delete(product);
        return Mapper.toDTO(product);
    }

}
