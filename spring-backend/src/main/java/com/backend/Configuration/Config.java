package com.backend.Configuration;

import java.util.Map;

public class Config {
	private static final Map<String,String> ENVIRONMENT_VARIABLES = System.getenv(); 

	public final static String TWILIO_ACCOUNT_SID = ENVIRONMENT_VARIABLES.get("TWILIO_ACCOUNT_SID");
	public final static String TWILIO_AUTH_TOKEN = ENVIRONMENT_VARIABLES.get("TWILIO_AUTH_TOKEN");
	public final static String TWILIO_FROM_NUMBER = ENVIRONMENT_VARIABLES.get("TWILIO_PHONE_NUMBER");

	public final static String SENDGRID_API_KEY = ENVIRONMENT_VARIABLES.get("SENDGRID_API_KEY");
	public final static String SENDGRID_EMAIL_ADDRESS = ENVIRONMENT_VARIABLES.get("SENDGRID_EMAIL_ADDRESS");

	public final static Double ACCELEROMETER_CRASH_THRESHOLD = Double.parseDouble(ENVIRONMENT_VARIABLES.get("ACCELEROMETER_CRASH_THRESHOLD")); 

	public final static String SPRING_ADMIN_USER_NAME = ENVIRONMENT_VARIABLES.get("SPRING_ADMIN_USER_NAME");
	public final static String SPRING_ADMIN_USER_PASSWORD = ENVIRONMENT_VARIABLES.get("SPRING_ADMIN_USER_PASSWORD");

	static {
		printConfig();
	}

	public static void printConfig() {		
		printConfig("twilio-account-sid", TWILIO_ACCOUNT_SID);
		printConfig("twilio-auth-token", TWILIO_AUTH_TOKEN);
		printConfig("twilio-from-number", TWILIO_FROM_NUMBER);
		printConfig("sendgrid-api-key", SENDGRID_API_KEY);
		printConfig("sendgrid-email-address", SENDGRID_EMAIL_ADDRESS);
		printConfig("accelerometer-crash-threshold", ACCELEROMETER_CRASH_THRESHOLD);
		printConfig("spring-admin-user-name", SPRING_ADMIN_USER_NAME);
		printConfig("spring-admin-user-password", SPRING_ADMIN_USER_PASSWORD);
	}

	public static <T> void printConfig(String varName, T value) {
		System.out.println(String.format("\t%s = \'%s\'", varName, value));
	}
}
