package com.hackerrank.weather.exception;

public class WeatherException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public WeatherException(String message) {
        super(message);
    }


    public WeatherException(String message, Throwable cause) {
        super(message, cause);
    }
}
