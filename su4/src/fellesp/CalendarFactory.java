package fellesp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

public class CalendarFactory {
	
private static DBConnection db;
private static Calendar cal; 
private static AppointmentFactory apfac;

	
	public CalendarFactory(Properties properties) throws ClassNotFoundException, SQLException
	{
		 db = new DBConnection(properties);
		 cal = Calendar.getInstance();
		 apfac = new AppointmentFactory(properties);
		 
	}
	
	public static ArrayList<Appointment> getWeek(String userName, int weekNumber) throws ClassNotFoundException, SQLException {
		ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();
		String query = String.format("SELECT appointmentID FROM Invite WHERE username = '" + userName + "' AND response = '2';");
		
		db.initialize();
		ResultSet rs = db.makeSingleQuery(query);
		while (rs.next()){
			Appointment a = AppointmentFactory.getAppointment(rs.getInt(1));
			String date = a.getDate();
			setCalendar(date);
			int week = cal.get(Calendar.WEEK_OF_YEAR);
			if (week == weekNumber){
				appointmentList.add(a);
			}
		}
		return appointmentList;	
	}
	
	private static void setCalendar(String date){
		String[] b = date.split("-");
		int[] d = new int[3];
		for (int i = 0; i < b.length; i++){
			d[i] = Integer.parseInt(b[i]);
		}
		cal.set(d[0], d[1] - 1, d[2]);
	}

}
