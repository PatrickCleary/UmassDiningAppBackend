package main;

import java.sql.Connection;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;



public class Wait {

	
	
	public static int wait(Properties Props, String username, String password, String host) {
		
		Session session = Session.getDefaultInstance(Props);
		try {
			
				Store store = session.getStore("pop3s");
				store.connect(host, username, password);
				
				Folder emailFolder = store.getFolder("INBOX");
				
			
				
				//while loop breaks if response = end.
				while(true) {
					emailFolder.open(Folder.READ_ONLY);
					TimeUnit.SECONDS.sleep(3);
					Message [] messages = emailFolder.getMessages();
					System.out.println("messages.length = " + messages.length);
					
					int numMSGS = messages.length;
					if(numMSGS>0) {
			
						String address = getAddress(messages[0]);
						
						Connection conn = SQLTest.getConnection();
						
						SQLTest.insertContact(conn, "'" + address + "'");
						
						String text = Read.read(messages[0]);
							
						String response = Respond.respond(text, address);
						if(response == "end")
							break;
						Send.send(response, username, password, address);
				}
					
					emailFolder.close(true);

				}
			
			
				store.close();				
				return 0;

		}  catch(Exception e){
				System.out.println(e.getMessage());
				return -1;
			}
		
	
		
	}

public static String getAddress(Message msg) {
	InternetAddress[] newAddress;
	try {
		newAddress = (InternetAddress[]) msg.getFrom();
		String num = newAddress[0].getAddress();
		
		//for some reason att doesn't work unless u change mms to txt
		if(num.endsWith("mms.att.net")) {
			num = num.substring(0, num.length()-11);
			num = num.concat("txt.att.net");
		}
			
		
		return num;
	} catch (MessagingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "number not found";
	}
	
}

}
