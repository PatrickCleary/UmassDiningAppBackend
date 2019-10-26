package main;

import java.util.Properties;

import javax.mail.*;

public class ReceiveEmail{
	
	public static void check(String host, String storeType, String user, String password) {
		
		try {
			Properties props = new Properties();
			props.put("mail.pop3.host", host);
			props.put("mail.pop3.port", "995");
			props.put("mail.pop3.starttls.enable", "true");
			
			//Session emailSession = Session.getDefaultInstance(props);
			
			
			Session emailSession = Session.getInstance(props,
			         new javax.mail.Authenticator() {
			            protected PasswordAuthentication getPasswordAuthentication() {
			               return new PasswordAuthentication(
			                  "berkalertsumass@gmail.com", "Samp7@88");
			            }
			         });
			
			Store store = emailSession.getStore("pop3s");
			
			store.connect(host, user, password);
			
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);
			
			Message[] messages = emailFolder.getMessages();
			System.out.println("Messages length: " + messages.length);
			int i =0;
			for(Message x : messages) {//check this
		
				i++;
				System.out.println("---------------------------------");
		         System.out.println("Email Number " + i);
		         System.out.println("Subject: " + x.getSubject());
		         System.out.println("From: " + x.getFrom()[0]);
		         System.out.println("Text: " + x.getContent().toString());
				
				
			}
			emailFolder.close(false);
			store.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String [] args) {
		
		String host = "pop.gmail.com";
		String mailStoreType = "pop3";
		String user = "berkalertsumass@gmail.com";
		String pass = "Samp7@88";
		check(host, mailStoreType, user, pass);
	}
	
}