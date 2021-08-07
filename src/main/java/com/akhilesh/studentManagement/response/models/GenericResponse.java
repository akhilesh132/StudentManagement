package com.akhilesh.studentManagement.response.models;

import java.time.LocalDateTime;

public class GenericResponse {
    private final String message;
    private final LocalDateTime timestamp;

    public GenericResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
