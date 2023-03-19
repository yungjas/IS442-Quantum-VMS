package com.quantum.backend.exception;

// unchecked //when trying to find sth based on ID but cannot find can throw this exception
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String className, String attributeName, String attributeValue){
        super(String.format("%s not found with %s %s", className, attributeName, attributeValue));
    }
}
