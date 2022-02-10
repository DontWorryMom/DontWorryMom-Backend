package com.backend.services.EntityDataService;

import com.backend.Models.Device;
import com.backend.Models.User;
import com.backend.Models.UserDetailsRootUser;
import com.backend.Models.UserDetailsWrapper;
import com.backend.util.UnauthorizedAccessException;

public abstract class BaseController {

	public void checkRootUser(Object principal) throws UnauthorizedAccessException {
		if (principal instanceof UserDetailsRootUser) {
			return;
		} else {
			throw new UnauthorizedAccessException("Only the root user has access to this requested resource");
		}
	}

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
	
}
