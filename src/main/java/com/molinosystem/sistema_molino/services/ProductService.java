package com.molinosystem.sistema_molino.services;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class ProductService implements IProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UnitService unitService;

    @Autowired 
    ProductCategoryService productCategoryService;

    @Override
    public ProductDto createProduct(ProductRequest productRequest) {

        UnitDto unitTmp = unitService.getUnitById(productRequest.getUnitId());
        ProductCategoryDto productCategoryTmp = productCategoryService.getProductCategoryById(productRequest.getCategoryId());

        Product newProduct = Product.builder()
        .id(null)
        .barCode(productRequest.getBarcode())
        .name(productRequest.getName())
        .description(productRequest.getDescripotion())
        .stock( productRequest.getStock() != null || productRequest.getStock() < 0 ? productRequest.getStock() : 0 )
        .unit(null) 
        .active(true)
        .build();
        
        //return Mapper.toDTO(productRepository.save(newProduct));
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createProduct'");
    }

    @Override
    public Page<ProductDto> getAllProducts(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Product> result = productRepository.findAll(pageable);

        //return result.map(Mapper::toDTO);
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllProducts'");
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
        //return result.map(Mapper::toDTO);
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchProduct'");
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).
        orElseThrow( () -> new NoFoundException("product is not exist"));

        //return Mapper.toDTO(product )
        throw new UnsupportedOperationException("Unimplemented method 'searchProduct'");
    }

    @Override
    public ProductDto updateProduct(Long id, ProductRequest productRquest) {
        Product product = productRepository.findById(id).
        orElseThrow( () -> new NoFoundException("product is not exist"));

        product.setName(productRquest.getName());
        product.setBarCode(productRquest.getBarcode());
        product.setDescription(productRquest.getDescripotion());
        product.setStock(productRquest.getStock());

        productRepository.save(product);
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProduct'");
    }

    @Override
    public ProductDto enableProduct(Long id) {
        Product product = productRepository.findById(id).
        orElseThrow( () -> new NoFoundException("product is not exist"));

        if (product.getActive()) throw new BadRequestException("product is alredy enable");
        
        product.setActive(true);

        productRepository.save(product);
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enableProduct'");
    }

    @Override
    public ProductDto disableProduct(Long id) {
        Product product = productRepository.findById(id).
        orElseThrow( () -> new NoFoundException("product is not exist"));

        if (!product.getActive()) throw new BadRequestException("product is alredy disable");
        
        product.setActive(false);

        productRepository.save(product);
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disableProduct'");
    }

    @Override
    public ProductDto deleteProduct(Long id) {
        Product product = productRepository.findById(id).
        orElseThrow( () -> new NoFoundException("product is not exist"));

        productRepository.delete(product);
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProduct'");
    }

}
