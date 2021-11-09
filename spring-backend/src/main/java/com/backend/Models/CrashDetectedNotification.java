package com.backend.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="crash_detected_notification")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@IdClass(CrashDetectedNotificationId.class)
public class CrashDetectedNotification {
	@Id
	@Column(name="location_id")
	Long locationId;
	
	@Id
	@Column(name="notification_id")
	Long notificationId;

	@Enumerated(EnumType.STRING)
	@Column(name="notification_status")
	NotificationStatus notificationStatus;
}
