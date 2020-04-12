package com.applications.exception;

public class ShortURLNotFoundException extends RuntimeException{

    public ShortURLNotFoundException(String message) {
        super(message);
    }
}
