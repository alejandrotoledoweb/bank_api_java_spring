package com.bankapi.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class MovementResponse {
    private List<MovementDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
