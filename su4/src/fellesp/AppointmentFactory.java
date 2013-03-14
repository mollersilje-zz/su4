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
	

	public static Appointment createAppointment(Date date, Time startTime, Time endTime, String description, boolean meeting, String owner) throws ClassNotFoundException, SQLException{

		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		java.sql.Time sqlStartTime = new java.sql.Time(startTime.getTime());
		java.sql.Time sqlEndTime = new java.sql.Time(endTime.getTime());
		
		int id = getNextId();
		int meetingInt = 0;
		if (meeting) {
			meetingInt = 1;
		}
		

		Appointment a= new Appointment(id, date, startTime, endTime, description, owner, meeting);
		String query=String.format("insert into Appointment " + "(appointmentID, date, startTime, endTime, description, meeting, owner) values ('" + id + "','" + sqlDate + "','" + sqlStartTime +"','" + sqlEndTime +"','" + description + "','" 
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
		String query= "select max(appointmentID) from Appointment ;";

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
	

	public static void updateAppointment(Date date, Time startTime, Time endTime, String place, String description, int type, String owner){

		String query = String.format("UPDATE User");
		
		
	}
	
	public static void updateAppointmentDate(String id, Date date){
		
		
		
		
	}
}

