package com.backend.Repositories.EntityDataRepositories;

import com.backend.Models.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	
}
