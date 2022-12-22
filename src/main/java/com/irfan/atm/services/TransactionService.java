package com.irfan.atm.services;

import com.irfan.atm.models.dto.CreditInput;
import com.irfan.atm.models.dto.DebitInput;
import com.irfan.atm.models.dto.TransferInput;
import com.irfan.atm.models.entities.Account;
import com.irfan.atm.models.entities.Action;
import com.irfan.atm.models.entities.Transaction;
import com.irfan.atm.models.entities.Type;
import com.irfan.atm.repositories.AccountRepository;
import com.irfan.atm.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    public void updateAccountBalance(Account account, double amount, Action action) {
        if (action == Action.DEBIT) {
            account.setBalance((account.getBalance() - amount));
        } else if (action == Action.CREDIT) {
            account.setBalance((account.getBalance() + amount));
        }
        accountRepository.save(account);
    }

    public boolean isAmountAvailable(double amount, double accountBalance) {
        return (accountBalance - amount) >= 0;
    }

    public boolean makeTransfer(TransferInput transferInput) {
        String sourceAccountNumber = transferInput.getSourceAccountNumber();
        Optional<Account> sourceAccount = accountRepository
                .findByAccountNumber(sourceAccountNumber);

        String targetAccountNumber = transferInput.getTargetAccountNumber();
        Optional<Account> targetAccount = accountRepository
                .findByAccountNumber(targetAccountNumber);

        if (sourceAccount.isPresent() && targetAccount.isPresent()) {
            if (isAmountAvailable(transferInput.getAmount(), sourceAccount.get().getBalance())) {
                Transaction sourceTransaction = Transaction.builder()
                        .account(sourceAccount.get())
                        .amount(transferInput.getAmount())
                        .targetAccountId(targetAccount.get().getId())
                        .targetOwnerName(targetAccount.get().getOwnerName())
                        .date(LocalDateTime.now())
                        .type(Type.TRANSFER)
                        .action(Action.DEBIT)
                        .build();

                Transaction targetTransaction = Transaction.builder()
                        .account(targetAccount.get())
                        .amount(transferInput.getAmount())
                        .targetAccountId(sourceAccount.get().getId())
                        .targetOwnerName(sourceAccount.get().getOwnerName())
                        .date(LocalDateTime.now())
                        .type(Type.TRANSFER)
                        .action(Action.CREDIT)
                        .build();

                updateAccountBalance(sourceAccount.get(), sourceTransaction.getAmount(), Action.DEBIT);
                updateAccountBalance(targetAccount.get(), targetTransaction.getAmount(), Action.CREDIT);

                sourceTransaction.setBalance(sourceAccount.get().getBalance());
                targetTransaction.setBalance(targetAccount.get().getBalance());

                transactionRepository.save(sourceTransaction);
                transactionRepository.save(targetTransaction);

                return true;
            }
        }
        return false;
    }

    public boolean makeDebit(DebitInput debitInput) {
        String sourceAccountNumber = debitInput.getAccountNumber();
        Optional<Account> sourceAccount = accountRepository
                .findByAccountNumber(sourceAccountNumber);

        if (sourceAccount.isPresent()) {
            if (isAmountAvailable(debitInput.getAmount(), sourceAccount.get().getBalance())) {
                Transaction transaction = Transaction.builder()
                        .account(sourceAccount.get())
                        .amount(debitInput.getAmount())
                        .date(LocalDateTime.now())
                        .type(Type.WITHDRAW)
                        .action(Action.DEBIT)
                        .build();

                updateAccountBalance(sourceAccount.get(), transaction.getAmount(), Action.DEBIT);

                transaction.setBalance(sourceAccount.get().getBalance());
                transactionRepository.save(transaction);

                return true;
            }
        }
        return false;
    }

    public boolean makeCredit(CreditInput creditInput) {
        String targetAccountNumber = creditInput.getTargetAccountNumber();
        Optional<Account> targetAccount = accountRepository
                .findByAccountNumber(targetAccountNumber);

        if (targetAccount.isPresent()) {
            Transaction transaction = Transaction.builder()
                    .account(targetAccount.get())
                    .amount(creditInput.getAmount())
                    .date(LocalDateTime.now())
                    .type(Type.DEPOSIT)
                    .action(Action.CREDIT)
                    .build();

            updateAccountBalance(targetAccount.get(), transaction.getAmount(), Action.CREDIT);

            transaction.setBalance(targetAccount.get().getBalance());
            transactionRepository.save(transaction);

            return true;
        }
        return false;
    }
}
