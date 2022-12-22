package com.irfan.atm.services;

import com.irfan.atm.models.entities.Account;
import com.irfan.atm.repositories.AccountRepository;
import com.irfan.atm.repositories.TransactionRepository;
import com.irfan.atm.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public Account getAccount(String accountNumber) {
        Optional<Account> account = accountRepository
                .findByAccountNumber(accountNumber);

        return account.orElse(null);
    }

    public Account createAccount(String ownerName) {
        CodeGenerator codeGenerator = new CodeGenerator();
        Account newAccount = Account.builder()
                .ownerName(ownerName)
                .accountNumber(codeGenerator.generateAccountNumber())
                .balance(0.00)
                .build();

        return accountRepository.save(newAccount);
    }
}
