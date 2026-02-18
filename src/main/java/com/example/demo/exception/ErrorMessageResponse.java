package com.example.demo.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;


@Getter
@Setter
public class ErrorMessageResponse {

    private final String error;
    private final Instant timestamp;
    private final String operation;
    private Map<String, String> details; // Дополнительные детали

    public ErrorMessageResponse(String error, String operation) {
        this.error = error;
        this.timestamp = Instant.now();
        this.operation = operation;
    }
}
