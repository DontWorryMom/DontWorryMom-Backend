package com.backend.DataAcquisitionObjects;

import java.util.List;
import java.util.Optional;

import com.backend.Models.User;
import com.backend.Repositories.EntityDataRepositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDAO {

	UserRepository userRepository;

	@Autowired
	public UserDAO(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(long userId) {
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
	}

	public User createUser(User user) {
		return userRepository.save(user);
	}

	public void deleteUser(long userId) {
		userRepository.deleteById(userId);
	}

	public User updateUser(User pUser) {
		Optional<User> userOptional = userRepository.findById(pUser.getUserId());
		if(userOptional.isPresent()) {
			User user = userOptional.get();
			user.assign(pUser);
			userRepository.save(user);
			return user;
		} else {
			return null;
		}
	}
}
