package com.applications.exception;

public class ShortURLAlreadyExistException extends RuntimeException{

    public ShortURLAlreadyExistException(String message) {
        super(message);
    }
}
