package com.project.fullstack.exception.apiError;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonTypeInfo(include = JsonTypeInfo.As.EXISTING_PROPERTY, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private String message;

    private List<String> validationMessage;

    private Boolean success;

    private Object payload;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }
    public ApiError(HttpStatus status) {
        this();
        this.status = status;
        this.message = String.valueOf("Something went wrong");
    }
}
