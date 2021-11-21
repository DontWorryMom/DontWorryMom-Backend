package com.backend.util;

import java.io.IOException;

import com.backend.Configuration.Config;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import org.springframework.stereotype.Component;

@Component
public class SendGridUtil {

	private Email fromEmail;
	private SendGrid sendGrid;
	
	public SendGridUtil() {
		fromEmail = new Email(Config.SENDGRID_EMAIL_ADDRESS);
		sendGrid = new SendGrid(Config.SENDGRID_API_KEY);
	}

	public String sendEmail(String toAddress, String subject, String messageContents) {
		Email to = new Email(toAddress);
		Content content = new Content("text/plain", messageContents);
		Mail mail = new Mail(fromEmail, subject, to, content);

		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sendGrid.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
			return response.getBody();
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
