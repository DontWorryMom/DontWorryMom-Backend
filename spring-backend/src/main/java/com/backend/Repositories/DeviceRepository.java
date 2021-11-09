package com.backend.Repositories;

import java.util.List;

import com.backend.Models.Device;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    public List<Device> findByUserId(long userId);
}

