package com.backend.services.EntityDataService;

import java.util.List;


import com.backend.DataAcquisitionObjects.CrashDetectedNotificationDAO;
import com.backend.Models.CrashDetectedNotification;
import com.backend.Models.ResponseWrapper;
import com.backend.util.ResourceAccessChecker;
import com.backend.util.UnauthorizedAccessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("crashDetectedNotification")
public class CrashDetectedNotificationController {

	CrashDetectedNotificationDAO crashDetectedNotificationDAO;
	ResourceAccessChecker resourceAccessChecker;

	@Autowired
	public CrashDetectedNotificationController(CrashDetectedNotificationDAO crashDetectedNotificationDAO, ResourceAccessChecker resourceAccessChecker) {
		this.crashDetectedNotificationDAO = crashDetectedNotificationDAO;
		this.resourceAccessChecker = resourceAccessChecker;
	}
	
	/*
	 *		READ ENDPOINTS
	 */

	@GetMapping("")
	public ResponseEntity<ResponseWrapper<List<CrashDetectedNotification>>> getCrashDetectedNotificationList() throws UnauthorizedAccessException {
		// check the user has root user access
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkRootUser(principal);

		return new ResponseEntity<>(
			ResponseWrapper.successResponse(crashDetectedNotificationDAO.getCrashDetectedNotificationList()), 
			HttpStatus.OK);
	}
}
