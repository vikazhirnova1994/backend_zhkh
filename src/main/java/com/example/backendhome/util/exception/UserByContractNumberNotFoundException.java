package com.example.backendhome.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserByContractNumberNotFoundException extends RuntimeException {
    public UserByContractNumberNotFoundException(String message){
        super(message);
    }
}
