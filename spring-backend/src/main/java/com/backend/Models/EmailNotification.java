package com.backend.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
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

    public static EmailNotification parse(JsonNode node) throws JsonProcessingException {
        long notificationId = 0;            // default value to be overwritten
        if(node.has("notificationId")) {
            notificationId = node.get("notificationId").asLong();
        }
        long userId = 0;                    // default value to be overwritten
        if(node.has("userId")) {
            userId = node.get("userId").asLong();
        }
        String email = node.get("email").asText();

        EmailNotification notification = new EmailNotification();
        notification.setNotificationId(notificationId);
        notification.setUserId(userId);
        notification.setEmail(email);
        return notification;
    }

}