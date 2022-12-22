package com.irfan.atm.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class CreditInput {

    @JsonProperty(value = "target_account_number")
    @NotBlank(message = "Target account number is mandatory")
    private String targetAccountNumber;

    @Positive(message = "Transfer amount must be positive")
    private double amount;
}
