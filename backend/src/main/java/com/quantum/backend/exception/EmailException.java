package com.quantum.backend.exception;

// unchecked
public class EmailException extends RuntimeException{
    public EmailException(Exception exception){
        super(String.format("Unable to send email due to %s", exception.getMessage()));
    }
}
