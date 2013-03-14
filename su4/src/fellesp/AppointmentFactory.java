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
	
	public Appointment createAppointment(Date date, Time startTime, Time endTime, String place, String description, int type, String owner) throws ClassNotFoundException, SQLException{
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		java.sql.Time sqlStartTime = new java.sql.Time(startTime.getTime());
		java.sql.Time sqlEndTime = new java.sql.Time(endTime.getTime());
		
		int id = getNextId();
		
		Appointment a= new Appointment(id, date, startTime, endTime, place, description, owner, type);
		String query=String.format("insert into appointment " + "(date, startTime, endTime, description, type, owner) values (" 
				+ id + "," + sqlDate + "," + sqlStartTime +"," + sqlEndTime +"," + description + "," 
				+ type + "," + owner + ");" );
	
		db.initialize();
		db.makeSingleUpdate(query);
		db.close();
		return a;
	}
	
	public Appointment getAppointment(int id) throws SQLException, ClassNotFoundException{
		String query = String.format("select * from Appointmet where id =" + id + ";" );
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		Date date = null;
		Time startTime = null;
		Time endTime = null;
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

		db.close();
		
		return a;
	}
	
	public int getNextId() throws ClassNotFoundException, SQLException{
		String query= "select max(appointmentID) from Appointment ;";
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
	
	public void deleteAppointment(String owner) throws ClassNotFoundException, SQLException{
		
		String query =String.format("delete from Appintment where owner =" + owner + ";");
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		rs.close();
		db.close();
		
	}
	
	public void updateAppointment(String id){
		String query = String.format("UPDATE User");
		
		
	}
	
	public void updateAppointmentDate(String id, Date date){
		
		
		
		
	}
}
