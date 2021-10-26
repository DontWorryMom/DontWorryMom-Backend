package com.backend.Models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.Data;

@Entity
@Data
@Table(name="text_notification")
@PrimaryKeyJoinColumn(name="notification_id")
public class TextNotification extends Notification {

    @Column(name="phone_number")
    String phoneNumber;

    public static final NotificationType NOTIFICATION_TYPE = NotificationType.TEXT_NOTIFICATION;

    @JsonGetter
    @Override
    public NotificationType getType() {
        return NOTIFICATION_TYPE;
    }

}