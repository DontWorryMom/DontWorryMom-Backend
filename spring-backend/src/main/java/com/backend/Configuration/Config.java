package com.backend.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Config {
	private static final Map<String,String> ENVIRONMENT_VARIABLES = System.getenv(); 

	public final static String KAFKA_HOST = ENVIRONMENT_VARIABLES.getOrDefault("KAFKA_HOST", "kafka:29092");
	public final static String TWILIO_ACCOUNT_SID = ENVIRONMENT_VARIABLES.get("TWILIO_ACCOUNT_SID");
	public final static String TWILIO_AUTH_TOKEN = ENVIRONMENT_VARIABLES.get("TWILIO_AUTH_TOKEN");
	public final static String TWILIO_FROM_NUMBER = ENVIRONMENT_VARIABLES.get("TWILIO_PHONE_NUMBER");
	public final static Double ACCELEROMETER_CRASH_THRESHOLD = Double.parseDouble(ENVIRONMENT_VARIABLES.get("ACCELEROMETER_CRASH_THRESHOLD")); 

	static {
		printConfig();
	}

	public static void printConfig() {		
		Map<String,Object> configVariables = new HashMap<>();
		configVariables.put("kafka-host", KAFKA_HOST);
		configVariables.put("twilio-account-sid", TWILIO_ACCOUNT_SID);
		configVariables.put("twilio-auth-token", TWILIO_AUTH_TOKEN);
		configVariables.put("twilio-from-number", TWILIO_FROM_NUMBER);
		configVariables.put("accelerometer-crash-threshold", ACCELEROMETER_CRASH_THRESHOLD);

		System.out.println("Service starting with config values:");
		for(Entry<String,Object> entry: configVariables.entrySet()) {
			System.out.println(String.format("\t%s = \'%s\'", entry.getKey(), entry.getValue()));
		}
	}
}