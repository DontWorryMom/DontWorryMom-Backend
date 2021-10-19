package com.backend.Repositories;

import java.util.List;

import com.backend.Models.Device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
	
    @Query(value = "select * from Device where user_id = ?1", nativeQuery = true)
    public List<Device> findByUserId(int userId);
}

