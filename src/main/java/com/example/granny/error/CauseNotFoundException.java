package com.example.granny.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Cause not found.")
public class CauseNotFoundException extends RuntimeException{
    private int statusCode;

    public CauseNotFoundException() {
        this.statusCode = 404;
    }

    public CauseNotFoundException(String message) {
        super(message);
        this.statusCode = 404;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
