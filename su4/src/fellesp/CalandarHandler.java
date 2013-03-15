package fellesp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;


public class CalandarHandler{
   
   private static boolean LoggedIn=false;
   private static User user;
 //  private static Appointment appointment;
   private static InviteFactory infac;
   private static UserFactory usfac;
   private static String username;
   private static String password;
   private static Scanner sc;
   private static ArrayList<Integer> notRespondedList;
   
   public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException{
	   sc = new Scanner(System.in);
	   Properties prop = new Properties();
	   prop.load(new FileInputStream("./Properties.properties"));
	   infac = new InviteFactory(prop);
	   usfac = new UserFactory(prop);
	   
	   //LOGIN
	   while (!LoggedIn){
		   System.out.println("Du er ikke logget inn. Skriv inn brukernavn og passord.\n Brukernavn: ");
		   username = sc.nextLine();
		   System.out.println("Passord: ");
		   password = sc.nextLine();

		   LogIn(username, password);
		   if (LoggedIn){
			   System.out.println("Du er logget inn!");
		   }
		   else{ System.out.println("Feil passord.");}
	   }
	   
	   // Håndterer ubesvarte invitasjoner
	   notRespondedList = new ArrayList<Integer>();
	   notRespondedList = checkInvites(username);
	   if (notRespondedList == null){
		   System.out.println("Ingen ubesvarte invitasjoner");
	   }
	   else{
		   System.out.println("Du har disse ubesvarte invitasjonene:");
		  for (int i: notRespondedList){
			  System.out.println(i+ "\n");
		  }  
	   }
	   
	   System.out.println("Ønsker du å svare på invitasjoner nå? (JA/NEI)");
	   String answerInvites = sc.nextLine();
	   if (answerInvites.equals("JA")){
		   System.out.println("Skriv inn appointmentID: ");
		   String id = sc.nextLine();
		   int intid = Integer.parseInt(id);
		   System.out.println("Skriv inn respons(Godta/Avslå): ");
		   String respons = sc.nextLine();
		   int intrespons;
		   while (1>0){
			   if(respons.equals("Godta")){
				   intrespons=2;
				   break;
			   }
			   else if (respons.equals("Avslå")){
				   intrespons=3;
				   break;
			   }
			   else {System.out.println("Skriv inn Godta eller Avslå");}

		   }
		   
		   infac.updateInviteResponse(username, intrespons, intid);
		   
		   
	   }
	   
	   
   }
   
 //  public CalendarHandler(User user){
   //   this.employee=user;
   //}
   
   public static void LogIn(String username, String password1) throws ClassNotFoundException, SQLException{
     String password2 = null;
	 //SPÃ˜RRING:password2 =select password from User as e where username=e.username
     User user = UserFactory.getUser(username);
     password2 = user.getPassword();
       
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
   
   /*public void createAppointment(Date date, Time startTime, Time endTime, String place, String des, boolean type, User owner) {
	   
   }
   
   public void deleteAppointment(Date date, Time startTime, Time endTime, String des, boolean type, User owner) {
	   
   }
   */
   
   
   
}
