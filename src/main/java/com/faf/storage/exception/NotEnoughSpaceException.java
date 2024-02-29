package com.faf.storage.exception;

import java.io.Serial;

public class NotEnoughSpaceException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public NotEnoughSpaceException() {
        super("Not enough space on your account!");
    }
}
