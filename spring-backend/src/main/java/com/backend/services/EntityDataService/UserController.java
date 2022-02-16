package com.backend.services.EntityDataService;

import java.util.Collections;
import java.util.List;

import com.backend.Models.User;
import com.backend.util.DontWorryMomException;
import com.backend.util.ResourceAccessChecker;
import com.backend.util.UnauthorizedAccessException;
import com.backend.Models.ResponseWrapper;
import com.backend.DataAcquisitionObjects.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {
	// CRUD Interface for User Entity

	UserDAO userDAO;
	ResourceAccessChecker resourceAccessChecker;

	@Autowired
	public UserController(UserDAO userDAO, ResourceAccessChecker resourceAccessChecker) {
		this.userDAO = userDAO;
		this.resourceAccessChecker = resourceAccessChecker;
	}
	
	/*
	 *		CREATE ENDPOINTS
	 */

	@PostMapping("")
	public ResponseEntity<ResponseWrapper<User>> createUser(@RequestBody User user) {
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(userDAO.createUser(user)), 
			HttpStatus.CREATED);
	}
	
	/*
	 *		READ ENDPOINTS
	 */

	@GetMapping("") 
	public ResponseEntity<ResponseWrapper<List<User>>> getUserList() throws UnauthorizedAccessException {
		// check the user has access to the requested resource
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkRootUser(principal);

		return new ResponseEntity<>(
			ResponseWrapper.successResponse(userDAO.getAllUsers()), 
			HttpStatus.OK);
	}

	@GetMapping("/userId/{userId}") 
	public ResponseEntity<ResponseWrapper<User>> getUserById(@PathVariable("userId") long userId) throws UnauthorizedAccessException {
		// check the user has access to the requested resource
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkAccessToUser(principal, userId);

		// serve the requested resource
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(userDAO.getUserById(userId)), 
			HttpStatus.OK);
	}
	
	/*
	 *		UPDATE ENDPOINTS
	 */

	@PutMapping("/userId/{userId}")
	public ResponseEntity<ResponseWrapper<User>> updateUserById(@PathVariable("userId") long userId, @RequestBody User user) throws UnauthorizedAccessException, DontWorryMomException {
		// check the user has access to the requested resource
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkAccessToUser(principal, userId);

		// invalidate request if given parameters violate constraints
		if(user.getUserId() != userId) {
			throw new DontWorryMomException(HttpStatus.BAD_REQUEST.value(), "userId in path variable ("+userId+") must match userId in PUT request body ("+user.getUserId()+")");
		} 

		// update the user model since everything is valid
		User updatedUser = userDAO.updateUser(user);
		if(updatedUser == null) {
			// invalid request because user cannot be updated as user does not exist
			return new ResponseEntity<>(
				ResponseWrapper.failureResponse(
					Collections.singletonList("Was unable to find user with userId ("+userId+")")), 
				HttpStatus.BAD_REQUEST);
		} else {
			// success response
			return new ResponseEntity<>(
				ResponseWrapper.successResponse(updatedUser), 
				HttpStatus.OK);
		}
	}
	
	/*
	 *		DESTROY ENDPOINTS
	 */

	@DeleteMapping("/userId/{userId}")
	public ResponseEntity<ResponseWrapper<Boolean>> deleteUserById(@PathVariable("userId") long userId) throws UnauthorizedAccessException {
		// check the user has access to the requested resource
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.resourceAccessChecker.checkAccessToUser(principal, userId);

		// delete the user since we have checked the authorization
		userDAO.deleteUser(userId);
		return new ResponseEntity<>(
			ResponseWrapper.successResponse(true),
			HttpStatus.OK);
	}
}
