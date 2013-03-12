package fellesp;

public class CalandarHandler{
   
   private boolean LoggedIn=false;
   private Employee employee;
   
 //  public CalendarHandler(Employee employee){
   //   this.employee=employee;
   //}
   
   public void LogIn(String username, String password1){
     //SPØRRING:password2 =select password from Employee as e where username=e.username
     if password1=password2{
       LoggedIn=True;
     }
   }
   
   public void LogOut(){
      LoggedIn=false;
   }
   
   
}
