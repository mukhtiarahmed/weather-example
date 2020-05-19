package com.hackerrank.weather.service;

import com.hackerrank.weather.dto.LocationDTO;
import com.hackerrank.weather.dto.TemperatureDTO;
import com.hackerrank.weather.dto.WeatherDTO;
import com.hackerrank.weather.exception.EntityNotFoundException;
import com.hackerrank.weather.model.Location;
import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.repository.LocationRepository;
import com.hackerrank.weather.repository.WeatherRepository;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    private Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private WeatherRepository weatherRepository;

    private DozerBeanMapper mappper = new DozerBeanMapper();

    @PostConstruct
    private void init() {
        mappper.setMappingFiles(Arrays.asList("dozer-mapper.xml"));
    }

    @Transactional
    public void createWeather(WeatherDTO weatherDTO) {
        logger.info("enter into createWeather ");
        Weather weather = mappper.map(weatherDTO, Weather.class);
        weather.setTemperature(weatherDTO.getTemperature());
        LocationDTO locationDTO = weatherDTO.getLocation();
        Location location = locationRepository.findByLatitudeAndLongitude(locationDTO.getLat(), locationDTO.getLon());
        if (location == null) {
            location = locationRepository.save(weather.getLocation());
            weather.setLocation(location);
        } else {
            weather.setLocation(location);
        }

        weather = weatherRepository.save(weather);
        logger.info("add weather successfully {} ", weather.getId());


    }

    @Transactional(readOnly = true)
    public List<WeatherDTO> getWeathers() {
        List<Weather> list = weatherRepository.findAll();
        return list.stream().map(w -> mappper.map(w, WeatherDTO.class)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<WeatherDTO> getWeatherByLocation(Float latitude, Float longitude) {
        Location location = locationRepository.findByLatitudeAndLongitude(latitude, longitude);
        if (location == null) {
            throw new EntityNotFoundException(
                    String.format("Location not found by latitude %f  :  longitude : %f ", latitude, longitude));
        }
        List<Weather> list = weatherRepository.findByLocation(location);
        return list.stream().map(w -> mappper.map(w, WeatherDTO.class)).collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public List<TemperatureDTO> getWeatherByDateBetween(Date start, Date end) {


        List<Object[]> list = weatherRepository.findByDateRecordedBetween(start, end);
        List<TemperatureDTO> temperatures = new ArrayList<>();

        for (Object[] temperature : list) {
            TemperatureDTO dto = mappper.map(temperature[0], TemperatureDTO.class);
            dto.setLowest((Float) temperature[1]);
            dto.setHighest((Float) temperature[2]);
            temperatures.add(dto);
        }

        long locationCount = locationRepository.count();
        if (temperatures.size() < locationCount) {
            List<Long> locationIds = temperatures.stream().map(TemperatureDTO::getId).collect(Collectors.toList());
            List<Location> locations = locationRepository.findByIdNotIn(locationIds);
            for (Location location : locations) {
                TemperatureDTO dto = mappper.map(location, TemperatureDTO.class);
                dto.setMessage("There is no weather data in the given date range");
                temperatures.add(dto);
            }
        }

        Collections.sort(temperatures);
        return temperatures;

    }

    @Transactional
    public void deleteWeather() {
        weatherRepository.deleteAll();
        logger.info("delete weather successfully ");
    }

    @Transactional
    public void deleteWeatherByLocationAndBetweenDate(Float latitude, Float longitude, Date start, Date end) {
        Location location = locationRepository.findByLatitudeAndLongitude(latitude, longitude);
        if (location == null) {
            throw new EntityNotFoundException(
                    String.format("Location not found by latitude %f  :  longitude : %f ", latitude, longitude));
        }
        long delete = weatherRepository.deleteByLocationAndDateRecordedBetween(location, start, end);
        logger.info("delete weather successfully count {} ", delete);

    }

    @Transactional(readOnly = true)
    public boolean existsWeatherById(Long id) {
        return weatherRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public WeatherDTO getWeatherById(Long id) {
        Weather weather = weatherRepository.findOne(id);
        if (weather == null) {
            throw new EntityNotFoundException("Weather not found by id : " + id);
        } else {
            return mappper.map(weather, WeatherDTO.class);
        }
    }

}


