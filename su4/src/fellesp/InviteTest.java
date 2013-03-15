package fellesp;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class InviteTest {

	public static void main(String[] args){
		Properties prop = new Properties();
		
		try {
			prop.load(new FileInputStream("./Properties.properties"));
			InviteFactory factory = new InviteFactory(prop);
			// Put test calls here
			factory.createInvite("siljemol",2);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException ex){
			ex.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
}
