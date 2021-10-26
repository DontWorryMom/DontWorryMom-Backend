package com.backend.Models;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.backend.Deserializers.NotificationDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@Entity
@Data
@Table(name="notification")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonDeserialize(using=NotificationDeserializer.class)
public abstract class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notification_id")
    long notificationId;

    @Column(name="user_id")
    long userId;

    public abstract NotificationType getType();
    
    public static enum NotificationType {
        EMAIL_NOTIFICATION,
        TEXT_NOTIFICATION
    }
}
