package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Respond {

	public static String respond(String text, String sender) throws IOException {
		Connection conn = SQLTest.getConnection();

		
		text = text.toLowerCase();	
		
		
		if(sender.equals("6178660768@vzwpix.com")) {
			String [] ln = text.split("\n");

			ln[0] = ln[0].trim();
			
		
		return "hello Patrick";
	}
		
		else if(text.equals("my favorites")){
			
			
			
			try {
				
				Statement st = conn.createStatement();
				String favorites = "SELECT * FROM favorites WHERE number = '" + sender + "';";
				
				
				System.out.println(favorites);
				ResultSet rs = st.executeQuery(favorites);
				
				
				
				while(rs.next()) {
	            String food = rs.getString("food");
	            String hall = rs.getString("hall");
	            String meal = rs.getString("meal");
	            String category = rs.getString("category");
	            
	           return (food + " - " + hall + " - " + meal + " - " + category + " station");
				
				}
			}catch(SQLException e){
			e.printStackTrace();
			
			}
			return "no favorites found";
			
			
		}
		
		
		
		else if(text.contains("berk")){
			
			String foodTXT = text.substring(5);
			String results = "";
			
			
			Statement st;
			try {
				st = conn.createStatement();
				String query = "SELECT  * FROM menus WHERE hall = 'berkshire' AND (food LIKE '" + foodTXT + "%' OR food LIKE  '%" + foodTXT + "' OR food LIKE '%" + foodTXT + "%');";
				System.out.println(query);
				ResultSet rs  = st.executeQuery(query);
				while(rs.next()) {
					String food = rs.getString("food");
					String meal = rs.getString("meal");
					String category = rs.getString("category");
					results = results.concat(food + " - " + meal + " - " + category  + "\n");
					
				}
				
				//return results if found. if not return nothing found.
				if(!results.equals(""))
				return results;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "nothing found";
			
			
			
			
		}
		
		else {
			return "invalid question";
		}
		
		
	}
	
	public static String Search(String searchStr) {
		String hall = "all";
		String [] searchStrArr = searchStr.split(" ", 1);
		String [] diningHalls = {"berk","hamp", "hampshire", "berkshire", "worcester", "frank", "franklin"};
		
		for(int hallNum =0; hallNum<diningHalls.length; hallNum++) {
			if(searchStrArr[0].equals(diningHalls[hallNum]));
				hall = diningHalls[hallNum];	
		}
		
		
		
			
		
		

		
		
		return "nothing found";
	}
	
}
