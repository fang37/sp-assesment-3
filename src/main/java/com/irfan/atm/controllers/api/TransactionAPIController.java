package com.irfan.atm.controllers.api;

import com.irfan.atm.models.dto.CreditInput;
import com.irfan.atm.models.dto.DebitInput;
import com.irfan.atm.models.dto.TransferInput;
import com.irfan.atm.models.entities.Account;
import com.irfan.atm.services.AccountService;
import com.irfan.atm.services.TransactionService;
import com.irfan.atm.utils.InputValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class TransactionAPIController {

    private final AccountService accountService;

    private final TransactionService transactionService;

    @PostMapping(value = "/transfer",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> transfer(@Valid @ModelAttribute TransferInput transferInput) {

        if (InputValidator.isTransactionValid(transferInput)) {
            boolean isComplete = transactionService.makeTransfer(transferInput);
            if (isComplete) {
                return new ResponseEntity<>("Transfer Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Transfer Failed", HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Input transaction invalid", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/debit")
    public ResponseEntity<?> debit(@Valid @ModelAttribute DebitInput debitInput) {
        Account account = accountService.getAccount(debitInput.getAccountNumber());

        if (account == null) {
            return new ResponseEntity<>("Account not found", HttpStatus.OK);
        } else {
            if (transactionService.isAmountAvailable(debitInput.getAmount(), account.getBalance())) {
                boolean isComplete = transactionService.makeDebit(debitInput);
                if (isComplete) {
                    return new ResponseEntity<>("Debit Success", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Debit Failed", HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("Your account does not have enough balance", HttpStatus.OK);
        }
    }

    @PostMapping(value = "/credit")
    public ResponseEntity<?> credit(@Valid @ModelAttribute CreditInput creditInput) {
        Account account = accountService.getAccount(creditInput.getTargetAccountNumber());

        if (account == null) {
            return new ResponseEntity<>("Account not found", HttpStatus.OK);
        } else {
            transactionService.makeCredit(creditInput);
            return new ResponseEntity<>("Credit Success", HttpStatus.OK);
        }
    }
}
