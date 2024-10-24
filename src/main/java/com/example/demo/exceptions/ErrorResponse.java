package com.example.demo.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ErrorResponse {
    private int statusCode;
    private String details;
    private Date timestamp;

    // Constructor
    public ErrorResponse(int statusCode, String details) {
        this.statusCode = statusCode;
        this.details = details;
        this.timestamp = new Date(); // Automatically sets the current date and time
    }
}
