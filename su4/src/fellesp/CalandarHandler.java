package fellesp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Scanner;

import fellesp.Appointment;
import fellesp.AppointmentFactory;
import fellesp.InviteFactory;
import fellesp.User;
import fellesp.UserFactory;


public class CalandarHandler{

	private static boolean LoggedIn=false;
	private static User user;
	//  private static Appointment appointment;
	private static InviteFactory infac;
	private static UserFactory usfac;
	private static AppointmentFactory apfac;
	private static CalendarFactory cafac;
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
		apfac = new AppointmentFactory(prop);
		cafac = new CalendarFactory(prop);



		//LOGIN
		while (!LoggedIn){
			Login();
		}

		while (LoggedIn){ 
			System.out.println("Hva vil du gjøre nå? \n 1) Sjekke nye invitasjoner \n 2) Sjekke avslag \n 3) Opprette avtale/møte \n 4) Endre avtale/møte \n 5) Slette avtale/møte" +
					" \n 6) Sette alam på avtale \n 7) Logge ut \n");
			String start = sc.nextLine();

			switch(start){
			case "1": handleInvites(); break; // Skriver ikke ut dato og tid riktig. Kan gjøres "finere".
			case "2": checkDeclines(username); break;

			case "3": try {
				addAppointment(); break;//Fikse dato og tid-objekter. Ellers alt ok.
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} break;
			case "4": changeAppointment(); break;
			case "5": deleteAppointment(); break;


			case "7": LogOut(); break; // IKKE ferdig enda.

			default: System.out.println("Skriv inn riktig tall."); break;
			}

		}

	}


	public static void LogInCheck(String username, String password1) throws ClassNotFoundException, SQLException{
		String password2 = null;
		password2 = UserFactory.getPassword(username);

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
		// Håndterer ubesvarte invitasjoner
		notRespondedList = new ArrayList<Integer>();
		notRespondedList = checkInvites(username);
		if (notRespondedList == null){
			System.out.println("Ingen ubesvarte invitasjoner");
		}
		else{
			System.out.println("Du har disse ubesvarte invitasjonene:");
			for (int i: notRespondedList){
				System.out.println("AvtaleID: " +i + "\n" + printAppointment(i));
				System.out.println("Inviterte deltagere for avtaleID " + i + " " + InviteFactory.getParticipants(i) + "\n");
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
					// Hvis Godta må det opprettes Appointment i kalender
				}

				InviteFactory.updateInviteResponse(username, intrespons, intid);

			}
			else if (answerInvites.equals("NEI")){
				System.out.println("Du har valgt å ikke svare på invitasjoner nå.");
			}
			else{System.out.println("Du skrev feil input og har blitt sendt tilbake til hovedmeny. \n");}
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

	public static String printAppointment(int aID) throws ClassNotFoundException, SQLException{
		Appointment a = AppointmentFactory.getAppointment(aID);

		String date = a.getDate();
		String startTime = a.getStartTime();
		String endTime = a.getStartTime();


		String res = "Dato: " + date + "\n" + "Starttid: " + startTime + "\n" + "Sluttid: " + endTime +  "\n" +
				"Sted: " + a.getPlace() +  "\n" + "Beskrivelse: " + a.getDescription() + "\n" + "Eier: " +  " " + a.getOwner();

		return res;

	}

	public static void checkDeclines(String username) throws ClassNotFoundException, SQLException{
		ArrayList<Integer> declineList = new ArrayList<Integer>();
		declineList = InviteFactory.getInviteApointmentID(username);

		for (int aid: declineList){
			for (String user:InviteFactory.getDeclinedUsers(aid)){
				System.out.println( user + " har avslått avtale " + aid);
			}
		}
	}


	public static void addAppointment() throws ParseException, ClassNotFoundException, SQLException{
		String place = null;
		String description = null;
		boolean meetingbol = false;
		ArrayList<String> list = new ArrayList<String>();

		System.out.println("Vil du lage en avtale eller et møte (Avtale/Møte)? :");
		String meeting = sc.nextLine();

		if (meeting.equals("Møte")){
			meetingbol = true;
			System.out.println("Skriv inn deltager (en om gangen). Avslutt med 'Ferdig'.");
			String svar = "";
			while (!svar.equals("Ferdig")){
				svar = sc.nextLine();
				list.add(svar);
			}
			// DATO
			System.out.println("Skriv inn dato på formen YYYY-MM-DD: ");
			String dateString = sc.nextLine(); 
			// TID
			System.out.println("Skriv inn starttid på formen hh:mm:ss : ");
			String startTime = sc.nextLine();
			System.out.println("Skriv inn sluttid på formen hh:mm:ss : ");
			String endTime = sc.nextLine();


			// Beskrivelse
			System.out.println("Skriv inn beskrivelse: ");
			description = sc.nextLine();
			
			// Reservere møterom
			System.out.println("Disse rommene er ledige da: ");
			AppointmentFactory.availableRooms(dateString, startTime, endTime);
			System.out.println("Skriv inn rom du vil reservere: ");
			place = sc.nextLine();

			Appointment a = AppointmentFactory.createAppointment(dateString, startTime, endTime, place, description, meetingbol, username);
			for (String user:list){
				InviteFactory.createInvite(user, a.getId());
			}

		}
		else if (meeting.equals("Avtale")){ 
			
			meetingbol = false;
			
			// DATO
			System.out.println("Skriv inn dato på formen YYYY-MM-DD: ");
			String dateString = sc.nextLine();
			
			// TID
			System.out.println("Skriv inn starttid på formen hh:mm:ss : ");
			String startTime = sc.nextLine();
			System.out.println("Skriv inn sluttid på formen hh:mm:ss : ");
			String endTime = sc.nextLine();
			
			// Beskrivelse
			System.out.println("Skriv inn beskrivelse: ");
			description = sc.nextLine();
			
			//Sted
			System.out.println("Skriv inn sted: ");
			place = sc.nextLine();
			
			Appointment b = AppointmentFactory.createAppointment(dateString, startTime, endTime, place, description, meetingbol, username);
		}
	}


	public static void changeAppointment() throws ClassNotFoundException, SQLException{
		System.out.println("Skriv inn avtaleID som du ønsker å endre på: )");
		String id = sc.nextLine();
		int idint = Integer.parseInt(id);
		if (AppointmentFactory.isMeeting(idint)){

		}
		else {
			System.out.println("Hva vil du endre på? \n 1) Endre dato \n 2) Endre starttid \n 3) Endre slutttid \n 4) Endre sted \n 5) Endre beskrivelse" + "\n");
			String svar = sc.nextLine();
			switch (svar){
			case "1": changeDate(idint);break;
			}


		}

	}
	
	public static void deleteAppointment() throws ClassNotFoundException, SQLException {
		System.out.println("Skriv inn avtaleID som du ønsker å slette: ");
		String id = sc.nextLine();
		int idint = Integer.parseInt(id);
		if (AppointmentFactory.isMeeting(idint)){
			AppointmentFactory.deleteAppointment(idint);
		}
	}

	public static void changeDate(int id) throws ClassNotFoundException, SQLException{
		System.out.println("Skriv inn ny dato på format YYYY-MM-DD : ");
		String datestring = sc.nextLine();
		AppointmentFactory.updateAppointmentDate(id, datestring);
	}

	public static void LogOut(){
		LoggedIn=false;
		System.out.println("Du er nå logget ut.");
	}
	
	public static void viewCalendar(String userName){
		
	}


}
