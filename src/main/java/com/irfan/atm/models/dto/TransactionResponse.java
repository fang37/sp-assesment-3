package com.irfan.atm.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.irfan.atm.models.entities.Account;
import com.irfan.atm.models.entities.Action;
import com.irfan.atm.models.entities.Type;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private double amount;

    private double balance;

    @Enumerated(EnumType.STRING)
    private Action action;

    @Enumerated(EnumType.STRING)
    private Type type;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime date;
}
