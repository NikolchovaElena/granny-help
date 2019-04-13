package com.example.granny.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Cause not found")
public class CauseNotFoundException extends RuntimeException{

    public CauseNotFoundException() {
    }

    public CauseNotFoundException(String message) {
        super(message);
    }
}
