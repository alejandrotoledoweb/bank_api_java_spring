package com.bankapi.api.exceptions;

import java.io.Serial;

public class ClientNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 2L;

    public ClientNotFoundException(String message) {
        super(message);

    }
}
