package com.irfan.atm.models.entities;

import com.irfan.atm.models.dto.AccountInput;
import com.irfan.atm.models.dto.AccountResponse;
import com.irfan.atm.models.dto.AccountTransactionResponse;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    String accountNumber;

    double balance;

    String ownerName;

    @OneToMany(mappedBy = "account")
    List<Transaction> transactions;

    public AccountResponse convertToResponse() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, AccountResponse.class);
    }

    public AccountTransactionResponse convertToTransactionResponse() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, AccountTransactionResponse.class);
    }

    public AccountInput convertToInput() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, AccountInput.class);
    }

}
