package com.backend.Repositories.EntityDataRepositories;

import java.util.Optional;

import com.backend.Models.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByUsername(String username);
}
