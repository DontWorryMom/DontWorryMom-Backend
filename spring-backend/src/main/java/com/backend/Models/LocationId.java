package com.backend.Models;

import lombok.NoArgsConstructor;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationId implements Serializable {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int deviceId;
	public int locationId;
}