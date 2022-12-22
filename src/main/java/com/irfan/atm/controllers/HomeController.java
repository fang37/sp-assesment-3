package com.irfan.atm.controllers;

import com.irfan.atm.models.dto.*;
import com.irfan.atm.models.entities.Account;
import com.irfan.atm.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AccountRepository accountRepository;

    @GetMapping("/accounts")
    public String home(Model model) {
        List<AccountResponse> accounts = accountRepository.findAll().stream().map(Account::convertToResponse).collect(Collectors.toList());
        model.addAttribute("accounts", accounts);
        return "accounts";
    }

    @GetMapping("/account/{id}/balance")
    public String balance(@PathVariable("id") Long id, Model model) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid account Id:" + id));;

        model.addAttribute("account", account.convertToResponse());
        return "account-balance";
    }

    @GetMapping("/account/{id}/history")
    public String history(@PathVariable("id") Long id, Model model) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid account Id:" + id));;

        model.addAttribute("account", account.convertToTransactionResponse());
        return "account-history";
    }

    @GetMapping("/account/{id}/transaction")
    public String transaction(@PathVariable("id") Long id, Model model) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid account Id:" + id));;
        CreditInput creditInput = CreditInput.builder().targetAccountNumber(account.getAccountNumber()).build();
        DebitInput debitInput = DebitInput.builder().accountNumber(account.getAccountNumber()).build();
        TransferInput transferInput = TransferInput.builder()
                .sourceAccountNumber(account.getAccountNumber())
                        .build();

        model.addAttribute("account", account.convertToResponse());
        model.addAttribute("credit", creditInput);
        model.addAttribute("debit", debitInput);
        model.addAttribute("transfer", transferInput);
        return "account-transaction";
    }

    @GetMapping("/account/create")
    public String addAccount(Model model) {
        CreateAccountInput createAccountInput = new CreateAccountInput();
        model.addAttribute("account", createAccountInput);
        return "create-account";
    }
}
