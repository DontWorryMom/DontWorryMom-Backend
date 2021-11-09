package com.backend.util;

import com.backend.Configuration.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class TwilioUtil {

	Config config;
	
	@Autowired
	public TwilioUtil(Config config) {
		// Initialize beans
		this.config = config;

		// Perform Service Instantiation
		Twilio.init(config.TWILIO_ACCOUNT_SID, config.TWILIO_AUTH_TOKEN);
	}

	public String sendMessage(String phoneNumber, String messageContents) {
		Message message = Message.creator(
			new PhoneNumber(phoneNumber),					// TO NUMBER
			new PhoneNumber(config.TWILIO_FROM_NUMBER),		// FROM NUMBER
			messageContents
		).create();
		return message.getSid().toString();
	}

}
