package fellesp;

public class CalandarHandler{
   
   private boolean LoggedIn=false;
   private User user;
   private Appointment appointment;
   
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
   
   public void LogOut(){
      LoggedIn=false;
   }
   
   public void createAppointment(Date date, Time startTime, Time endTime, String des, boolean type, User owner) {
	   appointment = new Appointment(date, startTime, endTime, des, type, owner);
   }
   
   public void deleteAppointment(Date date, Time startTime, Time endTime, String des, boolean type, User owner) {
	   
   }
   
}
