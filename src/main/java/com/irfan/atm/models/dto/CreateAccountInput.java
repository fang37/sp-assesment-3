package com.irfan.atm.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class CreateAccountInput {

    @JsonProperty("owner_name")
    @NotBlank(message = "Owner name is mandatory")
    private String ownerName;
}
