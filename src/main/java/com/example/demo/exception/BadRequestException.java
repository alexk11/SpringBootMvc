package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private final Long resourceId;
    private final String operation;

    public BadRequestException(Long resourceId, String message, String operation) {
        super(message);
        this.resourceId = resourceId;
        this.operation = operation;
    }

}
