package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private final Long resourceId;
    private final String operation;

    public ResourceNotFoundException(Long resourceId, String message, String operation) {
        super(message);
        this.resourceId = resourceId;
        this.operation = operation;
    }

}
