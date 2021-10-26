package com.backend.Repositories;

import java.util.List;

import com.backend.Models.Location;
import com.backend.Models.LocationId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<Location, LocationId> {
	
    @Query(value = "select * from past_locations where device_id = ?1", nativeQuery = true)
    public List<Location> findByDeviceId(int deviceId);
}