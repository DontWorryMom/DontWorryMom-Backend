package com.backend.Repositories;

import java.util.List;

import com.backend.Models.CrashDetectedNotification;
import com.backend.Models.CrashDetectedNotificationId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CrashDetectedNotificationRepository extends JpaRepository<CrashDetectedNotification, CrashDetectedNotificationId> {
	public List<CrashDetectedNotification> findAllByLocationId(long locationId);
}
