package com.irfan.atm.controllers.api;

import com.irfan.atm.models.dto.AccountInput;
import com.irfan.atm.models.dto.CreateAccountInput;
import com.irfan.atm.models.entities.Account;
import com.irfan.atm.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class AccountAPIController {

    private final AccountService accountService;


    @PostMapping(value = "accounts/balance")
    public ResponseEntity<?> checkAccountBalance(
            @Valid @RequestBody AccountInput accountInput) {
        if (!accountInput.getAccountNumber().isBlank()) {
            Account account = accountService.getAccount(accountInput.getAccountNumber());

            if (account == null) {
                return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(account.convertToResponse(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Invalid input data", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "accounts/transactions")
    public ResponseEntity<?> checkTransactionHistory(
            @Valid @RequestBody AccountInput accountInput) {
        if (!accountInput.getAccountNumber().isBlank()) {
            Account account = accountService.getAccount(accountInput.getAccountNumber());

            if (account == null) {
                return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(account.convertToTransactionResponse(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Invalid input data", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/accounts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAccount(
            @Valid @RequestBody CreateAccountInput createAccountInput) {

        if (!createAccountInput.getOwnerName().isBlank()) {
            Account account = accountService.createAccount(createAccountInput.getOwnerName());

            if (account == null) {
                return new ResponseEntity<>("Creating new account failed", HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(account, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Invalid input data", HttpStatus.BAD_REQUEST);
        }
    }
}
