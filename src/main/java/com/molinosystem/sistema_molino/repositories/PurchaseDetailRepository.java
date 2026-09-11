package com.molinosystem.sistema_molino.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.molinosystem.sistema_molino.entities.PurchaseDetails;

public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetails, Long>, JpaSpecificationExecutor<PurchaseDetails> {

}
