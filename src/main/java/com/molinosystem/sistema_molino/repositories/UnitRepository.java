package com.molinosystem.sistema_molino.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.molinosystem.sistema_molino.entities.Unit;
import java.util.List;


public interface UnitRepository extends JpaRepository<Unit, Long>, JpaSpecificationExecutor<Unit> {
    public List<Unit> findByName(String name); 
    public List<Unit> findBySymbol(String symbol);
}
