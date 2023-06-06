package com.bankapi.api.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MovementDto {
    private int id;
    private Date date;
    private String movementType;
    private Long value;
    private Long balance;
    private Long initialBalance;
}
