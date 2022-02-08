package com.backend.services.EntityDataService;

import com.backend.Models.User;
import com.backend.Models.UserDetailsWrapper;
import com.backend.util.UnauthorizedAccessException;

public abstract class BaseController {

	public void checkAccessToUser(Object principal, long userIdRequested) throws UnauthorizedAccessException {
		if (principal instanceof UserDetailsWrapper) {
			User authenticatedUser = ((UserDetailsWrapper) principal).getUser();
			if (authenticatedUser.getUserId() != userIdRequested) {
				throw new UnauthorizedAccessException("Current user ("+authenticatedUser+") cannot access the requested user resource with userId="+userIdRequested);
			}
		}
	}
	
}
