package com.backend.Models;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.backend.Deserializers.LocationDeserializer;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="past_locations")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@JsonDeserialize(using=LocationDeserializer.class)
public class Location implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="location_id")
	Long locationId;

	@Column(name="device_id")
	Long deviceId;

	@Column(name="location_time")
	Instant locationTime;

	@Column(name="location_lat")
	float locationLat;

	@Column(name="location_lon")
	float locationLon;

    @JsonGetter
    public boolean getCrashDetected() {
        return false;
    }

    public static Location parse(JsonNode node) throws JsonProcessingException {
        Long locationId = null;            	// default value to be overwritten
        Long deviceId = null;				// for deviceId, and locationTime, the data should be non-null in practice, 
		Instant locationTime = null;		// but it could be non-null in the payload, and should not throw an error in parsing
        if(node.has("locationId")) {
            locationId = node.get("locationId").asLong();
        }
        if(node.has("deviceId")) {
			deviceId = node.get("deviceId").asLong();
        }
		if(node.has("locationTime")) {
			locationTime = Instant.parse(node.get("locationTime").asText());
		}
		float locationLat = (float) node.get("locationLat").asDouble();
		float locationLon = (float) node.get("locationLon").asDouble();

		return new Location(locationId, deviceId, locationTime, locationLat, locationLon);
    }
}
