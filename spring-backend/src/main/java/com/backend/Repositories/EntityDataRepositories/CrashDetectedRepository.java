package com.backend.Repositories.EntityDataRepositories;

import java.util.List;

import com.backend.Models.CrashDetected;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CrashDetectedRepository extends JpaRepository<CrashDetected, Long> {
    public List<CrashDetected> findByDeviceId(long deviceId);
}
