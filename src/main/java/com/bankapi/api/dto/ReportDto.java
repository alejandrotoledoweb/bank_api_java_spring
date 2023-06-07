package com.bankapi.api.dto;

import lombok.Data;

@Data
public class ReportDto {
    private String date;
    private String client;
    private Long accountNumber;
    private String accountType;
    private Long initialBalance;
    private Boolean state;
    private Long value;
    private Long balance;

}
