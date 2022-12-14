package com.example.backendhome.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    ENTITY_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY),
    USER_IS_ALREADY_EXIST(HttpStatus.CONFLICT),
    TOKEN_REFRESH_EXCEPTION(HttpStatus.FORBIDDEN);

    private final HttpStatus status;
}
