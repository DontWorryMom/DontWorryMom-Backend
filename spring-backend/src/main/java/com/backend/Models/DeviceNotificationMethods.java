package com.backend.Models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="device_notification_methods")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@IdClass(DeviceNotificationMethods.class)
public class DeviceNotificationMethods implements Serializable {
    @Id
    @Column(name="device_id")
    long deviceId;

    @Id
    @Column(name="notification_id")
    long notificationId;

    @AllArgsConstructor
    public static class DeviceNotificationMethodsId implements Serializable {
        
        long deviceId;
        long notificationId;
    }
}
