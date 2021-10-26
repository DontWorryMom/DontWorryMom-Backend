package com.backend.DataAcquisitionObjects;

import java.util.List;
import java.util.Optional;

import com.backend.Models.User;
import com.backend.Models.Device;
import com.backend.Models.Location;
import com.backend.Repositories.DeviceRepository;
import com.backend.Repositories.LocationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LocationDAO {
    
    LocationRepository locationRepository;

    @Autowired
    public LocationDAO(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

	public List<Location> getAllLocationsFromDevice(Device device) {
		return this.getAllLocationsFromDevice(device.getDeviceId());
	}

	public List<Location> getAllLocationsFromDevice(long deviceId) {
		return locationRepository.findByDeviceId(deviceId);
	}

	public Location createLocation(Location location) {
		return locationRepository.save(location);
	}
}
