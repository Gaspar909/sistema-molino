package com.molinosystem.sistema_molino.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.molinosystem.sistema_molino.dtos.AccountMovementDto;
import com.molinosystem.sistema_molino.entities.Account;
import com.molinosystem.sistema_molino.entities.AccountMovement;
import com.molinosystem.sistema_molino.entities.User;
import com.molinosystem.sistema_molino.enums.MovementType;
import com.molinosystem.sistema_molino.exceptions.BadRequestException;
import com.molinosystem.sistema_molino.exceptions.NoFoundException;
import com.molinosystem.sistema_molino.mappers.Mapper;
import com.molinosystem.sistema_molino.repositories.AccountMovementRepository;
import com.molinosystem.sistema_molino.repositories.UserRepository;
import com.molinosystem.sistema_molino.requests.AccountMovementRequest;
import com.molinosystem.sistema_molino.requests.AccountMovementSearchRequest;
import com.molinosystem.sistema_molino.requests.TransferAccountMovementRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountMovementService implements IAccountMovementService  {

    private final AccountMovementRepository accountMovementRepository;
    private final UserRepository userRepository;

    private final AccountService accountService;


    @Override
    @Transactional
    public AccountMovementDto createAccountMovement(AccountMovementRequest request) {
        if(request.getAccountId() == null || request.getAccountId() <= 0) throw new BadRequestException("Account is required");

        Account account = accountService.getAccountEntityById(request.getAccountId());
        

        if (request.getDescription() == null || request.getDescription().trim().isEmpty()) throw new BadRequestException("Description is required");

        if (request.getMovementType() == null) throw new BadRequestException("Type is required");

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) throw new BadRequestException("Amount is required");

        switch (request.getMovementType()) {
            case INCOME:
                account.setBalance(account.getBalance().add(request.getAmount()));
                break;
            case EXPENSE:
                if (account.getBalance().compareTo(request.getAmount()) < 0) 
                throw new BadRequestException("Insufficient balance in account: " + account.getName());
                
                account.setBalance(account.getBalance().subtract(request.getAmount()));
                break;
            default:
                throw new BadRequestException("Movement type undefined");
        }
        
        
        AccountMovement newAccountMovement = AccountMovement.builder()
        .id(null)
        .account(account)
        .amount(request.getAmount())
        .movementType(request.getMovementType())
        .createdAt(Timestamp.valueOf(LocalDateTime.now()))
        .description(request.getDescription())
        .user(getCurrentUser())
        .build();

        return Mapper.toDTO(accountMovementRepository.save(newAccountMovement));
    }

    @Override
    @Transactional(readOnly= true)
    public Page<AccountMovementDto> getAllAccountMovement(int page, int pageSize, Long accountId) {
        if (accountId == null || accountId <= 0) throw new BadRequestException("Account is required");

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<AccountMovement> result = accountMovementRepository.findByAccountId(accountId, pageable);

        return result.map(Mapper::toDTO);
    }

    @Override
    public Page<AccountMovementDto> searchAccountMovement(int page, int pageSize, AccountMovementSearchRequest search) {
        Specification<AccountMovement> specification = Specification.where((root, query, cb) -> cb.conjunction());

        if(search.getAccountId() != null && search.getAccountId() > 0)
            specification = specification.and((root, query, cb) -> cb.equal(root.get("account").get("id"), search.getAccountId()));

        if(search.getSearch() != null && !search.getSearch().trim().isEmpty())
            specification = specification.and((root, query, cb) -> 
            cb.like(root.get("description"), "%" + search.getSearch() + "%")
        );

        if(search.getStartDate() != null)
            specification = specification.and((root, query, cb) -> 
            cb.greaterThanOrEqualTo(root.get("createdAt"), search.getStartDate())
        );

        if(search.getEndDate() != null)
            specification = specification.and((root, query, cb) -> 
            cb.lessThanOrEqualTo(root.get("createdAt"), search.getEndDate())
        );

        if(search.getUserId() != null && search.getUserId() > 0)
            specification = specification.and((root, query, cb) -> (
        cb.equal(root.get("user").get("id"), search.getUserId())   
        ));

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<AccountMovement> result = accountMovementRepository.findAll(specification, pageable);

        return result.map(Mapper::toDTO);
    }

    @Override
    @Transactional
    public List<AccountMovementDto> transferBetweenAccounts(TransferAccountMovementRequest request){
        if(request == null) 
            throw new BadRequestException("Invalid request");

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) 
        throw new BadRequestException("The transfer amount must be greater than zero");

        if (request.getSourceAccountId().equals(request.getDestinationAccountId())) 
        throw new BadRequestException("Source and destination accounts cannot be the same");

        Account sourceAccount = accountService.getAccountEntityById(request.getSourceAccountId());
        Account destinationAccount = accountService.getAccountEntityById(request.getDestinationAccountId());

        if (sourceAccount.getBalance().compareTo(request.getAmount()) < 0) 
        throw new BadRequestException("Insufficient funds in account: " + sourceAccount.getName());

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(request.getAmount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(request.getAmount()));
        
        User user = getCurrentUser();

        String extraNote = (request.getDescription() != null && !request.getDescription().trim().isEmpty())  
            ? " (" + request.getDescription().trim()  + ").": "";
        
        String descriptionSource = "Transfer to account " + destinationAccount.getName() + extraNote;
        String descriptionDestination = "Transfer from account " + sourceAccount.getName() + extraNote;

        Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

        AccountMovement accountOutMovement = AccountMovement.builder()
        .account(sourceAccount)
        .amount(request.getAmount())
        .movementType(MovementType.TRANSFER_OUT)
        .description(descriptionSource)
        .createdAt(createdAt)
        .user(user)
        .build();

        AccountMovement accountInMovement = AccountMovement.builder()
        .account(destinationAccount)
        .amount(request.getAmount())
        .movementType(MovementType.TRANSFER_IN)
        .description(descriptionDestination)
        .createdAt(createdAt)
        .user(user)
        .build();

        List<AccountMovementDto> result = new ArrayList<>();

        result.add(
            Mapper.toDTO(accountMovementRepository.save(accountOutMovement))
        );
        result.add(
            Mapper.toDTO(accountMovementRepository.save(accountInMovement))
        );

        return result;
    }

    @Override
    @Transactional
    public AccountMovementDto deleteAccountMovement(Long id) {
        AccountMovement accountMovement = accountMovementRepository.findById(id).orElseThrow( () -> new NoFoundException("Account movement no found"));

        Account account = accountMovement.getAccount();

        switch (accountMovement.getMovementType()) {
            case INCOME:
                if (account.getBalance().compareTo(accountMovement.getAmount()) < 0) 
                throw new BadRequestException("Insufficient balance in account: " + account.getName());
                
                account.setBalance(account.getBalance().subtract(accountMovement.getAmount()));
                break;
            case EXPENSE:
                account.setBalance(account.getBalance().add(accountMovement.getAmount()));
                break;
            default:
                throw new BadRequestException("Movement type undefined");
        }

        accountMovementRepository.delete(accountMovement);

        return Mapper.toDTO(accountMovement);
    }

    private User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Please Log in");
        }

        String username = authentication.getName();
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User no found: " + username));
    }


}
