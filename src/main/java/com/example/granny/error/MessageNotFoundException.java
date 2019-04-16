package com.example.granny.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Message not found.")
public class MessageNotFoundException extends RuntimeException{
    private int statusCode;

    public MessageNotFoundException() {
        this.statusCode = 404;
    }

    public MessageNotFoundException(String message) {
        super(message);
        this.statusCode = 404;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
