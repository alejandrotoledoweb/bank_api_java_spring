package com.bankapi.api.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MovementDto {
    private int id;
    private Date date;
    private String movementType;
    private String value;
    private int balance;
}
