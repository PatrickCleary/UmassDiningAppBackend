package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;    

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//javac -cp "C:\Users\Patrick\Programming\DiningApp\mySQLconnector\mysql-connector-java-5.1.47;C:\Users\Patrick\Programming\ReadMenu_lib\jsoup-1.11.2.jar" ReadMenu.java SQLTest.java
public class ReadMenu {
	static int z = 0;
	

	public static void main(String[] args) throws IOException, MessagingException {
		
		//connect to database
		
		Connection conn = SQLTest.getConnection();
		   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");  
		   LocalDateTime now = LocalDateTime.now();  
		   LocalDateTime lastWeek  = now.minus(7,ChronoUnit.DAYS);
		   
		   int DOW = now.getDayOfWeek().getValue();
		   String date = dtf.format(now);
		   String lastWeekStr = dtf.format(lastWeek);
		   SQLTest.CreateTodaysMenu(conn, date);
		   SQLTest.deleteLastWeeksMenu(conn, lastWeekStr);
		   
		// get menu from each hall.
		   int additions = 0;
		 //  additions += getMenuItems("https://umassdining.com/locations-menus/", "Berkshire", conn, date, DOW);
		//   additions += getMenuItems("https://umassdining.com/locations-menus/", "Franklin", conn, date, DOW);
		//   additions += getMenuItems("https://umassdining.com/locations-menus/", "Worcester", conn, date, DOW);
		   additions += getMenuItems("https://umassdining.com/locations-menus/", "Hampshire", conn, date, DOW);
		System.out.println("Complete. added: " + additions);
		Send.send("added " + additions + " entries to database: todaysMenu" + date, "berkalertsumass", "Samp7@88", "6178660768@vtext.com");

	}

	// should be passed simple text where each line is a LN option.
	public static void insertLateNight(String LNMenu, String date, int DOW) {
		// connect to database.
		Connection conn = SQLTest.getConnection();

		// make calendars for each dining hall and set date as today.
		Calendar calBerk = Calendar.getInstance();
		Calendar calWos = Calendar.getInstance();
		Date newDate = new Date();
		calBerk.setTime(newDate);
		calWos.setTime(newDate);
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

		// cut string into lines
		String[] lines = LNMenu.split("\n");
		String hall = "none";

		// iterate through lines and add to latenight database and menu.
		for (int x = 1; x < lines.length; x++) {

			lines[x] = lines[x].trim();

			if (lines[x].equals("berk")) {
				hall = "berk";
				continue;

			} else if (lines[x].equals("worcester")) {
				hall = "worcester";
				continue;
			} else {

				if (hall.equals("berk")) {
					calBerk.add(Calendar.DATE, 1);
					newDate = calBerk.getTime();
					SQLTest.insertLN(conn, "'" + lines[x] + "'", "'" + hall + "'", "'" + ft.format(newDate) + "'");
					SQLTest.insertFood(conn, "'" + lines[x] + "'", "'berkshire'", "'Late Night'", "'Special'", date, DOW);
				} else if (hall.equals("worcester")) {
					calWos.add(Calendar.DATE, 1);
					newDate = calWos.getTime();
					SQLTest.insertLN(conn, "'" + lines[x] + "'", "'" + hall + "'", "'" + ft.format(newDate) + "'");
					SQLTest.insertFood(conn, "'" + lines[x] + "'", "'worcester'", "'Late Night'", "'Special'", date, DOW);

				}

			}
		} // for
	}// insertLN

	// get menu from given URL, including nutrition, allergen and diet information.
	// Then add to databases.
	public static int getMenuItems(String url, String hall, Connection conn, String date, int DOW) throws IOException {
		
		int numAdditions = 0;

		String[] dietArr = { "vegetarian", "local", "sustainable", "whole grain", "halal", "antibiotic free", "vegan" };
		String[] allergensArr = { "corn", "milk", "eggs", "gluten", "soy", "sesame", "tree nuts", "fish", "shellfish",
				"peanuts" };
		String[] nutritionInfoArr = { "data-calories", "data-calories-from-fat", "data-total-fat", "data-sat-fat ",
				"data-trans-fat", "data-cholesterol", "data-sodium", "data-total-carb", "data-dietary-fiber",
				"data-sugars", "data-protein" };

		Document doc = Jsoup.connect(url + hall + "/menu").get();

		int mealInt;

		// connect to database
		for (mealInt = 0; mealInt < 5; mealInt++) {

			// name used by website to identify each meal.
			String mealStr;

			String fileName;

			switch (mealInt) {

			case (0):
				mealStr = "breakfast_fp";
				fileName = "Breakfast";
				break;
			case (1):
				mealStr = "lunch_fp";
				fileName = "Lunch";
				break;
			case (2):
				mealStr = "dinner_fp";
				fileName = "Dinner";
				break;
			case (3):
				mealStr = "grabngo";
				fileName = "Grab And Go";
				break;
			case (4):
				mealStr = "latenight_fp";
				fileName = "Late Night";
				break;
			default:
				fileName = "";
				mealStr = "";
			}

			// get each food item
			Elements foods = doc.select("." + mealStr + " > *");

			// set default category
			String categoryName = "none";

			// for each food
			for (Element Food : foods) {
				// when element is a categroy head:
				if (Food.className().equals("menu_category_name"))
					categoryName = Food.text().replace("'", "\\'");

				// otherwise element is a menu item:
				else {
					numAdditions++;
					// to deal with food names containing apostrophes:
					String foodText = "'" + Food.text().replace("'", "\\'") + "'";

					// parse info from each food item.
					Integer[] AllergyBool = createBoolArray(Food.select("a").attr("data-allergens"), allergensArr);
					Integer[] dietBool = createBoolArray(Food.select("a").attr("data-clean-diet-str"), dietArr);
					String servSize = "'" + Food.select("a").attr("data-serving-size") + "'";
					double[] nutritionInfoBool = parseNutritionInfo(Food, nutritionInfoArr);

					// insert menu and nutrition info.
					if(SQLTest.insertFood(conn, foodText, hall, fileName, categoryName, date, DOW)) numAdditions++;
					SQLTest.insertFoodFull(conn, foodText, servSize, AllergyBool, dietBool, nutritionInfoBool);

				}

			} // for each food

		} // for 5 each meals

		return numAdditions;

	}

	
	public static double[] parseNutritionInfo(Element food, String[] nutritionInfoArr) {
		
		//parse nut info and convert to doubles.
		String[] returnArr = new String[nutritionInfoArr.length];
		double[] returnNum = new double[nutritionInfoArr.length];
		for (int x = 0; x < nutritionInfoArr.length; x++) {

			returnArr[x] = food.select("a").attr(nutritionInfoArr[x]);
			if (returnArr[x].equals(""))
				returnArr[x] = "0.0";
			returnNum[x] = Double.parseDouble(returnArr[x].replaceAll("[^. & ^\\d]", ""));

		}

		return returnNum;

	}

	//create int arrays containing allergy and diet info.
	public static Integer[] createBoolArray(String dietInfo, String[] array) {

		Integer[] returnArr = new Integer[array.length];

		for (int x = 0; x < array.length; x++) {
			if (Pattern.compile(Pattern.quote(array[x]), Pattern.CASE_INSENSITIVE).matcher(dietInfo).find())
				returnArr[x] = 1;
			else
				returnArr[x] = 0;

		}

		return returnArr;
	}
}
