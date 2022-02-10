package com.backend.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	long deviceId;

	@Column(name="user_id")
	long userId;

	@JsonIgnore
	@Column(name="secondary_id")
	// secondaryId (generally used to store device MacAddress)
	// will be used for the location post endpoint from devices
	// if secondaryId is non-null, both the deviceId and secondaryId
	// will be needed to store location data, so that the device
	// location cannot be spoofed without knowledge of both
	String secondaryId;

	public void assign(Device pDevice) {
		// this method copies variables from pDevice into this
		// this is used to update the device in the repository
		this.userId = pDevice.userId;
		this.secondaryId = pDevice.secondaryId;
	}
}
