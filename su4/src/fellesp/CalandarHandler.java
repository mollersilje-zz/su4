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
			Login();
		}

		while (LoggedIn){ 
			System.out.println("Hva vil du gj�re n�? \n 1) Sjekke nye invitasjoner \n 2) Se avtaler/m�ter \n 3) Opprette avtale/m�te \n 4) Endre avtale/m�te \n 5) Slette avtale/m�te" +
					" \n 6) Sette alam p� avtale \n 7) Logge ut \n");
			String start = sc.nextLine();
			int intstart = Integer.parseInt(start);
			switch(intstart){
			case 1: handleInvites();
			case 2: 

			}
		}






		// H�ndtere invitasjoner som har f�tt avslag.




	}

	//  public CalendarHandler(User user){
	//   this.employee=user;
	//}

	public static void LogInCheck(String username, String password1) throws ClassNotFoundException, SQLException{
		String password2 = null;
		//SPØRRING:password2 =select password from User as e where username=e.username
		User user = UserFactory.getUser(username);
		password2 = user.getPassword();

		if (password1.equals(password2)) {
			LoggedIn = true;
		}

	}

	public static void Login() throws ClassNotFoundException, SQLException{
		System.out.println("Du er ikke logget inn. Skriv inn brukernavn og passord.\n Brukernavn: ");
		username = sc.nextLine();
		System.out.println("Passord: ");
		password = sc.nextLine();

		LogInCheck(username, password);
		if (LoggedIn){
			System.out.println("Du er logget inn!");
		}
		else{ System.out.println("Feil passord.");}
	}

	public static void handleInvites() throws ClassNotFoundException, SQLException{
		// H�ndterer ubesvarte invitasjoner
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


			System.out.println("�nsker du � svare p� invitasjoner n�? (JA/NEI)");
			String answerInvites = sc.nextLine();
			if (answerInvites.equals("JA")){
				System.out.println("Skriv inn appointmentID: ");
				String id = sc.nextLine();
				int intid = Integer.parseInt(id);
				System.out.println("Skriv inn respons(Godta/Avsl�): ");
				String respons = sc.nextLine();
				int intrespons;
				while (1>0){
					if(respons.equals("Godta")){
						intrespons=2;
						break;
					}
					else if (respons.equals("Avsl�")){
						intrespons=3;
						break;
					}
					else {System.out.println("Skriv inn Godta eller Avsl�");}
					// Hvis Godta m� det opprettes Appointment i kalender
				}

				infac.updateInviteResponse(username, intrespons, intid);

			}
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

	public static void checkDeclines(String username) throws ClassNotFoundException, SQLException{
		ArrayList<Integer> declineList = new ArrayList<Integer>();
		declineList = InviteFactory.getInviteApointmentID(username);

		for (int aid: declineList){
			for (String user:InviteFactory.getDeclinedUsers(aid)){
				System.out.println( user + " har avsl�tt avtale " + aid);
			}
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
