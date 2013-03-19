package fellesp;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class InviteFactory {

	private static DBConnection db;


	public InviteFactory(Properties properties) throws ClassNotFoundException, SQLException
	{
		db=new DBConnection(properties);
	}

	public static Invite  createInvite(String username, int aID) throws ClassNotFoundException, SQLException
	{
		Invite e=new Invite(username, aID);
		String query=String.format("insert into Invite " +
				"(response,username,appointmentID) values (1,'%s', '%d')", username, aID); 
		db.initialize();
		db.makeSingleUpdate(query);
		db.close();

		return e;
	}

	public static Invite  createAppointmentInvite(String username, int aID) throws ClassNotFoundException, SQLException
	{
		Invite e=new Invite(username, aID);
		String query=String.format("insert into Invite " +
				"(response,username,appointmentID) values (2,'%s', '%d')", username, aID); 
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
		int res;

		if (rs.next()) {
			res = rs.getInt(1);
		}
		else {
			return (Integer) null;
		}

		rs.close();
		db.close();

		return res;
	}

	public static ArrayList<String> getDeclinedUsers(int aID) throws ClassNotFoundException, SQLException{
		String query = String.format("SELECT username FROM Invite WHERE appointmentID = '%s' AND response = '3';", aID);
		db.initialize();
		ResultSet rs=db.makeSingleQuery(query);
		ArrayList<String> list = new ArrayList<String>();
		while (rs.next()){
			list.add(rs.getString(1));
		}
		rs.close();
		db.close();


		return list;
	}

	public static ArrayList<Integer> getInviteApointmentID(String username) throws ClassNotFoundException, SQLException{
		String query = String.format("SELECT Invite.appointmentID FROM Invite, Appointment WHERE username = '%s' AND response = '2' AND Invite.appointmentID = Appointment.appointmentID Group By date, starttime;",username);
		db.initialize();
		ResultSet rs=db.makeSingleQuery(query);
		ArrayList<Integer> list = new ArrayList<Integer>();
		while (rs.next()){
			list.add(rs.getInt(1));
		}
		rs.close();
		db.close();

		return list;
	}

	public static ArrayList<Integer> getAcceptedInvitesForThisUse(String username) throws ClassNotFoundException, SQLException{
		ArrayList<Integer> result = new ArrayList<Integer>();
		String query = String.format("SELECT appointmentID FROM Invite WHERE username='%s' AND response=2",username);
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		rs.beforeFirst();
		while (rs.next()){
			result.add(rs.getInt(1));
		}
		
		return result;
	}
	
	public static void deleteInviteUser(String username) throws ClassNotFoundException, SQLException
	{
		String query = String.format("DELETE FROM Invite WHERE username = '%s'",username);
		db.initialize();
		db.makeSingleUpdate(query);
		db.close();
	}

	public static void deleteInviteAppointment(int aID) throws ClassNotFoundException, SQLException
	{
		String query = String.format("DELETE FROM Invite WHERE appointmentID = '%d'",aID);
		db.initialize();
		db.makeSingleUpdate(query);
		db.close();
	}

	public static void deleteInviteAppointmentWhereCancelled(int aID,String username) throws ClassNotFoundException, SQLException{
		String query = String.format("DELETE FROM Invite WHERE appointmentID='%d' AND username='%s'",aID,username);
		db.initialize();
		db.makeSingleUpdate(query);
		db.close();
	}


	public static void updateInviteResponse(String username, int newResponse, int aID) throws ClassNotFoundException, SQLException
	{
		String update = String.format("UPDATE Invite" + " SET response = '%d'" + " WHERE username = '%s'" + " AND appointmentID =' %d'", newResponse, username, aID);
		db.initialize();
		db.makeSingleUpdate(update);
		db.close();
	}

	public static ArrayList<String> getParticipants(int aID) throws ClassNotFoundException, SQLException{
		ArrayList<String> list = new ArrayList<String>();
		String query = String.format("SELECT username FROM Invite WHERE appointmentID = '%d'",aID);
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		while (rs.next()){
			list.add(rs.getString("username"));
		}

		db.close();

		return list;
	}

	public static ArrayList<Integer> findCancelledAppointments(String username) throws ClassNotFoundException, SQLException{
		ArrayList<Integer> invitelist = new ArrayList<Integer>();
		ArrayList<Integer> appointmentlist = new ArrayList<Integer>();
		ArrayList<Integer> deletedaID = new ArrayList<Integer>();


		String query = String.format("SELECT appointmentID FROM Invite WHERE username='%s'",username);
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		rs.beforeFirst();
		while (rs.next()){
			invitelist.add(rs.getInt(1));
		}

		String query2 = String.format("SELECT appointmentID FROM Appointment");
		ResultSet rs2 = db.makeSingleQuery(query2);
		rs2.beforeFirst();
		while (rs2.next()){
			appointmentlist.add(rs2.getInt(1));
		}

		
		for (int idFromInvite: invitelist){
			if (!appointmentlist.contains(idFromInvite)){
				deletedaID.add(idFromInvite);
			}
		}

		db.close();
		return deletedaID;
	}


}

