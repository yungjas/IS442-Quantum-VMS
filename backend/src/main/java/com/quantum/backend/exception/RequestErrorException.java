package com.quantum.backend.exception;

// unchecked
public class RequestErrorException extends RuntimeException{
    public RequestErrorException(String requestAction, String className, String error){
        super(String.format("Unable to %s %s due to %s", requestAction, className, error));
    }
}
