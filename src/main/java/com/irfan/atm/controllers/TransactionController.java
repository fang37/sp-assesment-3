package com.irfan.atm.controllers;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class TransactionController {

    private final AccountService accountService;

    private final TransactionService transactionService;

    @PostMapping(value = "/transfer")
    public String transfer(@Valid @ModelAttribute TransferInput transferInput, RedirectAttributes redirectAttrs) {
        if (InputValidator.isTransactionValid(transferInput)) {
            boolean isComplete = transactionService.makeTransfer(transferInput);
            if (isComplete) {
                redirectAttrs.addFlashAttribute("message", "Transfer Success");
                return "redirect:/accounts";
            } else {
                redirectAttrs.addFlashAttribute("message", "Transfer Failed");
            }
        }
        return "redirect:/accounts";

    }

    @PostMapping(value = "/debit")
    public String debit(@Valid @ModelAttribute DebitInput debitInput, RedirectAttributes redirectAttrs) {
        Account account = accountService.getAccount(debitInput.getAccountNumber());

        if (account == null) {
            return "redirect:/accounts";
        } else {
            if (transactionService.isAmountAvailable(debitInput.getAmount(), account.getBalance())) {
                boolean isComplete = transactionService.makeDebit(debitInput);
                if (isComplete) {
                    redirectAttrs.addFlashAttribute("message", "Debit Success");
                    return "redirect:/accounts";
                } else {
                    redirectAttrs.addFlashAttribute("message", "Debit Failed");
                }
            } else {
                redirectAttrs.addFlashAttribute("message", "Debit Failed : Not enough balance available");
            }
        }
        return "redirect:/accounts";
    }

    @PostMapping(value = "/credit")
    public String credit(@Valid @ModelAttribute CreditInput creditInput, RedirectAttributes redirectAttrs) {
        Account account = accountService.getAccount(creditInput.getTargetAccountNumber());

        if (account != null) {
            boolean isComplete = transactionService.makeCredit(creditInput);
            if (isComplete) {
                redirectAttrs.addFlashAttribute("message", "Credit Success");
                return "redirect:/accounts";
            } else {
                redirectAttrs.addFlashAttribute("message", "Credit Failed");
            }
        }
        return "redirect:/accounts";
    }
}
