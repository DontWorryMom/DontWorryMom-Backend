package com.backend.Repositories;

import com.backend.Models.DeviceNotificationMethods;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceNotificationMethodRepository extends JpaRepository<DeviceNotificationMethods, DeviceNotificationMethods.DeviceNotificationMethodsId> {
	public void deleteByDeviceIdAndNotificationId(long deviceId, long notificationId);
}

