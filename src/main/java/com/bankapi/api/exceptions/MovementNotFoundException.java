package com.bankapi.api.exceptions;

import java.io.Serial;

public class MovementNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4L;

    public MovementNotFoundException(String message) {
        super(message);

    }
}
