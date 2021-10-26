package com.backend.services.EntityDataService;

import java.util.List;

import com.backend.DataAcquisitionObjects.NotificationDAO;
import com.backend.Models.Notification;
import com.backend.Models.ResponseWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notifications")
public class NotificationController {

	NotificationDAO notificationDAO;

	@Autowired
	public NotificationController(NotificationDAO notificationDAO) {
		this.notificationDAO = notificationDAO;
	}
    
    @GetMapping("/")
    public ResponseEntity<ResponseWrapper<List<Notification>>> getAllNotifications() {
        return new ResponseEntity<>(
            ResponseWrapper.successResponse(notificationDAO.getAllNotifications()),
            HttpStatus.OK
        );
    }
    
    @PostMapping("/userId/{userId}")
    public ResponseEntity<ResponseWrapper<Notification>> createNotification(@RequestBody Notification notification, @PathVariable("userId") long userId) {
        notification.setUserId(userId);
        notification = notificationDAO.createNotification(notification);
        return new ResponseEntity<>(
            ResponseWrapper.successResponse(notification),
            HttpStatus.OK
        );
    }
}
