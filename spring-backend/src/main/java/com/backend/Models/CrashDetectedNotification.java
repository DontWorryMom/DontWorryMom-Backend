package com.backend.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.backend.Deserializers.PostgreSQLEnumType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Type;

@Entity
@Table(name="crash_detected_notification")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@IdClass(CrashDetectedNotificationId.class)
@TypeDef(
    name = "pgsql_enum",
    typeClass = PostgreSQLEnumType.class
)
public class CrashDetectedNotification {
	@Id
	@Column(name="location_id")
	Long locationId;
	
	@Id
	@Column(name="notification_id")
	Long notificationId;

	@Enumerated(EnumType.STRING)
	@Column(name="notification_status")
	@Type(type="pgsql_enum")
	NotificationStatus notificationStatus;
}
