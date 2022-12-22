package com.irfan.atm.models.entities;

import lombok.Getter;

@Getter
public enum Type {
    WITHDRAW("Tarik Tunai"),
    DEPOSIT("Setor Tunai"),
    TRANSFER("Transfer");

    final String description;

    Type (String description) {
        this.description = description;
    }
}
