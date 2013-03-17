package fellesp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Properties;

public class AppointmentTest {
	/**
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	
	public static void main(String[] args) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("./Properties.properties"));
			AppointmentFactory fac = new AppointmentFactory(prop);
			// Tester utføres her
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
