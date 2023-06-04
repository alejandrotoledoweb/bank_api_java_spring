package com.bankapi.api.exceptions;

public class ClientNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2L;

    public ClientNotFoundException(String message) {
        super(message);

    }
}
