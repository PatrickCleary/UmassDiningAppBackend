package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class SQLTest {
	static Calendar calendar = Calendar.getInstance();
	static int dayOfWeek = calendar.DAY_OF_WEEK;
	public static void insertContact(Connection conn, String num) {
		
		try {
			
			PreparedStatement st = conn.prepareStatement("INSERT contacts VALUES("+ num + ");");//maybe no semicolon
			st.executeUpdate();}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void deleteLastWeeksMenu(Connection conn, String lw) {
		
try {
		PreparedStatement dwa = conn.prepareStatement("DROP TABLE todaysMenu" +lw + ";");
		System.out.println(dwa);
		dwa.executeUpdate();
}catch(SQLException e) {
	e.printStackTrace();
}
	
	
	
}
	public static void CreateTodaysMenu(Connection conn, String date) {
		try {
		PreparedStatement st = conn.prepareStatement("" + 
				"CREATE TABLE todaysMenu"+date+"("+
				"    food VARCHAR(60) NOT NULL CHECK (food <> ''),"  +
				"    hall VARCHAR(10),"  +
				"    meal VARCHAR(12),"  +
				"    category VARCHAR(30),"+  
				"    PRIMARY KEY(food, hall, meal, category)"  +
				");");
		System.out.println(st);
		st.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void insertLN(Connection conn, String food, String hall, String date) {
		try {
			System.out.println("Insert LN Running");
			PreparedStatement st = conn.prepareStatement("INSERT lateNight(food, hall, date) VALUES("  + food  + ", " + hall + ", "  +  date + ")");
			System.out.println(st);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block

				e.printStackTrace();
		}
		
	}
	
	
	public static void insertFoodFull(Connection conn, String food, String servSize, Integer[] allergyBool, Integer[] dietBool, double[] nutInfo) {
		try {
			PreparedStatement st = conn.prepareStatement("INSERT nutritionInfo VALUES("  + 
		food  + ", " + 
		allergyBool[0] + ", " +
		allergyBool[1] + ", " +
		allergyBool[2] + ", " + 
		allergyBool[3] + ", " +
		allergyBool[4] + ", " +
		allergyBool[5] + ", " +
		allergyBool[6] + ", " +
		allergyBool[7] + ", " +
		allergyBool[8] + ", " +
		allergyBool[9] + ", " +
		
		dietBool[0] + ", " +
		dietBool[1] + ", " +
		dietBool[2] + ", " +
		dietBool[3] + ", " +
		dietBool[4] + ", " +
		dietBool[5] + ", " +
		dietBool[6] + ", " +
		
		servSize + ", " +
		
		nutInfo[0] +", " +
		nutInfo[1] +", " +
		nutInfo[2] +", " +
		nutInfo[3] +", " +
		nutInfo[4] +", " +
		nutInfo[5] +", " +
		nutInfo[6] +", " +
		nutInfo[7] +", " +
		nutInfo[8] +", " +
		nutInfo[9] +", " +
		nutInfo[10] +
		
					
					
					
					
					
					")");
			
			st.executeUpdate();
			System.out.println("Added: " + food + "to nutritionInfo.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			System.out.println(e.getMessage());
		}

		
	}
	public static void clearMenu(Connection conn) {
		
		try {
			PreparedStatement st = conn.prepareStatement("DELETE FROM todaysMenu;");
			st.executeUpdate();
			System.out.println("deleted todays menu");
			
			
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	public static boolean insertFood(Connection conn, String food, String hall, String meal, String category, String date, int DOW) {
		
		if(meal == "Lunch" && (DOW == 7 || DOW == 6)) {

			meal = "Brunch";
		}
		
		try {
			PreparedStatement st = conn.prepareStatement(
					"INSERT todaysMenu"+ date+ " (food, hall, meal, category) VALUES("  + 
					food  + ", " + "'" +  
					hall + "'" + ", "  +  "'" + 
					meal + "'"  + ", " + "'" +
					category + "'" +  ")"
					);
			st.executeUpdate();
			System.out.println("Added: " + food + "to todays menu.");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		
			System.out.println(e.getMessage());
			return false;
		}
		
		
		
	}

	public static Connection getConnection() {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://213.190.6.43:3306/u293721445_menus";
			String username = "u293721445_user";
			String password = "password";
			Class.forName(driver);
			
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("connected");
			return conn;
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}

}
