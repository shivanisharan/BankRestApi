package com.rest.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailService {
	//String to = "shivanisharan15@gmail.com";
	static Properties properties=null;
	
	public static final String MAIL_REGISTRATION_SITE_LINK = "http://localhost:9090/apanabank_RESTAPI/EmailVerification";
	
	public static void getMailProperties(){
		try {
			File file = new File("C:\\Users\\user\\workspace\\apanabank_RESTAPI\\src\\main\\resources\\mail.properties");
			FileInputStream fileInput = new FileInputStream(file);
			properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			System.out.println(properties.getProperty("vendor_email"));
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMail(final int uid,final String uname,final String email,final String hash) {
		
		getMailProperties();
		final String from = properties.getProperty("vendor_email");
		final String password = properties.getProperty("vendor_password");
		Thread thread = new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				System.out.println("mail thread running");

				String host = "localhost";
				String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

				Authenticator auth = new Authenticator() {
					// override the getPasswordAuthentication method
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(from, password);
					}

				};
				String link = MAIL_REGISTRATION_SITE_LINK+"?scope=activation&userName="+uname+"&userID="+uid+"&hash="+hash;
			        
		          StringBuilder bodyText = new StringBuilder(); 
		            bodyText.append("<div>")
		                 .append("  Dear User<br/><br/>")
		                 .append("  Thank you for registration. Your mail ("+email+") is under verification<br/>")
		                 .append("  Please click <a href=\""+link+"\">here</a> or open below link in browser<br/>")
		                 .append("  <a href=\""+link+"\">"+link+"</a>")
		                 .append("  <br/><br/>")
		                 .append("  Thanks,<br/>")
		                 .append("  Aapna Bank Team")
		                 .append("</div>");
				// get the session object
				Properties properties = System.getProperties();
				properties.setProperty("mail.smtp.host", "smtp.gmail.com");
				properties.put("mail.smtp.starttls.enable", "true");
				// properties.put("mail.smtp.socketFactory.port", "465");
				properties.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
				properties.setProperty("mail.smtp.socketFactory.fallback", "false");
				properties.setProperty("mail.smtp.port", "465");
				properties.setProperty("mail.smtp.socketFactory.port", "465");
				properties.put("mail.smtp.auth", "true");
				properties.put("mail.debug", "true");
				properties.put("mail.store.protocol", "pop3");
				properties.put("mail.transport.protocol", "smtp");
				Session session = Session.getDefaultInstance(properties, auth);
				// compose message
				try {
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress(from));
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
					message.setSubject("Hi from Aapna Bank");
					message.setContent(bodyText.toString(), "text/html; charset=utf-8");
					// send Message
					Transport.send(message);
					System.out.println("Message sent successfully");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
		thread.start();

	}
	
	
	

}
