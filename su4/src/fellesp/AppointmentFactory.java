package fellesp;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Properties;

public class AppointmentFactory {
	

	private static DBConnection db;

	
	public AppointmentFactory(Properties properties) throws ClassNotFoundException, SQLException
	{
		 db = new DBConnection(properties);
	}
	

	public static Appointment createAppointment(Date date, Time startTime, Time endTime,
			String description, boolean meeting, String owner) throws ClassNotFoundException, SQLException{

		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		java.sql.Time sqlStartTime = new java.sql.Time(startTime.getTime());
		java.sql.Time sqlEndTime = new java.sql.Time(endTime.getTime());
		
		int id = getNextId();
		int meetingInt = 0;
		if (meeting) {
			meetingInt = 1;
		}
		

		Appointment a= new Appointment(id, date, startTime, endTime, description, owner, meeting);
		String query=String.format("insert into Appointment " 
				+ "(appointmentID, date, startTime, endTime, description, meeting, owner) values ('" 
				+ id + "','" + sqlDate + "','" + sqlStartTime +"','" + sqlEndTime +"','" + description + "','" 
				+ meetingInt + "','" + owner + "');" );
		System.out.println(query);
		

		db.initialize();
		db.makeSingleUpdate(query);
		db.close();
		return a;
	}
	

	public static Appointment getAppointment(int id) throws SQLException, ClassNotFoundException{
		String query = String.format("select * from Appointmet where id =" + id + ";" );

		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		Date date = null;
		Time startTime = null;
		Time endTime = null;
		String description = null;
		boolean meeting = true; // "1" avtale og "2" er møte
		String owner = null;
		
		while (rs.next()){
			date = rs.getDate(1);
			startTime = rs.getTime(2);
			endTime = rs.getTime(3);
			description = rs.getString(4);
			meeting = rs.getBoolean(5);
			owner = rs.getString(6);
		}
		
		Appointment a= new Appointment(id, date, startTime, endTime, description, owner, meeting);
		db.close();
		
		return a;
	}
	
	public static int getNextId() throws ClassNotFoundException, SQLException{
		String query= "SELECT MAX(appointmentID) FROM Appointment ;";

		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		int nextId = 0;
		while( rs.next() ){
			nextId = rs.getInt(1);
		}
		rs.close();
		db.close();
		nextId = nextId + 1;
		
		return nextId;
	}
	
	public static void deleteAppointment(String owner) throws ClassNotFoundException, SQLException{
		String query =String.format("delete from Appintment where owner =" + owner + ";");
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		rs.close();
		db.close();
	}
	
	public static void updateAppointmentFromQuery(int id, String query) throws ClassNotFoundException, SQLException{
		db.initialize();
		db.makeSingleQuery(query);
		db.close();
	}
	
	public static void updateAppointmentDate(int id, Date date) 
			throws ClassNotFoundException, SQLException{
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		String query = String.format("UPDATE Appointment SET date =" + sqlDate + "WHERE appointmentID = %d",id);
		updateAppointmentFromQuery(id,query);
	}
	
	public static void updateAppointmentStartTime(int id, Time startTime) 
			throws ClassNotFoundException, SQLException{
		java.sql.Time sqlStartTime = new java.sql.Time(startTime.getTime());
		String query = String.format("UPDATE Appointment SET startTime =" + sqlStartTime + "WHERE appointmentID = %d", id);
		updateAppointmentFromQuery(id,query);
	}
	
	public static void updateAppointmentEndTime(int id, Time endTime) 
			throws ClassNotFoundException, SQLException{
		java.sql.Time sqlEndTime = new java.sql.Time(endTime.getTime());
		String query = String.format("UPDATE Appointment SET startTime =" + sqlEndTime + "WHERE appointmentID = %d", id);
		updateAppointmentFromQuery(id,query);
	}
	
	public static void updateAppointmentPlace(int id, String place) 
			throws ClassNotFoundException, SQLException{
		String query = String.format("UPDATE Appointment SET date = %s WHERE appointmentID = %d",place,id);
		updateAppointmentFromQuery(id,query);
	}
	
	public static void updateAppointmentDescription(int id, String description) 
			throws ClassNotFoundException, SQLException{
		String query = String.format("UPDATE Appointment SET description = %s WHERE appointmentID = %d",description,id);
		updateAppointmentFromQuery(id,query);
	}
	
	public static void updateAppointmentMeeting(int id, boolean meeting) 
			throws ClassNotFoundException, SQLException{
		String query = String.format("UPDATE Appointment SET meeting = %b  WHERE appointmentID = %d",meeting,id);
		updateAppointmentFromQuery(id,query);
	}
	
	// Har ikke update for owner fordi denne skal ikke kunne endres!
	
}

