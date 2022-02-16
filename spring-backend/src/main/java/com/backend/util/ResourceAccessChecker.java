package com.backend.util;

import com.backend.DataAcquisitionObjects.DeviceDAO;
import com.backend.Models.Device;
import com.backend.Models.User;
import com.backend.Models.UserDetailsRootUser;
import com.backend.Models.UserDetailsWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ResourceAccessChecker {

	DeviceDAO deviceDAO;

	@Autowired
	public ResourceAccessChecker(DeviceDAO deviceDAO) {
		this.deviceDAO = deviceDAO;
	}

	/*
	 * 	Checking Root User Access
	 */

	public void checkRootUser(Object principal) throws UnauthorizedAccessException {
		if (principal instanceof UserDetailsRootUser) {
			return;
		} else {
			throw new UnauthorizedAccessException("Only the root user has access to this requested resource");
		}
	}

	/*
	 * 	Checking Access to User Resource
	 */

	public void checkAccessToUser(Object principal, long userIdRequested) throws UnauthorizedAccessException {
		if (principal instanceof UserDetailsRootUser) {
			// root user has access to all users
			return;
		}
		else if (principal instanceof UserDetailsWrapper) {
			User authenticatedUser = ((UserDetailsWrapper) principal).getUser();
			if (authenticatedUser.getUserId() != userIdRequested) {
				throw new UnauthorizedAccessException("Current user ("+authenticatedUser+") cannot access the requested user resource with userId="+userIdRequested);
			}
		}
		else {
			throw new UnauthorizedAccessException("Cannot determine what user is requesting the requested resource");
		}
	}

	/*
	 * 	Checking Access to Device Resource
	 */

	public void checkAccessToDevice(Object principal, Device deviceRequested) throws UnauthorizedAccessException {
		if (principal instanceof UserDetailsRootUser) {
			// root user has access to all devices
			return;
		}
		else if (principal instanceof UserDetailsWrapper) {
			User authenticatedUser = ((UserDetailsWrapper) principal).getUser();
			if (authenticatedUser.getUserId() != deviceRequested.getUserId()) {
				throw new UnauthorizedAccessException("Current user ("+authenticatedUser+") cannot access the requested device resource with deviceId="+deviceRequested.getDeviceId());
			}
		}
		else {
			throw new UnauthorizedAccessException("Cannot determine what user is requesting the requested resource");
		}
	}

	public void checkAccessToDevice(Object principal, long deviceId) throws UnauthorizedAccessException, DontWorryMomException {
		Device deviceRequested = deviceDAO.getDeviceById(deviceId);
		if (deviceRequested == null) {
			throw new DontWorryMomException(
				HttpStatus.BAD_REQUEST.value(),
				"Was unable to find Device with DeviceId ("+deviceId+")"	
			);
		}
		checkAccessToDevice(principal, deviceRequested);
	}
	
}
