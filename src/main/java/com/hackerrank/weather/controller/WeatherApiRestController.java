package com.hackerrank.weather.controller;

import com.hackerrank.weather.dto.TemperatureDTO;
import com.hackerrank.weather.dto.WeatherDTO;
import com.hackerrank.weather.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
public class WeatherApiRestController {


    private Logger logger = LoggerFactory.getLogger(WeatherApiRestController.class);

    @Autowired
    private WeatherService weatherService;

    @PostMapping(value = "/weather")
    public ResponseEntity create(@RequestBody @Valid WeatherDTO weatherDTO) {

        if (weatherService.existsWeatherById(weatherDTO.getId())) {
            logger.info("weather already exits by id {} ", weatherDTO.getId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Weather already exists by id : " + weatherDTO.getId());
        } else {
            weatherService.createWeather(weatherDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Weather Created");
        }
    }

    @GetMapping(value = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getWeathers(@RequestParam(value = "lat", required = false) Float latitude,
                                      @RequestParam(value = "lon", required = false) Float longitude) {

        List<WeatherDTO> list;
        if (latitude != null && longitude != null) {
            list = weatherService.getWeatherByLocation(latitude, longitude);
        } else {
            list = weatherService.getWeathers();
        }

        return ResponseEntity.ok(list);
    }

    @DeleteMapping(value = "/erase", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteWeatherByLocation(@RequestParam(value = "start", required = false)
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                                  @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
                                                  @RequestParam(value = "lat", required = false) Float latitude,
                                                  @RequestParam(value = "lon", required = false) Float longitude) {

        if (latitude != null && longitude != null && start != null && end != null) {
            weatherService.deleteWeatherByLocationAndBetweenDate(latitude, longitude, start, end);
        } else {
            weatherService.deleteWeather();
        }
        return ResponseEntity.ok("Delete Weather resource successfully");
    }


    @GetMapping(value = "/weather/temperature", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getWeatherByDateBetween(@RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                                  @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {

        List<TemperatureDTO> list = weatherService.getWeatherByDateBetween(start, end);
        return ResponseEntity.ok(list);
    }


}
