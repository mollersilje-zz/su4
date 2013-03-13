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
	
	public User getUser(int id) throws ClassNotFoundException, SQLException
	{
		String query=String.format("Select username,password from User where id=%d",id);
		db.initialize();
		ResultSet rs=db.makeSingleQuery(query);
		String userName=null;
		String password=null;
		while(rs.next())
		{
			userName=rs.getString(1);
			password=rs.getString(2);
		}
		
		User e=new User(userName,password);
		rs.close();
		db.close();
		
		return e;
	
		
	}
	
	public void deleteUser(String userName) throws ClassNotFoundException, SQLException
	{
		String query = String.format("DELETE from User WHERE username=%s",userName);
		db.initialize();
		db.makeSingleUpdate(query);
		db.close();
	}
	public void updateUser()
	{
		;
	}

}
