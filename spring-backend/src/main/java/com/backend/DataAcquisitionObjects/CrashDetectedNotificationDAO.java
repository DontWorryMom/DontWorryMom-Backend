package com.backend.DataAcquisitionObjects;

import java.util.List;
import java.util.Optional;

import com.backend.Models.CrashDetectedNotification;
import com.backend.Models.CrashDetectedNotificationId;
import com.backend.Models.NotificationStatus;
import com.backend.Repositories.EntityDataRepositories.CrashDetectedNotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CrashDetectedNotificationDAO {

	CrashDetectedNotificationRepository cdnRepo;

	@Autowired
	public CrashDetectedNotificationDAO(CrashDetectedNotificationRepository cdnRepo) {
		this.cdnRepo = cdnRepo;
	}

	/*
	 *	READ DATA
	 */

	public List<CrashDetectedNotification> getCrashDetectedNotificationList() {
		return cdnRepo.findAll();
	}

	public List<CrashDetectedNotification> getCrashDetectedNotificationListByLocationId(long locationId) {
		return cdnRepo.findAllByLocationId(locationId);
	}

	/*
	 *	CREATE DATA
	 */

	public CrashDetectedNotification createCrashDetectedNotification(long locationId, long notificationId) {
		CrashDetectedNotification cdn = new CrashDetectedNotification(locationId, notificationId, NotificationStatus.PENDING);
		return cdnRepo.save(cdn);
	}

	/*
	 *	UPDATE DATA
	 */

	public CrashDetectedNotification updateCrashDetectedNotificationStatus(long locationId, long notificationId, NotificationStatus status) {
		Optional<CrashDetectedNotification> cdnOptional = cdnRepo.findById(new CrashDetectedNotificationId(locationId, notificationId));
		if(cdnOptional.isPresent()) {
			CrashDetectedNotification cdn = cdnOptional.get();
			cdn.setNotificationStatus(status);
			return cdnRepo.save(cdn);
		} else {
			throw new IllegalArgumentException("Could not find CrashDetectedNotification with locationId "+locationId+" and notificationId "+notificationId);
		}
	}

}
