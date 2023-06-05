package com.bankapi.api.dto;

import lombok.Data;

@Data
public class AccountDto {
    private int id;
    private Long accountNumber;
    private String accountType;
    private Long initialBalance;
    private Boolean state;
}
