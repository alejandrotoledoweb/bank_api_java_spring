package com.bankapi.api.dto;

import lombok.Data;

@Data
public class AccountDto {
    private int id;
    private int accountNumber;
    private String accountType;
    private int initialBalance;
    private boolean state;
}
