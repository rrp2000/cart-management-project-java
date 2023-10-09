package com.project.fullstack.exception.controller;

import com.project.fullstack.exception.UserServiceException;
import com.project.fullstack.exception.apiError.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserServiceException.class)
    protected ResponseEntity<Object> handleUserServiceException(UserServiceException ex){
        ApiError apiError;
        if(ex.getHttpStatus() != null) apiError = new ApiError(ex.getHttpStatus());
        else apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setMessage(ex.getMessage());
        apiError.setSuccess(false);
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
