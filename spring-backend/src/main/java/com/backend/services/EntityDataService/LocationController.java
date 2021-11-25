package com.backend.services.EntityDataService;

import java.time.Instant;
import java.util.List;

import com.backend.Models.CrashDetected;
import com.backend.Models.Location;
import com.backend.Models.ResponseWrapper;
import com.backend.util.LocationToNotificationProcessor;
import com.backend.DataAcquisitionObjects.LocationDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("locations")
public class LocationController {

	LocationDAO locationDAO;
	LocationToNotificationProcessor locationToNotificationProcessor;

	@Autowired
	public LocationController(LocationDAO locationDAO, LocationToNotificationProcessor locNotifProcessor) {
		this.locationDAO = locationDAO;
		this.locationToNotificationProcessor = locNotifProcessor;
	}
	
	/*
	 *		CREATE ENDPOINTS
	 */

	@PostMapping("/deviceId/{deviceId}")
	public ResponseEntity<ResponseWrapper<Location>> createLocation(@RequestBody Location location, @PathVariable("deviceId") long deviceId) {
		location.setDeviceId(deviceId);
		if(location.getLocationTime() == null) {
			Instant now = Instant.now();
			location.setLocationTime(now);
		}
		Location createdLocation = locationDAO.createLocation(location);

		if(createdLocation instanceof CrashDetected) {
			this.locationToNotificationProcessor.sendNotificationsForCrash((CrashDetected) createdLocation);
		}

		return new ResponseEntity<>(
			ResponseWrapper.successResponse(createdLocation), 
			HttpStatus.CREATED);
	}
	
	/*
	 *		READ ENDPOINTS
	 */

	@GetMapping("") 
	public ResponseEntity<ResponseWrapper<List<? extends Location>>> getLocations(
			@RequestParam(name="onlyCrashes", defaultValue="false") boolean onlyShowCrashes) {
		
		List<? extends Location> results;
		if(onlyShowCrashes) {
			results = locationDAO.getAllCrashDetecteds();
		} else {
			results = locationDAO.getAllLocations();
		}
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(results), 
			HttpStatus.OK);
	}

	@GetMapping("/deviceId/{deviceId}") 
	public ResponseEntity<ResponseWrapper<List<? extends Location>>> getLocationsByDeviceId(
			@PathVariable("deviceId") long deviceId,
			@RequestParam(name="onlyCrashes", defaultValue="false") boolean onlyShowCrashes) {
		
		List<? extends Location> results;
		if(onlyShowCrashes) {
			results = locationDAO.getAllCrashDetectedsFromDevice(deviceId);
		} else {
			results = locationDAO.getAllLocationsFromDevice(deviceId);
		}
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(results), 
			HttpStatus.OK);
	}
}
