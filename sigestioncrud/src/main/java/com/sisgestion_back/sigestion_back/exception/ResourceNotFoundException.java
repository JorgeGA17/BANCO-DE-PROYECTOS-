package com.sisgestion_back.sigestion_back.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() { }
    public ResourceNotFoundException(String message) {
        super(message);
    }


}