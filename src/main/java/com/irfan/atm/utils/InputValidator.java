package com.irfan.atm.utils;

import com.irfan.atm.models.dto.TransferInput;

public class InputValidator {

    public static boolean isTransactionValid(TransferInput transactionInput) {

        if (transactionInput.getSourceAccountNumber().equals(transactionInput.getTargetAccountNumber())) {
            return false;
        } else {
            return true;
        }
    }
}
