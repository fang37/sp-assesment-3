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
public class DebitInput {

    @JsonProperty("account_number")
    @NotBlank
    String accountNumber;

    @Positive(message = "Transfer amount must be postive")
    private double amount;


}
