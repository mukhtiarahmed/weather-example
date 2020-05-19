package com.hackerrank.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemperatureDTO implements Comparable<TemperatureDTO> {


    private Long id;
    private String city;
    private String state;
    private Float lat;
    private Float lon;

    private Float lowest;
    private Float highest;
    private String message;

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

    public Float getLowest() {
        return lowest;
    }

    public void setLowest(Float lowest) {
        this.lowest = lowest;
    }

    public Float getHighest() {
        return highest;
    }

    public void setHighest(Float highest) {
        this.highest = highest;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int compareTo(TemperatureDTO o) {
        return city.compareTo(o.city);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemperatureDTO that = (TemperatureDTO) o;
        return Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {

        return Objects.hash(city);
    }
}
