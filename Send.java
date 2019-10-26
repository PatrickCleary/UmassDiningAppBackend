package main;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Send {

	public static String send(String response, String username, String password, String sendAddress) throws MessagingException {
		
		Properties props = new Properties();
		props.put("mail.smtp.user", "berkalertsumass");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");
	    Session session = Session.getInstance(props);
		
		
		Message responseMSG = new MimeMessage(session);
		  responseMSG.setFrom(new InternetAddress("berkalertsumass@gmail.com"));
		  responseMSG.setText(response);
		 try {

			 Transport.send(responseMSG, InternetAddress.parse(sendAddress), "berkalertsumass@gmail.com", "Samp7@88");
			 System.out.println("Message sent successfully");
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		
		return "Sent Successfully";
	}
	
}
