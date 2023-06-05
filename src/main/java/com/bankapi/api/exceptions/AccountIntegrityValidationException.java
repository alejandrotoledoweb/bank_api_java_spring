package com.bankapi.api.exceptions;

import java.io.Serial;

public class AccountIntegrityValidationException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 6L;

    public AccountIntegrityValidationException(String message) {
        super(message);

    }
}
