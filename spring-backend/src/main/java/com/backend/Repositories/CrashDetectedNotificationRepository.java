package com.backend.Repositories;

import com.backend.Models.CrashDetectedNotification;
import com.backend.Models.CrashDetectedNotificationId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CrashDetectedNotificationRepository extends JpaRepository<CrashDetectedNotification, CrashDetectedNotificationId> {
	
}
