package com.backend.util;

import com.backend.Configuration.Config;

import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class TwilioUtil {
	
	public TwilioUtil() {
		// Perform Service Instantiation
		Twilio.init(Config.TWILIO_ACCOUNT_SID, Config.TWILIO_AUTH_TOKEN);
	}

	public String sendMessage(String phoneNumber, String messageContents) {
		Message message = Message.creator(
			new PhoneNumber(phoneNumber),					// TO NUMBER
			new PhoneNumber(Config.TWILIO_FROM_NUMBER),		// FROM NUMBER
			messageContents
		).create();
		return message.getSid();
	}

}
