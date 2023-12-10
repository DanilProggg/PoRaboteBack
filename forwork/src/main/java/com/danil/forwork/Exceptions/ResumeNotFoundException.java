package com.danil.forwork.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResumeNotFoundException extends RuntimeException{
    String message;

    public ResumeNotFoundException(String message) {
        super(message);
    }
}
