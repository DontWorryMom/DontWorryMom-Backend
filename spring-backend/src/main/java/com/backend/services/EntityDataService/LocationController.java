package com.backend.services.EntityDataService;

import java.util.List;

import com.backend.Models.Location;
import com.backend.Models.ResponseWrapper;
import com.backend.DataAcquisitionObjects.LocationDAO;

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
@RequestMapping("locations")
public class LocationController {

	LocationDAO locationDAO;

	@Autowired
	public LocationController(LocationDAO locationDAO) {
		this.locationDAO = locationDAO;
	}
	
	/*
	 *		CREATE ENDPOINTS
	 */

	@PostMapping("/")
	public ResponseEntity<ResponseWrapper<Location>> createDevice(@RequestBody Location location) {
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(locationDAO.createLocation(location)), 
			HttpStatus.CREATED);
	}
	
	/*
	 *		READ ENDPOINTS
	 */

	@GetMapping("/") 
	public ResponseEntity<ResponseWrapper<List<Location>>> getLocations() {
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(locationDAO.getAllLocations()), 
			HttpStatus.OK);
	}

	@GetMapping("/deviceId/{deviceId}") 
	public ResponseEntity<ResponseWrapper<List<Location>>> getLocationsByDeviceId(@PathVariable("deviceId") int deviceId) {
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(locationDAO.getAllLocationsFromDevice(deviceId)), 
			HttpStatus.OK);
	}
}
