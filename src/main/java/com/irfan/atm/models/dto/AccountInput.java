package com.irfan.atm.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class AccountInput {

    @JsonProperty("account_number")
    @NotNull(message = "Account number is mandatory")
    private String accountNumber;
}
