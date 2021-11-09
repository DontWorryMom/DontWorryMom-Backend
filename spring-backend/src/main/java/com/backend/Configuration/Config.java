package com.backend.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

@Component
public class Config {
	private static final Map<String,String> ENVIRONMENT_VARIABLES = System.getenv(); 

	public final String KAFKA_HOST = ENVIRONMENT_VARIABLES.getOrDefault("KAFKA_HOST", "kafka:29092");
	public final String TWILIO_ACCOUNT_SID = ENVIRONMENT_VARIABLES.get("TWILIO_ACCOUNT_SID");
	public final String TWILIO_AUTH_TOKEN = ENVIRONMENT_VARIABLES.get("TWILIO_AUTH_TOKEN");

	// As a Spring component, this class is effectively static
	// with a single instance initialized on boot
	public Config() {
		printConfig();
	}

	public void printConfig() {		
		Map<String,String> configVariables = new HashMap<>();
		configVariables.put("kafka-host", KAFKA_HOST);
		configVariables.put("twilio-account-sid", TWILIO_ACCOUNT_SID);
		configVariables.put("twilio-auth-token", TWILIO_AUTH_TOKEN);

		System.out.println("Service starting with config values:");
		for(Entry<String,String> entry: configVariables.entrySet()) {
			System.out.println(String.format("\t%s = \'%s\'", entry.getKey(), entry.getValue()));
		}
	}
}
