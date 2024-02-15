package com.faf.storage.exception;

import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

public class UserNotActivatedException extends AuthenticationException{

    @Serial
    private static final long serialVersionUID = 1L;

    public UserNotActivatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotActivatedException(String message) {
        super(message);
    }
}
