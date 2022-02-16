package com.backend.services.EntityDataService;

import java.time.Instant;
import java.util.List;
import com.backend.Models.CrashDetected;
import com.backend.Models.Device;
import com.backend.Models.Location;
import com.backend.Models.ResponseWrapper;
import com.backend.util.DontWorryMomException;
import com.backend.util.LocationToNotificationProcessor;
import com.backend.util.ResourceAccessChecker;
import com.backend.util.UnauthorizedAccessException;
import com.backend.DataAcquisitionObjects.DeviceDAO;
import com.backend.DataAcquisitionObjects.LocationDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

	DeviceDAO deviceDAO;
	LocationDAO locationDAO;
	LocationToNotificationProcessor locationToNotificationProcessor;
	ResourceAccessChecker resourceAccessChecker;

	@Autowired
	public LocationController(
			LocationDAO locationDAO, 
			DeviceDAO deviceDAO, 
			LocationToNotificationProcessor locNotifProcessor, 
			ResourceAccessChecker resourceAccessChecker) {
		this.locationDAO = locationDAO;
		this.deviceDAO = deviceDAO;
		this.locationToNotificationProcessor = locNotifProcessor;
		this.resourceAccessChecker = resourceAccessChecker;
	}
	
	/*
	 *		CREATE ENDPOINTS
	 */

	@PostMapping("/deviceId/{deviceId}")
	public ResponseEntity<ResponseWrapper<Location>> createLocation(
			@RequestBody Location location, 
			@PathVariable("deviceId") long deviceId,
			@RequestParam(name="macAddr", required=false) String secondaryId) 
		throws DontWorryMomException {

		// Check if the device exists and if the device has the same macAddr
		Device d = deviceDAO.getDeviceById(deviceId);
		if(d==null) {
			throw new DontWorryMomException(HttpStatus.NOT_FOUND.value(), 
				String.format("Device with id=%s does not exist", deviceId));
		}

		// if this is the first time the device checks in, it's secondaryId will be null
		// so we should set it here
		if(d.getSecondaryId()==null && secondaryId != null && !secondaryId.isBlank()) {
			d.setSecondaryId(secondaryId);
			deviceDAO.updateDevice(d);
		}

		// we need to check if the secondaryIds are equal
		// if the device did not have a secondaryId, and the device sends data without
		// a secondaryId to this endpoint, we will allow it as maybe the device
		// has not been updated to send the secondaryId yet
		if(d.getSecondaryId()!=null && !d.getSecondaryId().equals(secondaryId)) {
			throw new DontWorryMomException(HttpStatus.BAD_REQUEST.value(), 
				String.format("SecondaryId (%s) given does not match with SecondaryId of Device with id=%s", secondaryId, deviceId));
		}

		// save the location data into the location/crash detected table
		location.setDeviceId(deviceId);
		if(location.getLocationTime() == null) {
			Instant now = Instant.now();
			location.setLocationTime(now);
		}
		Location createdLocation = locationDAO.createLocation(location);

		// send the notification if a crash was detected
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
			@RequestParam(name="onlyCrashes", defaultValue="false") boolean onlyShowCrashes) throws UnauthorizedAccessException {
		// check the user has root user access
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkRootUser(principal);

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
			@RequestParam(name="onlyCrashes", defaultValue="false") boolean onlyShowCrashes) throws DontWorryMomException, UnauthorizedAccessException {
		// check the user has access to the requested resource
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkAccessToDevice(principal, deviceId);
		
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
