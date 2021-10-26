package com.backend.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="device")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Device {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="device_id")
	int deviceId;

	@Column(name="user_id")
	int userId;

	public void assign(Device pDevice) {
		// this method copies variables from pUser into this
		// this is used to update the user in the repository
		this.userId = pDevice.userId;
	}
}
