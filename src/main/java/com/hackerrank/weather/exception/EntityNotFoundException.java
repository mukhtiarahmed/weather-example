package com.hackerrank.weather.exception;

public class EntityNotFoundException extends WeatherException {

    private static final long serialVersionUID = 1L;


    public EntityNotFoundException(String message) {
        super(message);
    }


    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
