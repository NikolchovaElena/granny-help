package com.example.granny.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Invalid Link")
public class InvalidLink extends RuntimeException{
    private int statusCode;

    public InvalidLink() {
        this.statusCode = 400;
    }

    public InvalidLink(String message) {
        super(message);
        this.statusCode = 400;
    }

    public int getStatusCode() {
        return statusCode;
    }
}