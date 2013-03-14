package fellesp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


public class UserFactory {
DBConnection db;
	
	
	public UserFactory(Properties properties) throws ClassNotFoundException, SQLException
	{
		 db=new DBConnection(properties);
	}
	
	public  User  createUser(String userName, String password) throws ClassNotFoundException, SQLException
	{
		User e=new User(userName, password);
		String query=String.format("insert into User " +
				"(username,password) values ('%s', %d)", userName, password); 
		db.initialize();
		db.makeSingleUpdate(query);
		db.close();
		
		return e;
	}
	
	public User getUser(String userName) throws ClassNotFoundException, SQLException
	{
		String query=String.format("SELECT password FROM User WHERE username = %s",userName);
		db.initialize();
		ResultSet rs=db.makeSingleQuery(query);
		String password=null;
			password=rs.getString(1);
		User e=new User(userName,password);
		rs.close();
		db.close();
		
		return e;
	
		
	}
	
	public void deleteUser(String userName) throws ClassNotFoundException, SQLException
	{
		String query = String.format("DELETE FROM User WHERE username=%s",userName);
		db.initialize();
		db.makeSingleUpdate(query);
		db.close();
	}
	public void updateUser(String userName, String newPassword) throws ClassNotFoundException, SQLException
	{
		String update = String.format("UPDATE User" +
				" SET password = %s",newPassword +
				" WHERE username = %s", userName);
		db.initialize();
		db.makeSingleUpdate(update);
		db.close();
	}

}
