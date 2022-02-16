package com.backend.services.EntityDataService;

import java.util.List;

import com.backend.Models.DeviceNotificationMethods;
import com.backend.Models.ResponseWrapper;
import com.backend.util.DontWorryMomException;
import com.backend.util.ResourceAccessChecker;
import com.backend.util.UnauthorizedAccessException;
import com.backend.DataAcquisitionObjects.DeviceNotificationMethodDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("deviceNotificationMethods")
public class DeviceNotificationMethodsController {

	DeviceNotificationMethodDAO dnmDAO;
	ResourceAccessChecker resourceAccessChecker;

	@Autowired
	public DeviceNotificationMethodsController(DeviceNotificationMethodDAO dnmDAO, ResourceAccessChecker resourceAccessChecker) {
		this.dnmDAO = dnmDAO;
		this.resourceAccessChecker = resourceAccessChecker;
	}

	/*
	 *		CREATE ENDPOINTS
	 */

	@PostMapping("")
	public ResponseEntity<ResponseWrapper<DeviceNotificationMethods>> createDevice(@RequestBody DeviceNotificationMethods dnm) throws UnauthorizedAccessException, DontWorryMomException {
		// check the user has access to the requested resource
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkAccessToDevice(principal, dnm.getDeviceId());
		this.resourceAccessChecker.checkAccessToNotification(principal, dnm.getNotificationId());
		
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(dnmDAO.createDeviceNotificationMethod(dnm)), 
			HttpStatus.CREATED);
	}

	/*
	 *		READ ENDPOINTS
	 */

	@GetMapping("") 
	public ResponseEntity<ResponseWrapper<List<DeviceNotificationMethods>>> getDeviceNotificationMethods() throws UnauthorizedAccessException {
		// check the user has root user access
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkRootUser(principal);

		return new ResponseEntity<>(
			ResponseWrapper.successResponse(dnmDAO.getAllDeviceNotificationMethods()), 
			HttpStatus.OK);
	}

	@GetMapping("/deviceId/{deviceId}") 
	public ResponseEntity<ResponseWrapper<List<DeviceNotificationMethods>>> getDeviceNotificationMethodsByDevice(@PathVariable("deviceId") long deviceId) throws DontWorryMomException, UnauthorizedAccessException {
		// check the user has access to the requested resource
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkAccessToDevice(principal, deviceId);

		return new ResponseEntity<>(
			ResponseWrapper.successResponse(dnmDAO.getDeviceNotificationMethodsByDeviceId(deviceId)), 
			HttpStatus.OK);
	}

	/*
	 *		DELETE ENDPOINTS
	 */

	@DeleteMapping("")
	public ResponseEntity<ResponseWrapper<Boolean>> deleteDeviceNotificationMethod(@RequestBody DeviceNotificationMethods dnm) throws UnauthorizedAccessException, DontWorryMomException {
		// check the user has access to the requested resource
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkAccessToDevice(principal, dnm.getDeviceId());

		dnmDAO.deleteDeviceNotificationMethod(dnm);
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(true),
			HttpStatus.OK);
	}
}
