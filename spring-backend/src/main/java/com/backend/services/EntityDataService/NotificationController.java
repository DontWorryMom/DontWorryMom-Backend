package com.backend.services.EntityDataService;

import java.util.List;

import com.backend.DataAcquisitionObjects.NotificationDAO;
import com.backend.Models.Notification;
import com.backend.Models.ResponseWrapper;
import com.backend.util.DontWorryMomException;
import com.backend.util.ResourceAccessChecker;
import com.backend.util.UnauthorizedAccessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    ResourceAccessChecker resourceAccessChecker;

	@Autowired
	public NotificationController(NotificationDAO notificationDAO, ResourceAccessChecker resourceAccessChecker) {
		this.notificationDAO = notificationDAO;
        this.resourceAccessChecker = resourceAccessChecker;
	}

    /*
     *      READ ENDPOINTS
     */
    
    @GetMapping("")
    public ResponseEntity<ResponseWrapper<List<Notification>>> getAllNotifications() throws UnauthorizedAccessException {
		// check the user has root user access
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkRootUser(principal);
        
        return new ResponseEntity<>(
            ResponseWrapper.successResponse(notificationDAO.getAllNotifications()),
            HttpStatus.OK
        );
    }
    
    @GetMapping("/notificationId/{notificationId}")
    public ResponseEntity<ResponseWrapper<Notification>> getNotification(@PathVariable("notificationId") long notificationId) throws UnauthorizedAccessException, DontWorryMomException {
        Notification requestedNotification = notificationDAO.getNotificationById(notificationId);
        if (requestedNotification == null) {
			throw new DontWorryMomException(
				HttpStatus.BAD_REQUEST.value(),
				"Was unable to find Notification with NotificationId ("+notificationId+")"	
			);
        }

		// check the user has access to the requested resource
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkAccessToNotification(principal, requestedNotification);

        return new ResponseEntity<>(
            ResponseWrapper.successResponse(requestedNotification),
            HttpStatus.OK
        );
    }
    
    @GetMapping("/userId/{userId}")
    public ResponseEntity<ResponseWrapper<List<Notification>>> getNotificationByUserId(@PathVariable("userId") long userId) throws UnauthorizedAccessException {
		// check the user has access to the requested resource
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkAccessToUser(principal, userId);

        return new ResponseEntity<>(
            ResponseWrapper.successResponse(notificationDAO.getNotificationByUserId(userId)),
            HttpStatus.OK
        );
    }

    /*
     *      CREATE ENDPOINTS
     */
    
    @PostMapping("/userId/{userId}")
    public ResponseEntity<ResponseWrapper<Notification>> createNotification(@RequestBody Notification notification, @PathVariable("userId") long userId) throws UnauthorizedAccessException {
		// check the user has access to the requested resource
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkAccessToUser(principal, userId);
        
        notification.setUserId(userId);
        notification = notificationDAO.createNotification(notification);
        return new ResponseEntity<>(
            ResponseWrapper.successResponse(notification),
            HttpStatus.OK
        );
    }
}
