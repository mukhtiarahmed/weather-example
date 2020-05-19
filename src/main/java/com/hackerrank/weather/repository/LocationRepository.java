package com.hackerrank.weather.repository;

import com.hackerrank.weather.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {


    Location findByLatitudeAndLongitude(Float latitude, Float longitude);

    @Query(" SELECT loc FROM Location loc WHERE EXISTS " +
            "  (SELECT w from Weather w WHERE w.location = loc) and loc.id not in ( :ids ) ")
    List<Location> findByIdNotIn(@Param("ids") List<Long> ids);


}
