package com.molinosystem.sistema_molino.services;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.molinosystem.sistema_molino.dtos.AccountDto;
import com.molinosystem.sistema_molino.entities.Account;
import com.molinosystem.sistema_molino.exceptions.BadRequestException;
import com.molinosystem.sistema_molino.exceptions.NoFoundException;
import com.molinosystem.sistema_molino.mappers.Mapper;
import com.molinosystem.sistema_molino.repositories.AccountRepository;
import com.molinosystem.sistema_molino.requests.AccountRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {
    private final AccountRepository accountRepository;

    @Override
    public Page<AccountDto> getAllAccounts(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Account> result = accountRepository.findAll(pageable);

        return result.map(Mapper::toDTO);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(
            () -> new NoFoundException("Account does not exist")
        );

        return Mapper.toDTO(account);
    }

    @Override
    public Page<AccountDto> searchAccount(int page, int pageSize, String search, Boolean isActive) {
        Specification<Account> specification = Specification.where((root, query, cb) -> cb.conjunction());
        
        if(search != null && !search.isEmpty()){
            specification = specification.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + search + "%"));
        }

        if (isActive != null) {
            specification = specification.and((root, query, cb) ->
            cb.equal(root.get("active"), isActive));
        }

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Account> result = accountRepository.findAll(specification, pageable);
        
        return result.map(Mapper::toDTO);
    }

    @Override
    public AccountDto createAccount(AccountRequest newAccountRequest) {
        if (newAccountRequest.getName() == null || newAccountRequest.getName().isEmpty() )
            throw new BadRequestException("Account Name is invalid");
        
        Account newAccount = Account.builder()
        .id(null)
        .name(newAccountRequest.getName())
        .balance(BigDecimal.ZERO)
        .description(newAccountRequest.getDescription())
        .active(true)
        .build();

        return Mapper.toDTO(accountRepository.save(newAccount));
    }

    @Override
    public AccountDto updateAccount(Long id, AccountRequest upAccount) {
        Account account = accountRepository.findById(id).orElseThrow(
            () -> new NoFoundException("Account does not exist")
        );

        if (upAccount.getName() != null && !upAccount.getName().isEmpty()) {
            account.setName(upAccount.getName());
        }

        if (upAccount.getDescription() != null && !upAccount.getDescription().isEmpty()) {
            account.setDescription(upAccount.getDescription());
        }
        
        return Mapper.toDTO(accountRepository.save(account));
    }

    @Override
    public AccountDto enableAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(
            () -> new NoFoundException("Account does not exist")
        );

        if (account.getActive()) throw new BadRequestException("Account is alredy enable");

        account.setActive(true);

        return Mapper.toDTO(account);
    }

    @Override
    public AccountDto disableAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(
            () -> new NoFoundException("Account does not exist")
        );

        if (!account.getActive()) throw new BadRequestException("Account is alredy disable");

        account.setActive(false);

        return Mapper.toDTO(account);
    }

    @Override
    public AccountDto deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(
            () -> new NoFoundException("Account does not exist")
        );

        accountRepository.delete(account);

        return Mapper.toDTO(account);
    }

}
