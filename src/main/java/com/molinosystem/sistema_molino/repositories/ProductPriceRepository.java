package com.molinosystem.sistema_molino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.molinosystem.sistema_molino.entities.ProductPrice;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {

    public List<ProductPrice> findByProductId(Long productId);
}
