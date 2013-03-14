package fellesp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class InviteFactory {
  
	DBConnection db;
	
	
	public InviteFactory(Properties properties) throws ClassNotFoundException, SQLException
	{
		 db=new DBConnection(properties);
	}
	
	public  Invite  createInvite(String username, int aID) throws ClassNotFoundException, SQLException
	{
		Invite e=new Invite(username, aID);
		String query=String.format("insert into Invite " +
				"(username,appointmentID) values ('%s', '%d')", username, aID); 
		db.initialize();
		db.makeSingleUpdate(query);
		db.close();
		
		return e;
	}
	
	
	
	public int getInviteResponse(String username, int aID) throws ClassNotFoundException, SQLException
	{
		String query=String.format("SELECT response FROM Invite WHERE username = '%s' AND appointmentID = '%d'",username, aID);
		db.initialize();
		ResultSet rs=db.makeSingleQuery(query);

		
		Invite e=new Invite(username,aID);
		rs.close();
		db.close();
		
		return e.getResponse();
	
		
	}
	
	public void deleteInvite(String username) throws ClassNotFoundException, SQLException
	{
		String query = String.format("DELETE FROM Invite WHERE username = '%s'",username);
		db.initialize();
		db.makeSingleUpdate(query);
		db.close();
	}
	
	
	public void updateInviteResponse(String username, int newResponse, int aID) throws ClassNotFoundException, SQLException
	{
		String update = String.format("UPDATE Invite" +
				" SET response = '%d'",newResponse +
				" WHERE username = '%s'", username + 
				" AND appointmentID = '%d'", aID);
		db.initialize();
		db.makeSingleUpdate(update);
		db.close();
	}

}
