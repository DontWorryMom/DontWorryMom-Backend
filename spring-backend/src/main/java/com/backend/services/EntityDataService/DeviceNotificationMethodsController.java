package com.backend.services.EntityDataService;

import java.util.List;

import com.backend.Models.DeviceNotificationMethods;
import com.backend.Models.ResponseWrapper;
import com.backend.DataAcquisitionObjects.DeviceNotificationMethodDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("deviceNotificationMethods")
public class DeviceNotificationMethodsController {

	DeviceNotificationMethodDAO dnmDAO;

	@Autowired
	public DeviceNotificationMethodsController(DeviceNotificationMethodDAO dnmDAO) {
		this.dnmDAO = dnmDAO;
	}

	/*
	 *		CREATE ENDPOINTS
	 */

	@PostMapping("")
	public ResponseEntity<ResponseWrapper<DeviceNotificationMethods>> createDevice(@RequestBody DeviceNotificationMethods dnm) {
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(dnmDAO.createDeviceNotificationMethod(dnm)), 
			HttpStatus.CREATED);
	}

	/*
	 *		READ ENDPOINTS
	 */

	@GetMapping("") 
	public ResponseEntity<ResponseWrapper<List<DeviceNotificationMethods>>> getDeviceNotificationMethods() {
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(dnmDAO.getAllDeviceNotificationMethods()), 
			HttpStatus.OK);
	}

	/*
	 *		DELETE ENDPOINTS
	 */

	@DeleteMapping("")
	public ResponseEntity<ResponseWrapper<Boolean>> deleteDeviceNotificationMethod(@RequestBody DeviceNotificationMethods dnm) {
		dnmDAO.deleteDeviceNotificationMethod(dnm);
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(true),
			HttpStatus.OK);
	}
}
