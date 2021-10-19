package com.backend.services.EntityDataService;

import java.util.Collections;
import java.util.List;

import com.backend.Models.Device;
import com.backend.Models.ResponseWrapper;
import com.backend.DataAcquisitionObjects.DeviceDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@Autowired
	public DeviceController(DeviceDAO deviceDAO) {
		this.deviceDAO = deviceDAO;
	}
	
	/*
	 *		CREATE ENDPOINTS
	 */

	@PostMapping("/")
	public ResponseEntity<ResponseWrapper<Device>> createDevice(@RequestBody Device device) {
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(deviceDAO.createDevice(device)), 
			HttpStatus.CREATED);
	}
	
	/*
	 *		READ ENDPOINTS
	 */

	@GetMapping("/") 
	public ResponseEntity<ResponseWrapper<List<Device>>> getDeviceList() {
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(deviceDAO.getAllDevices()), 
			HttpStatus.OK);
	}

	@GetMapping("/deviceId/{deviceId}") 
	public ResponseEntity<ResponseWrapper<Device>> getDeviceById(@PathVariable("deviceId") int deviceId) {
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(deviceDAO.getDeviceById(deviceId)), 
			HttpStatus.OK);
	}

	@GetMapping("/userId/{userId}") 
	public ResponseEntity<ResponseWrapper<List<Device>>> getDeviceByUserId(@PathVariable("userId") int userId) {
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(deviceDAO.getAllDevicesFromUser(userId)), 
			HttpStatus.OK);
	}
	
	/*
	 *		UPDATE ENDPOINTS
	 */

	@PutMapping("/deviceId/{deviceId}")
	public ResponseEntity<ResponseWrapper<Device>> updateDeviceById(@PathVariable("deviceId") int deviceId, @RequestBody Device device) {
		// invalid request because given parameters violate constraints
		if(device.getDeviceId() != deviceId) {
			return new ResponseEntity<>(
				ResponseWrapper.failureResponse(
					Collections.singletonList("DeviceId in path variable ("+deviceId+") must match DeviceId in PUT request body ("+device.getDeviceId()+")")), 
				HttpStatus.BAD_REQUEST);
		} 

		Device updatedDevice = deviceDAO.updateDevice(device);
		if(updatedDevice == null) {
			// invalid request because Device cannot be updated as Device does not exist
			return new ResponseEntity<>(
				ResponseWrapper.failureResponse(
					Collections.singletonList("Was unable to find Device with DeviceId ("+deviceId+")")), 
				HttpStatus.BAD_REQUEST);
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
	public ResponseEntity<ResponseWrapper<Boolean>> deleteDeviceById(@PathVariable("deviceId") int deviceId) {
		deviceDAO.deleteDevice(deviceId);
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(true),
			HttpStatus.OK);
	}
}
