package com.irfan.atm.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.irfan.atm.models.entities.Action;
import com.irfan.atm.models.entities.Type;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TransferInput {

    @JsonProperty(value = "source_account")
    @NotNull
    private String sourceAccountNumber;

    @JsonProperty(value = "target_account_number")
    @NotNull
    private String targetAccountNumber;

    @Positive(message = "Transfer amount must be postive")
    @Min(value = 1, message = "Amount must be larger than 1")
    private double amount;
}
