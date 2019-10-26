package main;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;

public class Read {

	
	public static String read(Message message) throws Exception {
	
			System.out.println("FROM: " + Wait.getAddress(message));
			String read = parseMSG(message);
			
			return read;	
			
			
			
			}
	
	
	
	public static String parseMSG(Part p) throws Exception {
		
				
		      String returnStr = "";
		      if(p.isMimeType("multipart/*")) {
		    	 Multipart mp = ((Multipart) p.getContent());
		    	 Part z = mp.getBodyPart(0);
		    	 if(z.isMimeType("text/plain")) {
		    		 returnStr = (String) z.getContent();
		    		 
		    	 }else {
		    		 returnStr = "Invalid MSG";
		    	 }
		      }else {
		    	  returnStr = "Invalid MSG";
		      }
		      
		return returnStr;	
		}
	
}
