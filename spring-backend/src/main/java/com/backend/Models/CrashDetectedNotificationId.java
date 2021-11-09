package com.backend.Models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CrashDetectedNotificationId implements Serializable {
	long locationId;
	long notificationId;
}