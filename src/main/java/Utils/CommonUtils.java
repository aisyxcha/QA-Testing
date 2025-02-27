package Utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;


public class CommonUtils {

	public static String generateBrandNewEmail() {
		return new Date().toString().replaceAll("\\s", "").replaceAll("\\:", "") + "@email.com";
	}

	public static Properties loadProperties() {

		Properties prop = new Properties();
		try {
			FileReader fr = new FileReader(
					System.getProperty("user.dir") + "\\src\\test\\resources\\projectdata.properties");
			prop.load(fr);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;

	}
	
	public static void setProperties(String propertyKey,String propertyValue,Properties prop) {
		
		prop.setProperty(propertyKey,propertyValue);
		FileWriter fr = null;
		try {
			fr = new FileWriter(System.getProperty("user.dir")+"\\src\\test\\resources\\projectdata.properties");
			prop.store(fr,"");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}



	public static String validEmailRandomizeGenerator() {

		String[] validEmails = { "aisyah1@gmail.com", "aisyah2@gmail.com", "aisyah3@gmail.com",
				"aisyah4@gmail.com", "aisyah5@gmail.com", "aisyah6@gmail.com",
				"aisyah7@gmail.com", "aisyah8@gmail.com"};
		
		Random random = new Random();
		int index = random.nextInt(validEmails.length);

		return validEmails[index];
	}

}