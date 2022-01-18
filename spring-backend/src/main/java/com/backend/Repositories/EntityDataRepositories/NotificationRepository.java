package com.backend.Repositories.EntityDataRepositories;

import java.util.List;

import com.backend.Models.Notification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	List<Notification> findByUserId(long userId);
}
