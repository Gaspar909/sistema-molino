package com.molinosystem.sistema_molino.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.molinosystem.sistema_molino.dtos.SuplyDto;
import com.molinosystem.sistema_molino.entities.Suply;
import com.molinosystem.sistema_molino.exceptions.BadRequestException;
import com.molinosystem.sistema_molino.exceptions.NoFoundException;
import com.molinosystem.sistema_molino.mappers.Mapper;
import com.molinosystem.sistema_molino.repositories.SuplyRepository;
import com.molinosystem.sistema_molino.requests.SuplyRequest;
import com.molinosystem.sistema_molino.requests.SuplySearchRequest;

import jakarta.persistence.criteria.Expression;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SuplyService implements ISuplyService{
    private final SuplyRepository suplyRepository;

    @Override
    public SuplyDto createSuply(SuplyRequest newSuplyRequest) {
        Suply newSuply = Suply.builder()
        .id(null)
        .name(newSuplyRequest.getName())
        .description(newSuplyRequest.getDescription())
        .stock(newSuplyRequest.getStock())
        .price(newSuplyRequest.getPrice())
        .active(true)
        .build();

        return Mapper.toDTO(suplyRepository.save(newSuply));
    }

    @Override
    public Page<SuplyDto> getAllSuplies(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Suply> result = suplyRepository.findAll(pageable);

        return result.map(Mapper::toDTO);
    }

    @Override
    public Page<SuplyDto> searchSuplies(int page, int pageSize, SuplySearchRequest search) {
        Specification<Suply> specification = Specification.where((root, query, cb) -> cb.conjunction());

        if (search.getSearch() != null && !search.getSearch().isEmpty()) {
            specification = specification.and((root, query, cb) -> {
                Expression<String> expression = 
                cb.concat(cb.concat(root.get("name"), " "), root.get("description"));

                return cb.like(cb.lower(expression), "%" + search.getSearch().toLowerCase() + "%");
            }
        );
        }

        if (search.getActive() != null) {
            specification = specification.and((root, query, cb) -> 
                cb.equal(root.get("active"), search.getActive()));
        }

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Suply> result = suplyRepository.findAll(specification, pageable);

        return result.map(Mapper::toDTO);
    }

    @Override
    public SuplyDto getSuplyById(Long id) {
        Suply suply = suplyRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Suply does not exist"));

        return Mapper.toDTO(suply);
    }

    @Override
    public Suply getSuplyEntityById(Long id) {
        return suplyRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Suply does not exist"));
    }

    @Override
    public SuplyDto updateSuply(Long id, SuplyRequest upSuply) {
        Suply suply = suplyRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Suply does not exist"));

        suply.setName(upSuply.getName() != null && !upSuply.getName().isEmpty() ? upSuply.getName() : suply.getName());
        suply.setDescription(upSuply.getDescription() != null && !upSuply.getDescription().isEmpty() ? upSuply.getDescription() : suply.getDescription());
        suply.setPrice(upSuply.getPrice() != null ? upSuply.getPrice() : suply.getPrice());
        suply.setStock(upSuply.getStock() != null ? upSuply.getStock() : suply.getStock());

        return Mapper.toDTO(suplyRepository.save(suply));
    }

    @Override
    public SuplyDto enableSuply(Long id) {
        Suply suply = suplyRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Suply does not exist"));

        if (suply.getActive()) throw new BadRequestException("Suply is alredy enable");

        suply.setActive(true);

        return Mapper.toDTO(suplyRepository.save(suply));
    }

    @Override
    public SuplyDto disableSuply(Long id) {
        Suply suply = suplyRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Suply does not exist"));

        if (!suply.getActive()) throw new BadRequestException("Suply is alredy disable");

        suply.setActive(false);

        return Mapper.toDTO(suplyRepository.save(suply));
    }

    @Override
    public SuplyDto deleteSuply(Long id) {
        Suply suply = suplyRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("Suply does not exist"));

        suplyRepository.delete(suply);

        return Mapper.toDTO(suply);
    }

}
