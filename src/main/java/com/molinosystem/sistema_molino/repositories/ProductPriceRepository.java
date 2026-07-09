package com.molinosystem.sistema_molino.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.molinosystem.sistema_molino.entities.ProductPrice;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {

    public Page<ProductPrice> findByProductId(Long productId, Pageable pageable);
}
