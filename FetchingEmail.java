package main;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;



public class FetchingEmail {

	
	public static void fetch(String pop3Host, String storeType, String user, String password) {
		
		try {
			Properties props = new Properties();
			props.put("mail.store.protocol", "pop3");
			props.put("mail.pop3.host", pop3Host);
			props.put("mail.pop3.port", "995");
			props.put("mail.pop3.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(props);
			
			Store store = emailSession.getStore("pop3s");
			store.connect(pop3Host, user, password);
			
			
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			Message [] messages = emailFolder.getMessages();
			System.out.println("messages.length = " + messages.length);
			
			
			 for (Message message: messages) {
		            System.out.println("---------------------------------");
		            writePart(message);
		            String line = reader.readLine();
		            if ("YES".equals(line)) {
		               message.writeTo(System.out);
		            } else if ("QUIT".equals(line)) {
		               break;
		            }
		         }
			 emailFolder.close(false);
			 store.close();
		}
		catch (NoSuchProviderException e) {
	         e.printStackTrace();
	      } catch (MessagingException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		
		
	}
	
	public static void main(String [] args) {
		
		String host = "pop.gmail.com";// change accordingly
	      String mailStoreType = "pop3";
	      String username = "berkalertsumass@gmail.com";// change accordingly
	      String password = "Samp7@88";// change accordingly

	      //Call method fetch
	      fetch(host, mailStoreType, username, password);

	}
	
	public static void writePart(Part p) throws Exception{
		if(p instanceof Message) 
			writeEnvelope((Message) p);
			
		System.out.println("---");
	      System.out.println("CONTENT-TYPE: " + p.getContentType());
	      
	      if(p.isMimeType("text/plain")) {
	    	  System.out.println("PLAIN TEXT MESSAGE");
	    	  System.out.println("---");
	    	  System.out.println((String) p.getContent());
	    	  
	      }
	      else if(p.isMimeType("multipart/*")) {
	    	  System.out.println("MULTIPART MSG");
	    	  System.out.println("---");
	    	  Multipart mp = ((Multipart) p.getContent());
	    	  for(int i = 0; i < mp.getCount(); i++) 
	    		  writePart(mp.getBodyPart(i));
	      }
	      else if(p.isMimeType("message/rfc822")){
	    	  System.out.println("NESTED MSG");
	    	  System.out.println("---");
	    	  writePart((Part) p.getContent());
	      }
	      else if(p.isMimeType("image/jpeg")) {
	    	  System.out.println("JPEG IMAGE");
	    	  System.out.println("---");
	    	 
	      }
	      else {
	    	  System.out.println("UNKOWN TYPE");
	    	  
	      }
		
	}

public static void writeEnvelope(Message m) throws Exception {
    System.out.println("This is the message envelope");
    System.out.println("---------------------------");
    Address[] a;

    // FROM
    if ((a = m.getFrom()) != null) {
       for (int j = 0; j < a.length; j++)
       System.out.println("FROM: " + a[j].toString());
    }

    // TO
    if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
       for (int j = 0; j < a.length; j++)
       System.out.println("TO: " + a[j].toString());
    }

    // SUBJECT
    if (m.getSubject() != null)
       System.out.println("SUBJECT: " + m.getSubject());

 }

}
