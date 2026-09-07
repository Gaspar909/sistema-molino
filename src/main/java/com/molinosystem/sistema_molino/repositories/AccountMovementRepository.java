package com.molinosystem.sistema_molino.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.molinosystem.sistema_molino.entities.AccountMovement;


public interface AccountMovementRepository extends JpaRepository<AccountMovement, Long>, JpaSpecificationExecutor<AccountMovement>{
    Page<AccountMovement> findByAccountId(Long id, Pageable pageable);
}
