package com.bankapi.api.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class MovementDto {
    private int id;
    private LocalDateTime date;
    private String movementType;
    private Long value;
    private Long balance;
    private Long initialBalance;
    private Long accountNumber;
    private String accountType;
    private Boolean status;
}
