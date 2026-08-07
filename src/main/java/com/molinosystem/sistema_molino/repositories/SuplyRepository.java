package com.molinosystem.sistema_molino.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.molinosystem.sistema_molino.entities.Suply;

public interface SuplyRepository extends JpaRepository<Suply, Long>, JpaSpecificationExecutor<Suply> {

}
