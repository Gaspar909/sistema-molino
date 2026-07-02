package com.molinosystem.sistema_molino.services;

import org.springframework.data.domain.Page;

import com.molinosystem.sistema_molino.dtos.UnitDto;
import com.molinosystem.sistema_molino.requests.UnitRequest;

public interface IUnitService {

    public UnitDto createUnitDto(UnitRequest newUnit);
    public Page<UnitDto> getAllUnit(int page, int sizePage);
    public UnitDto getUnitById(Long id);
    public Page<UnitDto> searchUnit(String seach, int page, int sizePage);
    public UnitDto updateUnit(Long id, UnitRequest upUnit);
    public UnitDto enableUnit(Long id);
    public UnitDto disableUnit (Long id);
    public UnitDto deleteUnit (Long id);
}
