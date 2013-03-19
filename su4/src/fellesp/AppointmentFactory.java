package fellesp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import fellesp.Appointment;
import fellesp.DBConnection;

public class AppointmentFactory {
	

	private static DBConnection db;

	
	public AppointmentFactory(Properties properties) throws ClassNotFoundException, SQLException
	{
		 db = new DBConnection(properties);
	}
	

	public static Appointment createAppointment(String date, String startTime, String endTime, String place,
			String description, boolean meeting, String owner) throws ClassNotFoundException, SQLException{

		/*java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		java.sql.Time sqlStartTime = new java.sql.Time(startTime.getTime());
		java.sql.Time sqlEndTime = new java.sql.Time(endTime.getTime());
		*/
		
		int id = getNextId();
		
		int meetingInt = 0;
		if (meeting) {
			meetingInt = 1;
		}
		/*if (endTime.before(startTime)){
			return null;
		}*/

		Appointment a= new Appointment(id, date, startTime, endTime, place, description, meeting, owner);
		String query=String.format("INSERT INTO Appointment " 
				+ "(appointmentID, date, startTime, endTime, place, description, meeting, owner) VALUES ('" 
				+ id + "','" + date + "','" + startTime +"','" + endTime +"','" + place + "','" + description + "','" 
				+ meetingInt + "','" + owner + "');" );

		db.initialize();
		db.makeSingleUpdate(query);
		db.close();
		return a;
	}
	

	public static Appointment getAppointment(int id) throws SQLException, ClassNotFoundException{
		String query = String.format("SELECT * FROM Appointment WHERE appointmentID =" + id + ";" );

		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		
		String date = null;
		String startTime = null;
		String endTime = null;
		String place = null;
		String description = null;
		boolean meeting = true;
		String owner = null;
		
		while (rs.next()){
			date = rs.getString(2);
			startTime = rs.getString(3);
			endTime = rs.getString(4);
			place = rs.getString(5);
			description = rs.getString(6);
			meeting = rs.getBoolean(8);
			owner = rs.getString(9);
		}
		
		Appointment a= new Appointment(id, date, startTime, endTime, place, description, meeting, owner);
		db.close();
		
		return a;
	}
	
	public static boolean isMeetingOwner(String username, int aID) throws ClassNotFoundException, SQLException{
		String query =String.format("SELECT owner FROM Appointment WHERE appointmentID='%s'",aID);
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		rs.beforeFirst();
		rs.next();
		String owner = rs.getString(1);
		if (owner.equals(username)){
			db.close();
			return true;
		}
		db.close();
		return false;
	}

	public static ArrayList<Integer> getMeetingWhereOwner(String username) throws ClassNotFoundException, SQLException{
		ArrayList<Integer> result = new ArrayList<Integer>();
		String query = String.format("SELECT appointmentID From Appointment WHERE owner='%s'", username);
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		rs.beforeFirst();
		while (rs.next()){
			result.add(rs.getInt(1));
		}
		
		return result;
	}

	public static int getNextId() throws ClassNotFoundException, SQLException{
		String query= "SELECT MAX(appointmentID) FROM Appointment ;";

		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		int nextId = 0;
		while( rs.next() ){
			nextId = Math.max(nextId, rs.getInt(1));
		}
		rs.close();
		db.close();
		nextId = nextId + 1;
		
		return nextId;
	}
	
	public static void deleteAppointment(int aID) throws ClassNotFoundException, SQLException{
		String query =String.format("DELETE FROM Appointment WHERE appointmentID ='" + aID + "';");
		db.initialize();
		db.makeSingleUpdate(query);
		db.close();
	}
	
	public static boolean isMeeting(int aID) throws ClassNotFoundException, SQLException{
		String query = String.format("SELECT meeting FROM Appointment WHERE appointmentID='%d';",aID);
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		rs.beforeFirst();
		rs.next();
		boolean res = rs.getBoolean(1);
		db.close();
		return res;
	}

	public static void updateAppointmentFromQuery(int id, String query) throws ClassNotFoundException, SQLException{
		db.initialize();
		db.makeSingleUpdate(query);
		db.close();
	}
	
	public static void updateAppointmentDate(int id, String date) 
			throws ClassNotFoundException, SQLException{
		String query = String.format("UPDATE Appointment SET date ='" + date + "'WHERE appointmentID = '%d'",id);
		updateAppointmentFromQuery(id,query);
	}
	
	public static void updateAppointmentStartTime(int id, String startTime) 
			throws ClassNotFoundException, SQLException{
		String query = String.format("UPDATE Appointment SET startTime ='" + startTime + "'WHERE appointmentID = '%d'", id);
		updateAppointmentFromQuery(id,query);
	}
	
	public static void updateAppointmentEndTime(int id, String endTime) 
			throws ClassNotFoundException, SQLException{
		String query = String.format("UPDATE Appointment SET endTime ='" + endTime + "'WHERE appointmentID = '%d'", id);
		updateAppointmentFromQuery(id,query);
	}
	
	public static void updateAppointmentTime(int id, String startTime, String endTime) 
			throws ClassNotFoundException, SQLException{
		updateAppointmentStartTime(id, startTime);
		updateAppointmentEndTime(id, endTime);
	}
	
	public static void updateAppointmentPlace(int id, String place) 
			throws ClassNotFoundException, SQLException{
		String query = String.format("UPDATE Appointment SET place = '%s' WHERE appointmentID = '%d'",place,id);
		updateAppointmentFromQuery(id,query);
	}
	
	public static void updateAppointmentDescription(int id, String description) 
			throws ClassNotFoundException, SQLException{
		String query = String.format("UPDATE Appointment SET description = '%s' WHERE appointmentID = '%d'",description,id);
		updateAppointmentFromQuery(id,query);
	}
	
	public static void updateAppointmentMeeting(int id, boolean meeting) 
			throws ClassNotFoundException, SQLException{
		int meetingInt = 0;
		if (meeting) {
			meetingInt = 1;
		}
		String query = String.format("UPDATE Appointment SET meeting = '%d'  WHERE appointmentID = '%d'",meetingInt,id);
		updateAppointmentFromQuery(id,query);
	}
	
	// Har ikke update for owner fordi denne skal ikke kunne endres!
	
	public static ArrayList<String> availableRooms(String date, String starttime, String endtime) throws ClassNotFoundException, SQLException {

		
		String query = String.format("Select roomnumber From MeetingRoom Where roomnumber NOT IN (Select place From Appointment Where date = '" + date + "' and ((starttime >= '" + starttime + "' and starttime <= '" + endtime + "') or (endtime >= '" + starttime + "' and endtime <= '" + endtime + "') or (starttime < '" + starttime + "' and endtime > '" + endtime + "')))");

		ArrayList<String> availableRooms = new ArrayList<String>();
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		
		while(rs.next()) {
			availableRooms.add(rs.getString("roomnumber"));
		}
		
		for (int i = 0; i<= availableRooms.size() - 1;i++) {
			System.out.println(availableRooms.get(i));
		}
		
		
		rs.close();
		db.close();
		
		
		return availableRooms;
		
	}
	
	public static void addAlarm(int id, String alarmtime) throws ClassNotFoundException, SQLException{
		String query = String.format("UPDATE Appointment SET alarm = '" + alarmtime + "' WHERE appointmentID = '" + id +"'");
		db.initialize();
		db.makeSingleUpdate(query);
		db.close();
	}
	
}

