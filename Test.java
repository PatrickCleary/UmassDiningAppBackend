
		package main;

		import java.io.BufferedReader;
		import java.io.DataOutputStream;
		import java.io.IOException;
		import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
		import java.net.MalformedURLException;
		import java.net.ProtocolException;
		import java.net.URL;
		import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

		public class Test {
			
		
			public static void main(String [] args) {
				
				
					try {
						String driver = "com.mysql.jdbc.Driver";
						String url = "jdbc:mysql://213.190.6.43:3306/u293721445_menus";
						String username = "u293721445_user";
						String password = "password";
						Class.forName(driver);
						
						Connection conn = DriverManager.getConnection(url, username, password);
						System.out.println("connected");
						
					

							PreparedStatement st = conn.prepareStatement(
									"INSERT menu(food, hall, meal, category) VALUES(\'tomatos\', \'berk\', \'dinner\', \'entrees\') ON DUPLICATE KEY UPDATE numAppearances = numAppearances+1;"
									);
							st.executeUpdate();
							System.out.println("Added: to menus.");
						
						
					}catch(Exception e) {
						e.printStackTrace();
						
					}
					
					
				}
			
			
		}