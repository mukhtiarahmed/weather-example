package com.hackerrank.weather.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "weather")
public class Weather {

    @Id
    private Long id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateRecorded;
    @ManyToOne
    private Location location;
    @ElementCollection
    private List<Float> temperature;

    public Weather() {
    }

    public Weather(Long id, Date dateRecorded, Location location, List<Float> temperature) {
        this.id = id;
        this.dateRecorded = dateRecorded;
        this.location = location;
        this.temperature = temperature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(Date dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Float> getTemperature() {
        return temperature;
    }

    public void setTemperature(List<Float> temperature) {
        this.temperature = temperature;
    }
}
