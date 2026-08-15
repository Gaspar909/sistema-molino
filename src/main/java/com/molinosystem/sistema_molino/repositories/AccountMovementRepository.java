package com.molinosystem.sistema_molino.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.molinosystem.sistema_molino.entities.Account;
import com.molinosystem.sistema_molino.entities.AccountMovement;
import java.util.List;


public interface AccountMovementRepository extends JpaRepository<AccountMovement, Long>, JpaSpecificationExecutor<AccountMovement>{
    List<AccountMovement> findByAccount(Account account);
}
