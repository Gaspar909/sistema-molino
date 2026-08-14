package com.molinosystem.sistema_molino.services;

import org.springframework.data.domain.Page;

import com.molinosystem.sistema_molino.dtos.AccountDto;
import com.molinosystem.sistema_molino.requests.AccountRequest;

public interface IAccountService {
    Page<AccountDto> getAllAccounts (int page, int pageSize);
    AccountDto getAccountById (Long id);
    Page<AccountDto> searchAccount (int page, int pageSize, String search, Boolean isActive);
    AccountDto createAccount (AccountRequest newAccountRequest);
    AccountDto updateAccount (Long id, AccountRequest upAccount);
    AccountDto disableAccount (Long id);
    AccountDto deleteAccount (Long id);
}
