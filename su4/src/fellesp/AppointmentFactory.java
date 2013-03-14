package fellesp;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Properties;

public class AppointmentFactory {
	
<<<<<<< HEAD
	private static DBConnection db;
=======
	static DBConnection db;
>>>>>>> AppointmentTest
	
	public AppointmentFactory(Properties properties) throws ClassNotFoundException, SQLException
	{
		 db = new DBConnection(properties);
	}
	
<<<<<<< HEAD
	public static Appointment createAppointment(Date date, Time startTime, Time endTime, String place, String description, int type, String owner) throws ClassNotFoundException, SQLException{
=======
	public static Appointment createAppointment(Date date, Time startTime, Time endTime, String description, boolean meeting, String owner) throws ClassNotFoundException, SQLException{
>>>>>>> AppointmentTest
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		java.sql.Time sqlStartTime = new java.sql.Time(startTime.getTime());
		java.sql.Time sqlEndTime = new java.sql.Time(endTime.getTime());
		
		int id = getNextId();
		
<<<<<<< HEAD
		Appointment a= new Appointment(id, date, startTime, endTime, place, description, owner, type);
		String query=String.format("insert into appointment " + "(date, startTime, endTime, description, type, owner) values (" 
				+ id + "," + sqlDate + "," + sqlStartTime +"," + sqlEndTime +"," + description + "," 
				+ type + "," + owner + ");" );
	
=======
		Appointment a= new Appointment(id, date, startTime, endTime, description, owner, meeting);
		String query=String.format("insert into appointment " + "(date, startTime, endTime, description, type, owner) values (" + id + "," + sqlDate + "," + sqlStartTime +"," + sqlEndTime +"," + description + "," 
		+ meeting + "," + owner + ");" );
		
>>>>>>> AppointmentTest
		db.initialize();
		db.makeSingleUpdate(query);
		db.close();
		return a;
	}
	
<<<<<<< HEAD
	public static Appointment getAppointment(int id) throws SQLException, ClassNotFoundException{
		String query = String.format("select * from Appointmet where id =" + id + ";" );
=======
	public static  Appointment getAppointment(int id) throws SQLException, ClassNotFoundException{
		String query = String.format("select * from Appointmet where id =" + id);
>>>>>>> AppointmentTest
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		Date date = null;
		Time startTime = null;
		Time endTime = null;
<<<<<<< HEAD
		String place = null;
		String description = null;
		int type = 1; // "1" avtale og "2" er møte
		String owner = null;
		
		while (rs.next()){
			date = rs.getDate(2);
			startTime = rs.getTime(3);
			endTime = rs.getTime(4);
			place = rs.getString(5);
			description = rs.getString(6);
			type = rs.getInt(7);
			owner = rs.getString(8);
		}
		
		Appointment a= new Appointment(id, date, startTime, endTime, place, description, owner, type);

=======
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
>>>>>>> AppointmentTest
		db.close();
		
		return a;
	}
	
<<<<<<< HEAD
	public static int getNextId() throws ClassNotFoundException, SQLException{
		String query= "select max(appointmentID) from Appointment ;";
=======
	private static int getNextId() throws ClassNotFoundException, SQLException{
		String query= "select max(appointmentID) from Appointment";
>>>>>>> AppointmentTest
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		int nextId = 0;
		while( rs.next() ){
			nextId = rs.getInt(0);
		}
		rs.close();
		db.close();
		
		return nextId++;
	}
	
	public static void deleteAppointment(String owner) throws ClassNotFoundException, SQLException{
		
<<<<<<< HEAD
		String query =String.format("delete from Appintment where owner =" + owner + ";");
=======
		String query =String.format("delete from Appintment where owner =" + owner);
>>>>>>> AppointmentTest
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		rs.close();
		db.close();
		
	}
	
<<<<<<< HEAD
	public static void updateAppointment(String id){
		String query = String.format("UPDATE User");
		
		
	}
	
	public static void updateAppointmentDate(String id, Date date){
		
		
		
		
	}
}
=======
	
}
>>>>>>> AppointmentTest
