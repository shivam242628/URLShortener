package com.applications.exception;

public class ShortURLAlreadyExist extends RuntimeException{

    public ShortURLAlreadyExist(String message) {
        super(message);
    }
}
