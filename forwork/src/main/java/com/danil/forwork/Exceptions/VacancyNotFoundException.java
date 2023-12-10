package com.danil.forwork.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VacancyNotFoundException extends RuntimeException{
    String message;

    public VacancyNotFoundException(String message) {
        super(message);
    }

}
