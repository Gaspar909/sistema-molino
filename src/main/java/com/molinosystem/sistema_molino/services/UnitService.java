package com.molinosystem.sistema_molino.services;

import jakarta.persistence.criteria.Expression;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.molinosystem.sistema_molino.dtos.UnitDto;
import com.molinosystem.sistema_molino.entities.Unit;
import com.molinosystem.sistema_molino.exceptions.BadRequestException;
import com.molinosystem.sistema_molino.exceptions.NoFoundException;
import com.molinosystem.sistema_molino.mappers.Mapper;
import com.molinosystem.sistema_molino.repositories.UnitRepository;
import com.molinosystem.sistema_molino.requests.UnitRequest;

@Service
@RequiredArgsConstructor
public class UnitService implements IUnitService {

    private final UnitRepository unitRepository;

    @Override
    public UnitDto createUnitDto(UnitRequest newUnitRequest) {
        Unit newUnit = Unit.builder()
        .id(null)
        .name(newUnitRequest.getName())
        .description(newUnitRequest.getDescription())
        .symbol(newUnitRequest.getSymbol())
        .ints(newUnitRequest.getInts())
        .active(true)
        .build();

        return Mapper.toDTO(unitRepository.save(newUnit));
    }

    @Override
    public Page<UnitDto> getAllUnit(int page, int sizePage) {
        Pageable pageable = PageRequest.of(page, sizePage);
        Page<Unit> result = unitRepository.findAll(pageable);

        return result.map(Mapper::toDTO);
    }

    @Override
    public UnitDto getUnitById(Long id) {
        Unit result = unitRepository.getReferenceById(id);
        return Mapper.toDTO(result);
    }

    @Override
    public Page<UnitDto> searchUnit(String search, int page, int sizePage) {
        Specification<Unit> specification = Specification.where((root, query, cb) -> cb.conjunction());

        if (!search.isEmpty() && search != null) {
            specification = specification.and((root, query, cb) -> {
                Expression<String> concatExpression = cb.concat(
                    cb.concat(root.get("name"), " "), root.get("symbol")
                );
                return cb.like(cb.lower(concatExpression), "%" + search.toLowerCase() + "%");
            });
        }

        Pageable pageable = PageRequest.of(page, sizePage);
        Page<Unit> result = unitRepository.findAll(specification, pageable);

        return result.map(Mapper::toDTO);
    }

    @Override
    public UnitDto updateUnit(Long id, UnitRequest upUnit) {
        Unit unitTmp = unitRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Unit is not exist"));
        
        unitTmp.setName(upUnit.getName());
        unitTmp.setDescription(upUnit.getDescription());
        unitTmp.setSymbol(upUnit.getSymbol());
        unitTmp.setInts(upUnit.getInts());

        return Mapper.toDTO(unitRepository.save(unitTmp));
    }

    @Override
    public UnitDto enableUnit(Long id) {
        Unit unitTmp = unitRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Unit is not exist"));

        if(unitTmp.getActive()) throw new BadRequestException("Unit is alredy enable");

        unitTmp.setActive(true);

        return Mapper.toDTO(unitRepository.save(unitTmp));
    }

    @Override
    public UnitDto disableUnit(Long id) {
        Unit unitTmp = unitRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Unit is not exist"));

        if(!unitTmp.getActive()) throw new BadRequestException("Unit is alredy disable");

        unitTmp.setActive(false);

        return Mapper.toDTO(unitRepository.save(unitTmp));
    }

    @Override
    public UnitDto deleteUnit(Long id) {
        Unit unitTmp = unitRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Unit is not exist"));

        unitRepository.delete(unitTmp);

        return Mapper.toDTO(unitTmp);
    }

}
