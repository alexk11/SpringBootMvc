package com.example.demo.exception;

import lombok.Getter;
import java.time.Instant;


@Getter
public class ErrorMessageResponse {

    private final String error;
    private final Instant timestamp;
    private final String operation;

    public ErrorMessageResponse(String error, String operation) {
        this.error = error;
        this.timestamp = Instant.now();
        this.operation = operation;
    }

}
