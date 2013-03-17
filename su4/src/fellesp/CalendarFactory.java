package fellesp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

public class CalendarFactory {
	
private static DBConnection db;
private static Calendar cal;

	
	public CalendarFactory(Properties properties) throws ClassNotFoundException, SQLException
	{
		 db = new DBConnection(properties);
		 cal = Calendar.getInstance();
		 
	}
	
	public static void getWeek(String userName, int weekNumber) throws ClassNotFoundException, SQLException {
		ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();
		String query = String.format("SELECT appointmentID FROM Invite WHERE username = '" + userName + "' AND response = '2';");
		
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		while (rs.next()){
			Appointment a = AppointmentFactory.getAppointment(rs.getInt(1));
			cal.set;
			appointmentList.add(a);
		}
		
		
		
	}

}
