package main;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ReplyMessage {

	public static void main(String [] args) {
		
		
		Properties properties = new Properties();
	      properties.put("mail.smtp.user", "berkalertsumass");
	      properties.put("mail.smtp.auth", "true");
	      properties.put("mail.smtp.starttls.enable", "true");
	      properties.put("mail.smtp.host", "smtp.gmail.com");
	      properties.put("mail.smtp.port", "587");
	      Session session = Session.getInstance(properties);
	
	      try {
	    	  
	    	  Store store = session.getStore("pop3s");
	    	  store.connect("pop.gmail.com", "berkalertsumass@gmail.com", "Samp7@88");
	    	  
	    	  Folder folder = store.getFolder("INBOX");
	    	  if(!folder.exists()) {
	    		  System.out.println("Folder not found");
	    		  System.exit(0);
	    	  }
	    	  folder.open(Folder.READ_ONLY);
	    	
	    	  Message[] messages = folder.getMessages();
	    	  if(messages.length>0) {
	    		  
	    		  
	    		  for(int i = 0; i<messages.length; i++) {
	    			  Message message = messages[i];
	    			  
	    				  Message response = new MimeMessage(session);
	    				  response = (MimeMessage) message.reply(false);
	    				  response.setReplyTo(message.getFrom());
	    				  response.setFrom(new InternetAddress("berkalertsumass@gmail.com"));
	    				  response.setText("hello world");
	    				 try {
	    					 Transport.send(response, message.getFrom(), "berkalertsumass@gmail.com", "Samp7@88");
	    				 }catch(Exception e) {
	    					 System.out.println("Here");
	    					 e.printStackTrace();
	    				 }
	    				 System.out.println("MEssage sent successfully");
	    			  
	    		  }

 				 folder.close(false);
 				 store.close();
 				  
	    	  
	      }else {
	    	  System.out.println("No messages");
	    	  
	      }
	
	
	}catch(Exception e) {
		e.printStackTrace();
	}
	
	}
}
