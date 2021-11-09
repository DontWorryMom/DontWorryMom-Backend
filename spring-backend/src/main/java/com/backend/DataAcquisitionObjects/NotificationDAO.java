package com.backend.DataAcquisitionObjects;

import java.util.List;
import java.util.Optional;

import com.backend.Models.Notification;
import com.backend.Repositories.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class NotificationDAO {
    
    NotificationRepository notificationRepository;

    @Autowired
    public NotificationDAO(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification getNotificationByNotificationId(long notificationId) {
		Optional<Notification> notif = notificationRepository.findById(notificationId);
		if(notif.isPresent()) {
			return notif.get();
		} else {
			return null;
		}
	}

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getNotificationByUserId(long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public Notification createNotification(Notification notif) {
        return notificationRepository.save(notif);
    }

    public Notification getNotificationById(long notificationId) {
        Optional<Notification> notif = notificationRepository.findById(notificationId);
        if(notif.isPresent()) {
            return notif.get();
        } else {
            return null;
        }
    }
}
