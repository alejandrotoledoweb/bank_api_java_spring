package com.bankapi.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClientResponse {
    private List<ClientDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
