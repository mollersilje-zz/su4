package fellesp;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Properties;

public class AppointmentFactory {
	
	DBConnection db;
	
	public AppointmentFactory(Properties properties) throws ClassNotFoundException, SQLException
	{
		 db = new DBConnection(properties);
	}
	
	public Appointment createAppointment(Date date, Time startTime, Time endTime, String description, boolean type, String owner) throws ClassNotFoundException, SQLException{
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		java.sql.Time sqlStartTime = new java.sql.Time(startTime.getTime());
		java.sql.Time sqlEndTime = new java.sql.Time(endTime.getTime());
		
		Appointment a= new Appointment(getNextId(), date, startTime, endTime, description, type, owner);
		String query=String.format("insert into appointment " + "(date, startTime, endTime, description, type, owner) values (" + sqlDate + "," + sqlStartTime +"," + sqlEndTime +"," + description + "," 
		+ type + "," + owner + ");" );
		
		db.initialize();
		db.makeSingleUpdate(query);
		return a;
	}
	
	public Appointment getAppointment(int id) throws SQLException, ClassNotFoundException{
		String query = String.format("select * from Appointmet where id =" + id);
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		Date date = null;
		Time startTime = null;
		Time endTime = null;
		String description = null;
		boolean type = false ; // "false" er appoinment, "true" er meeting
		String owner = null;
		
		while (rs.next()){
			date = rs.getDate(1);
			startTime = rs.getTime(2);
			endTime = rs.getTime(3);
			description = rs.getString(4);
			type = rs.getBoolean(5);
			owner = rs.getString(6);
		}
		
		Appointment a= new Appointment(id , date, startTime, endTime, description, type, owner);
		return a;
	}
	
	public int getNextId() throws ClassNotFoundException, SQLException{
		String query= "select max(appointmentID) from Appointment";
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
}
