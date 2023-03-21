package com.quantum.backend.exception;

// unchecked
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String className, String attributeName, String attributeValue){
        super(String.format("%s not found with %s %s", className, attributeName, attributeValue));
    }
}
