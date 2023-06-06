package com.bankapi.api.exceptions;

import java.io.Serial;

public class MovementBadRequestException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4L;

    public MovementBadRequestException(String message) {
        super(message);

    }
}
