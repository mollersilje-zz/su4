package fellesp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class CalandarHandler{
   
   private boolean LoggedIn=false;
   private User user;
   private Appointment appointment;
   private static InviteFactory infac;
   private static UserFactory usfac;
   
   public static void main(String [ ] args) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException{
	   Properties prop = new Properties();
	   prop.load(new FileInputStream("./Properties.properties"));
	   infac = new InviteFactory(prop);
	   usfac = new UserFactory(prop);
	   
	   
	   
	   
	   
   }
   
 //  public CalendarHandler(User user){
   //   this.employee=user;
   //}
   
   public void LogIn(String username, String password1){
     String password2 = null;
	   //SPØRRING:password2 =select password from User as e where username=e.username
     if (password1.equals(password2)) {
       LoggedIn = true;
     }
   }
   
   public static ArrayList<Integer> checkInvites(String username) throws ClassNotFoundException, SQLException{
	   ArrayList<Integer> list= new ArrayList<Integer>();
	   list = UserFactory.getUnansweredInvites(username);
	   if (list.size() == 0){
		   return null;
	   }
	   else{
	   return list;
	   }
	}
	
   
   public void LogOut(){
      LoggedIn=false;
   }
   
   public void createAppointment(Date date, Time startTime, Time endTime, String des, boolean type, User owner) {
	   appointment = new Appointment(date, startTime, endTime, des, type, owner);
   }
   
   public void deleteAppointment(Date date, Time startTime, Time endTime, String des, boolean type, User owner) {
	   
   }
   
}
