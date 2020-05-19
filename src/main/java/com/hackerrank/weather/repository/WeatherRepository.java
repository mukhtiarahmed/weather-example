package com.hackerrank.weather.repository;

import com.hackerrank.weather.model.Location;
import com.hackerrank.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {


    boolean existsById(Long id);

    List<Weather> findByLocation(Location location);

    @Query(" SELECT  w.location , min(t) as lowest,  max(t) as highest FROM Weather w JOIN w.temperature t" +
            " WHERE w.dateRecorded between  :start AND :end GROUP BY   w.location order by  w.location.cityName ")
    List<Object[]> findByDateRecordedBetween(@Param("start") Date start, @Param("end") Date end);

    long deleteByLocationAndDateRecordedBetween(Location location, Date start, Date end);


}



