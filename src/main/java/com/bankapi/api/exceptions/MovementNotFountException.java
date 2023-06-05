package com.bankapi.api.exceptions;

import java.io.Serial;

public class MovementNotFountException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4L;

    public MovementNotFountException(String message) {
        super(message);

    }
}
