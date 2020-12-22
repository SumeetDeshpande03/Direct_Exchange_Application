package edu.sjsu.cmpe275.Term_Project.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailOfferService {
	
	public String sendEmail(String fromUser, String toUser, String message) throws AddressException, MessagingException {
			
		   Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("teamprojectcmpe275@gmail.com", "AbCd@1234");
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress(fromUser, false));

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toUser));
		   msg.setSubject("Message from " + fromUser);
		   msg.setContent(message, "text/html");
		   msg.setSentDate(new Date());

		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setContent(message, "text/html");

		   Transport.send(msg);  
		   return "Email sent successfully";
		   
	}
	
}
