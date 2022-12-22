package com.irfan.atm.controllers;

import com.irfan.atm.models.dto.AccountInput;
import com.irfan.atm.models.dto.CreateAccountInput;
import com.irfan.atm.models.entities.Account;
import com.irfan.atm.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AccountController {

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

    @PostMapping(value = "/accounts")
    public String createAccount(
            @Valid @ModelAttribute CreateAccountInput createAccountInput, RedirectAttributes redirectAttrs) {
        Account account = accountService.createAccount(createAccountInput.getOwnerName());

        if (account == null) {
            redirectAttrs.addFlashAttribute("message", "Create Account Failed");
        } else {
            redirectAttrs.addFlashAttribute("message", "Create Account Success");
            return "redirect:/accounts";
        }
        return "redirect:/accounts";
    }
}
