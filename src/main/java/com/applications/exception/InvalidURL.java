package com.applications.exception;

public class InvalidURL extends RuntimeException{

    public InvalidURL(String message) {
        super(message);
    }
}
