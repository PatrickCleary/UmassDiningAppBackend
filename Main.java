package main;

import java.util.Properties;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;


public class Main {

	public static void main(String[] args) {
		
	String username = "berkalertsumass";	//might need @gmail.com
	String password = "Samp7@88";
	String host = "pop.gmail.com";
	Properties props = new Properties();
	
	
	props.put("mail.store.protocol", "pop3");
	props.put("mail.pop3.host", host);
	props.put("mail.pop3.port", "995");
	props.put("mail.pop3.starttls.enable", "true");

	
	try{
		
	
		Wait.wait(props, username, password, host);
		
		
	
	}catch (Exception e) {
		e.printStackTrace();
	}
	
	
}
}
