package com.example.granny.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Expired link")
public class LinkHasExpired extends RuntimeException{
    private int statusCode;

    public LinkHasExpired() {
        this.statusCode = 401;
    }

    public LinkHasExpired(String message) {
        super(message);
        this.statusCode = 401;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
