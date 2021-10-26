package com.backend.Models;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="past_locations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(LocationId.class)
public class Location implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="location_id")
	int locationId;

	@Id
	@Column(name="device_id")
	int deviceId;

	@Column(name="location_time")
	Instant locationTime;

	@Column(name="location_lat")
	float locationLat;

	@Column(name="location_lon")
	float locationLon;
}
