package com.applications.exception;

public class URLNotFoundException extends RuntimeException{

    public URLNotFoundException(String message) {
        super(message);
    }
}
