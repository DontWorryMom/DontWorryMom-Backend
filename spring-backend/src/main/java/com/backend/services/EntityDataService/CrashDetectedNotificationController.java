package com.backend.services.EntityDataService;

import java.util.List;


import com.backend.DataAcquisitionObjects.CrashDetectedNotificationDAO;
import com.backend.Models.CrashDetectedNotification;
import com.backend.Models.ResponseWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("crashDetectedNotification")
public class CrashDetectedNotificationController {

	CrashDetectedNotificationDAO crashDetectedNotificationDAO;

	@Autowired
	public CrashDetectedNotificationController(CrashDetectedNotificationDAO crashDetectedNotificationDAO) {
		this.crashDetectedNotificationDAO = crashDetectedNotificationDAO;
	}
	
	/*
	 *		READ ENDPOINTS
	 */

	@GetMapping("")
	public ResponseEntity<ResponseWrapper<List<CrashDetectedNotification>>> getCrashDetectedNotificationList() {
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(crashDetectedNotificationDAO.getCrashDetectedNotificationList()), 
			HttpStatus.OK);
	}
}
