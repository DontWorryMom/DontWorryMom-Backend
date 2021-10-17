package com.backend.services.DataAcquisitionService;

import java.util.List;

import com.backend.Models.User;
import com.backend.DataAcquisitionObjects.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataAcquisitionController {

	UserDAO userDAO;

	@Autowired
	public DataAcquisitionController(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@GetMapping("/users/") 
	public ResponseEntity<List<User>> getUserList() {
		return ResponseEntity.ok(userDAO.getAllUsers());
	}

	
	
}
