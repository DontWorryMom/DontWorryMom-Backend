package com.backend.DataAcquisitionObjects;

import java.util.List;

import com.backend.Models.CrashDetectedNotification;
import com.backend.Repositories.CrashDetectedNotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CrashDetectedNotificationDAO {

	CrashDetectedNotificationRepository cdnRepo;

	@Autowired
	public CrashDetectedNotificationDAO(CrashDetectedNotificationRepository cdnRepo) {
		this.cdnRepo = cdnRepo;
	}

	public List<CrashDetectedNotification> getCrashDetectedNotificationList() {
		return cdnRepo.findAll();
	}
}
