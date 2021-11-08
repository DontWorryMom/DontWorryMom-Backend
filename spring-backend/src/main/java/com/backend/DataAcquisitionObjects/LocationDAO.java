package com.backend.DataAcquisitionObjects;

import java.util.List;
import java.util.Optional;

import com.backend.Models.User;
import com.backend.Models.CrashDetected;
import com.backend.Models.Device;
import com.backend.Models.Location;
import com.backend.Repositories.CrashDetectedRepository;
import com.backend.Repositories.DeviceRepository;
import com.backend.Repositories.LocationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LocationDAO {
    
    LocationRepository locationRepository;
	CrashDetectedRepository crashDetectedRepository;

    @Autowired
    public LocationDAO(LocationRepository locationRepository, CrashDetectedRepository crashDetectedRepository) {
        this.locationRepository = locationRepository;
		this.crashDetectedRepository = crashDetectedRepository;
    }
	
	/*
	 *	Create a location data input
	 */

	public Location createLocation(Location location) {
		return locationRepository.save(location);
	}

	/*
	 *	Get all location data, regardless of crash status
	 */

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

	public List<Location> getAllLocationsFromDevice(Device device) {
		return this.getAllLocationsFromDevice(device.getDeviceId());
	}

	public List<Location> getAllLocationsFromDevice(long deviceId) {
		return locationRepository.findByDeviceId(deviceId);
	}

	/*
	 *	Get all location data, only if crash was detected
	 */

	public List<CrashDetected> getAllCrashDetecteds() {
		return crashDetectedRepository.findAll();
	}

	public List<CrashDetected> getAllCrashDetectedsFromDevice(Device device) {
		return getAllCrashDetectedsFromDevice(device.getDeviceId());
	}

	public List<CrashDetected> getAllCrashDetectedsFromDevice(long deviceId) {
		return crashDetectedRepository.findByDeviceId(deviceId);
	}
}
