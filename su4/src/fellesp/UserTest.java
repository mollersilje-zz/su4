package fellesp;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class UserTest {
	
	
	
	public static void main(String[] args){
		String userName = "Sivert";
		String password = "Banankake";
		Properties prop = new Properties();
		
		try {
			prop.load(new FileInputStream("Properties.properties"));
		} catch (IOException ex){
			ex.printStackTrace();
		}
		
		try {
			UserFactory factory = new UserFactory(prop);
			User sivert = factory.createUser(userName, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

}
