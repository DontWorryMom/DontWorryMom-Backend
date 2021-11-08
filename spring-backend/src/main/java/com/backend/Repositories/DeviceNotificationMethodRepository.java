package com.backend.Repositories;

import com.backend.Models.DeviceNotificationMethods;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

public interface DeviceNotificationMethodRepository extends JpaRepository<DeviceNotificationMethods, DeviceNotificationMethods.DeviceNotificationMethodsId> {
	
	@Modifying
	@Query(value = "delete from device_notification_methods dnm where dnm.device_id = ?1 and dnm.notification_id = ?2", nativeQuery = true)
    public void deleteByDeviceIdAndNotificationId(long deviceId, long notificationId);
}

