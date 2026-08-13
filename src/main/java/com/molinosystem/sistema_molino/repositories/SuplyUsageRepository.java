package com.molinosystem.sistema_molino.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.molinosystem.sistema_molino.entities.SuplyUsage;

public interface SuplyUsageRepository extends JpaRepository<SuplyUsage, Long>, JpaSpecificationExecutor<SuplyUsage>{

}
