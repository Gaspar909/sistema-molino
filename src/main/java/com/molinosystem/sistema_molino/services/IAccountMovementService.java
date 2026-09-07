package com.molinosystem.sistema_molino.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.molinosystem.sistema_molino.dtos.AccountMovementDto;
import com.molinosystem.sistema_molino.requests.AccountMovementRequest;
import com.molinosystem.sistema_molino.requests.AccountMovementSearchRequest;
import com.molinosystem.sistema_molino.requests.TransferAccountMovementRequest;

public interface IAccountMovementService {
    AccountMovementDto createAccountMovement(AccountMovementRequest request);
    Page<AccountMovementDto> getAllAccountMovement(int page, int pageSize, Long accountId);
    Page<AccountMovementDto> searchAccountMovement(int page, int pageSize, AccountMovementSearchRequest search);
    List<AccountMovementDto> transferBetweenAccounts(TransferAccountMovementRequest request);
    AccountMovementDto deleteAccountMovement(Long id);
}
