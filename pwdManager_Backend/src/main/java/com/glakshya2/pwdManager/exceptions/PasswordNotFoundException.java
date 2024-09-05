package com.glakshya2.pwdManager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class PasswordNotFoundException extends RuntimeException{

    public PasswordNotFoundException(String message) {
        super(message);
    }
}
