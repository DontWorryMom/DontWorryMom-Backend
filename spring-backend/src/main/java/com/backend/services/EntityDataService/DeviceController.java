package com.backend.services.EntityDataService;

import java.util.Collections;
import java.util.List;

import com.backend.Models.Device;
import com.backend.Models.ResponseWrapper;
import com.backend.util.DontWorryMomException;
import com.backend.util.ResourceAccessChecker;
import com.backend.util.UnauthorizedAccessException;
import com.backend.DataAcquisitionObjects.DeviceDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("devices")
public class DeviceController {
	// CRUD Interface for Device Entity

	DeviceDAO deviceDAO;
	ResourceAccessChecker resourceAccessChecker;

	@Autowired
	public DeviceController(DeviceDAO deviceDAO, ResourceAccessChecker resourceAccessChecker) {
		this.deviceDAO = deviceDAO;
		this.resourceAccessChecker = resourceAccessChecker;
	}
	
	/*
	 *		CREATE ENDPOINTS
	 */

	@PostMapping("")
	public ResponseEntity<ResponseWrapper<Device>> createDevice(@RequestBody Device device) throws UnauthorizedAccessException {
		// check the user has access to add a device to the account
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkAccessToUser(principal, device.getUserId());

		// create the device for that user
		Device createdDevice = deviceDAO.createDevice(device);
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(createdDevice), 
			HttpStatus.CREATED);
	}
	
	/*
	 *		READ ENDPOINTS
	 */

	@GetMapping("") 
	public ResponseEntity<ResponseWrapper<List<Device>>> getDeviceList() throws UnauthorizedAccessException {
		// check the user has access to the requested resource
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkRootUser(principal);
		
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(deviceDAO.getAllDevices()), 
			HttpStatus.OK);
	}

	@GetMapping("/deviceId/{deviceId}") 
	public ResponseEntity<ResponseWrapper<Device>> getDeviceById(@PathVariable("deviceId") long deviceId) throws UnauthorizedAccessException {
		// retrieve the device
		Device device = deviceDAO.getDeviceById(deviceId);

		// check the user has access to the requested resource
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkAccessToDevice(principal, device);

		// return the device
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(device), 
			HttpStatus.OK);
	}

	@GetMapping("/userId/{userId}") 
	public ResponseEntity<ResponseWrapper<List<Device>>> getDeviceByUserId(@PathVariable("userId") long userId) throws UnauthorizedAccessException {
		// check the user has access to the requested resource
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkAccessToUser(principal, userId);
		
		// return the devices
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(deviceDAO.getAllDevicesFromUser(userId)), 
			HttpStatus.OK);
	}
	
	/*
	 *		UPDATE ENDPOINTS
	 */

	@PutMapping("/deviceId/{deviceId}")
	public ResponseEntity<ResponseWrapper<Device>> updateDeviceById(@PathVariable("deviceId") long deviceId, @RequestBody Device device) throws UnauthorizedAccessException, DontWorryMomException {
		// invalid request because given parameters violate constraints
		if(device.getDeviceId() != deviceId) {
			throw new DontWorryMomException(
				HttpStatus.BAD_REQUEST.value(),
				"DeviceId in path variable ("+deviceId+") must match DeviceId in PUT request body ("+device.getDeviceId()+")"
			);
		}

		// check that the device exists and
		// that the user has access to the requested device
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkAccessToDevice(principal, deviceId);
		
		// if the device ownership is being changed, the user needs access to the other account as well
		// currently only the root user has access to multiple acccounts and can change the userId
		this.resourceAccessChecker.checkAccessToUser(principal, device.getUserId());

		// update the device
		Device updatedDevice = deviceDAO.updateDevice(device);
		if(updatedDevice == null) {
			// invalid request because Device cannot be updated as Device does not exist
			throw new DontWorryMomException(
				HttpStatus.BAD_REQUEST.value(),
				"Was unable to find Device with DeviceId ("+deviceId+")"	
			);
		} else {
			// success response
			return new ResponseEntity<>(
				ResponseWrapper.successResponse(updatedDevice), 
				HttpStatus.OK);
		}
	}
	
	/*
	 *		DESTROY ENDPOINTS
	 */

	@DeleteMapping("/deviceId/{deviceId}")
	public ResponseEntity<ResponseWrapper<Boolean>> deleteDeviceById(@PathVariable("deviceId") long deviceId) throws DontWorryMomException {
		// check that the device exists and
		// that the user has access to the requested device
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkAccessToDevice(principal, deviceId);

		// delete the device
		deviceDAO.deleteDevice(deviceId);
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(true),
			HttpStatus.OK);
	}
}
