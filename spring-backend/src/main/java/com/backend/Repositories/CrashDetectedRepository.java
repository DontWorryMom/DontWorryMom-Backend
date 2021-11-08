package com.backend.Repositories;

import java.util.List;

import com.backend.Models.CrashDetected;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CrashDetectedRepository extends JpaRepository<CrashDetected, Long> {
	
    @Query(
        value = "select * from crash_detected cd inner join past_locations pl on cd.location_id = pl.location_id where pl.device_id = ?1", 
        nativeQuery = true
    )
    public List<CrashDetected> findByDeviceId(long deviceId);
}
