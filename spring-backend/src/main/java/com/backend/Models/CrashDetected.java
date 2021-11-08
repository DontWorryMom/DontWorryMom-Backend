package com.backend.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@Table(name="crash_detected")
@PrimaryKeyJoinColumn(name="location_id")
@NoArgsConstructor
@SuperBuilder
public class CrashDetected extends Location {

	public static final double CRASH_THRESHOLD_ACCELERATION = 9.8;
	
	@Column(name="max_acceleration")
	float maxAcceleration;

    @Override
    public boolean getCrashDetected() {
        return true;
    }

    public static Location parse(JsonNode node) throws JsonProcessingException {
		Location loc = Location.parse(node);
		float maxAcceleration = (float) node.get("maxAcceleration").asDouble();
		if(maxAcceleration >= CRASH_THRESHOLD_ACCELERATION) {
			return CrashDetected.builder()
				// parent properties
				.locationId(loc.getLocationId())
				.deviceId(loc.getDeviceId())
				.locationTime(loc.getLocationTime())
				.locationLat(loc.getLocationLat())
				.locationLon(loc.getLocationLon())
				// child properties
				.maxAcceleration(maxAcceleration)
				.build();
		} else {
			return loc;
		}
    }
}
