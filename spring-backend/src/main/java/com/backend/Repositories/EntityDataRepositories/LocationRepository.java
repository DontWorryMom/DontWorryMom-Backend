package com.backend.Repositories.EntityDataRepositories;

import java.util.List;

import com.backend.Models.Location;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    public List<Location> findByDeviceId(long deviceId);
}
