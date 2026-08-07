package com.molinosystem.sistema_molino.services;

import org.springframework.data.domain.Page;

import com.molinosystem.sistema_molino.dtos.SuplyDto;
import com.molinosystem.sistema_molino.entities.Suply;
import com.molinosystem.sistema_molino.requests.SuplyRequest;

public interface ISuplyService {
    SuplyDto createSuply(SuplyRequest newSuplyRequest);
    Page<SuplyDto> getAllSuplies(int page, int pageSize);
    Page<SuplyDto> searchSuplies(int page, int pageSize, String name);
    SuplyDto getSuplyById(Long id);
    Suply getSuplyEntityById(Long id);
    SuplyDto updateSuply(Long id, SuplyRequest upSuply);
    SuplyDto enableSuply(Long id);
    SuplyDto disableSuply(Long id);
    SuplyDto deleteSuply(Long id);
}
