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
@Table(name="email_notification")
@PrimaryKeyJoinColumn(name="notification_id")
public class EmailNotification extends Notification {

    @Column(name="email")
    String email;

    public static final NotificationType NOTIFICATION_TYPE = NotificationType.EMAIL_NOTIFICATION;

    @JsonGetter
    @Override
    public NotificationType getType() {
        return NOTIFICATION_TYPE;
    }

}