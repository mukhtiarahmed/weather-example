package com.hackerrank.weather.dto;

import javax.validation.constraints.NotNull;

public class LocationDTO {

    @NotNull
    private String city;
    @NotNull
    private String state;
    @NotNull
    private Float lat;
    @NotNull
    private Float lon;


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }
}
