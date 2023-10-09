package com.project.fullstack.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UserServiceException extends RuntimeException {

    private final HttpStatus httpStatus;
    public UserServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
