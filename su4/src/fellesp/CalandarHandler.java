package fellesp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.Scanner;
import java.util.Date;
import fellesp.Appointment;
import fellesp.AppointmentFactory;
import fellesp.InviteFactory;
import fellesp.User;
import fellesp.UserFactory;


public class CalandarHandler{

	private static boolean LoggedIn=false;
	private static boolean ViewingCalendar=false;
	private static boolean ChangingAppointment=false;
	private static User user;
	private static InviteFactory infac;
	private static UserFactory usfac;
	private static AppointmentFactory apfac;
	private static String username;
	private static String password;
	private static Scanner sc;
	private static ArrayList<Integer> notRespondedList;
	private static Calendar cal;
	private static int weekNumber;

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException{
		sc = new Scanner(System.in);
		Properties prop = new Properties();
		prop.load(new FileInputStream("./Properties.properties"));
		cal = Calendar.getInstance();
		infac = new InviteFactory(prop);
		usfac = new UserFactory(prop);
		apfac = new AppointmentFactory(prop);



		//LOGIN
		while (!LoggedIn){
			Login();
		

			while (LoggedIn){ 
				System.out.println("Hva vil du gjøre nå? \n 1) Se Kalender \n 2) Sjekke nye invitasjoner \n " +
						"3) Sjekke avlyste møter \n 4) Sjekke avslag \n 5) Sjekke avbud \n 6) Opprette avtale/møte \n 7) Endre avtale/møte \n 8) Slette avtale/møte" +
						" \n 9) Melde avbud på møte \n 10) Sette alarm på avtale \n 11) Logge ut \n");
				String start = sc.nextLine();
	
				switch(start){
				case "1": defaultWeekNumber(); ViewingCalendar = true; break;
				case "2": handleInvites(); break; 
				case "3": checkCancelled(); break;
				case "4": checkDeclines(username); break;
				case "5": checkCancellations(); break;
				case "6": try {
					addAppointment(); break;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} break;
				case "7": changeAppointment(); break;
				case "8": deleteAppointment(); break;
				case "9": giveCancellation(); break;
				case "10": addAlarm(); break;
				case "11": LogOut(); break; // IKKE ferdig enda.
	
				default: System.out.println("Skriv inn gyldig tall.\n"); break;
				}
	
				while(ViewingCalendar){
					String out = viewCalendar(username, weekNumber);
					System.out.println(String.format("Du ser nå på %ss kalender for uke ", username) + out);
					System.out.println(" 1) Neste Uke \n 2) Forrige Uke \n 3) Se en annens kalender \n 4) Tilbake til hovedmeny");
					String Brynjar = sc.nextLine();
					switch(Brynjar){
					case "1": incrementWeekNumber(); break; 
					case "2": decrementWeekNumber(); break;
					case "3": changeUser(); break;
					case "4": username = user.getUsername(); ViewingCalendar = false; break;
					default: System.out.println("Skriv inn gyldig tall."); break;
					}
				}
	
			}
		}

	}


	private static void changeUser() {
		System.out.println("Hvilken brukers kalender ønsker du å se?:");
		username = sc.nextLine();

	}

	private static void addAlarm() throws ClassNotFoundException, SQLException {
		System.out.println("Skriv inn AppointmentID for møtet: ");
		String id = sc.nextLine();
		int intid = Integer.parseInt(id);
		System.out.println("Skriv inn tidspunkt for alarm: ");
		String alarm = sc.nextLine();
		AppointmentFactory.addAlarm(intid, alarm);
	}

	public static void LogInCheck(String username, String password1) throws ClassNotFoundException, SQLException{
		String password2 = null;
		user = UserFactory.getUser(username);
		password2 = user.getPassword();

		if (password1.equals(password2)) {
			LoggedIn = true;
		} else {
			user = null;
		}

	}

	public static void Login() throws ClassNotFoundException, SQLException{
		System.out.println("Du er ikke logget inn. Skriv inn brukernavn og passord.\nBrukernavn: ");
		username = sc.nextLine();
		System.out.println("Passord: ");
		password = sc.nextLine();

		LogInCheck(username, password);
		if (LoggedIn){
			username = user.getUsername();
			System.out.println("Du er logget inn!");
		}
		else{ System.out.println("Feil passord.");}
	}

	public static void handleInvites() throws ClassNotFoundException, SQLException{
		// Hï¿½ndterer ubesvarte invitasjoner
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
			if (answerInvites.equalsIgnoreCase("JA")){
				System.out.println("Skriv inn appointmentID: ");
				String id = sc.nextLine();
				int intid = Integer.parseInt(id);
				System.out.println("Skriv inn respons(Godta/Avslå): ");
				String respons = sc.nextLine();
				int intrespons;
				while (1>0){
					if(respons.equalsIgnoreCase("Godta")){
						intrespons=2;
						break;
					}
					else if (respons.equalsIgnoreCase("Avslå")){
						intrespons=3;
						break;
					}
					else {System.out.println("Skriv inn Godta eller Avslå");}
					// Hvis Godta mï¿½ det opprettes Appointment i kalender
				}

				InviteFactory.updateInviteResponse(username, intrespons, intid);

			}
			else if (answerInvites.equalsIgnoreCase("NEI")){
				System.out.println("Du har valgt å ikke svare på invitasjoner nå.");
			}
			else{System.out.println("Du skrev ukjent input og har blitt sendt tilbake til hovedmeny. \n");}
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

	public static void checkCancellations(){
		// Denne skal sjekke om noen har meldt avbud på et møte KRAV 9
		
	}
	
	public static void giveCancellation() throws ClassNotFoundException, SQLException{
		// KRAV 9
		ArrayList<Integer> listWhereOwner = AppointmentFactory.getMeetingWhereOwner(username);
		ArrayList<Integer> meetings = InviteFactory.getAcceptedInvitesForThisUse(username);
		ArrayList<Integer> acceptedMeetings = new ArrayList<Integer>();
		for (int i: meetings){
			if (!listWhereOwner.contains(i)){
				acceptedMeetings.add(i);
			}
		}
		if (acceptedMeetings.isEmpty()){
			System.out.println("Du er ikke deltaker på noen møter. \n");
			return;
		}
		System.out.println("Du skal på disse møtene:");
		for (int i: acceptedMeetings){
			System.out.println("ID: " + i);
		}		
		System.out.println("Hvilket møte vil du melde avbud for?");
		String svar = sc.nextLine();
		appointmentDeleter(svar);
		
		
	}
	
	public static void checkCancelled() throws ClassNotFoundException, SQLException{
		// Denne sjekker om det er noen avlyste møter
		ArrayList<Integer> cancelled = InviteFactory.findCancelledAppointments(username);
		if (cancelled.isEmpty()){
			System.out.println("Du har ingen avlyste møter");
		}
		else {
			System.out.println("Disse møtene er avlyste: ");
			for (int i: cancelled){
				System.out.println("ID: " + i);
				InviteFactory.deleteInviteAppointmentWhereCancelled(i, username);
	
			}
			System.out.println("Disse er nå slettet fra din kalender.");	
		}
		
	}

	public static void addAppointment() throws ParseException, ClassNotFoundException, SQLException{
		String place = null;
		String description = null;
		boolean meetingbol = false;
		ArrayList<String> list = new ArrayList<String>();

		System.out.println("Vil du lage en avtale eller et møte (Avtale/Møte)? :");
		String meeting = sc.nextLine();

		if (meeting.equalsIgnoreCase("Møte")){
			meetingbol = true;
			System.out.println("Skriv inn deltager (en om gangen). Avslutt med 'Ferdig'.");
			String svar = sc.nextLine();
			while (!svar.equalsIgnoreCase("Ferdig")){
				if (UserFactory.isUser(svar)){
					list.add(svar);
					System.out.println("Bruker lagt til.");
				}
				else{ System.out.println("Brukeren eksisterer ikke. Skriv inn nytt brukernavn:");}
				svar = sc.nextLine();

			}
			// DATO
			System.out.println("Skriv inn dato på formen YYYY-MM-DD: ");
			String dateString = sc.nextLine();
			// TID
			String startTime;
			String endTime;
			do{
				System.out.println("Skriv inn starttid på formen hh:mm : ");
				startTime = sc.nextLine() + ":00";
				System.out.println("Skriv inn sluttid på formen hh:mm : ");
				endTime = sc.nextLine() + ":00";
			} while(endBeforeStart(startTime, endTime));
				
			// Beskrivelse
			System.out.println("Skriv inn beskrivelse: ");
			description = sc.nextLine();
			
			// Reservere møterom
			System.out.println("Disse rommene er ledige da: ");
			AppointmentFactory.availableRooms(dateString, startTime, endTime);
			System.out.println("Skriv inn rom du vil reservere: ");
			place = sc.nextLine();

			Appointment a = AppointmentFactory.createAppointment(dateString, startTime, endTime, place, description, meetingbol, username);
			InviteFactory.createAppointmentInvite(username, a.getId());
			for (String user:list){
				InviteFactory.createInvite(user, a.getId());
			}

		}

		else if (meeting.equalsIgnoreCase("Avtale")){ 

			meetingbol = false;

			// DATO
			System.out.println("Skriv inn dato på formen YYYY-MM-DD: ");
			String dateString = sc.nextLine();

			// TID
			String endTime;
			String startTime;
			do{
				System.out.println("Skriv inn starttid på formen hh:mm : ");
				startTime = sc.nextLine() + ":00";
				System.out.println("Skriv inn sluttid på formen hh:mm : ");
				endTime = sc.nextLine() + ":00";
			} while(endBeforeStart(startTime, endTime));

			// Beskrivelse
			System.out.println("Skriv inn beskrivelse: ");
			description = sc.nextLine();

			//Sted
			System.out.println("Skriv inn sted: ");
			place = sc.nextLine();

			Appointment a = AppointmentFactory.createAppointment(dateString, startTime, endTime, place, description, meetingbol, username);
			InviteFactory.createAppointmentInvite(username, a.getId());
		}
	}

	private static boolean endBeforeStart(String startTime, String endTime) throws ParseException {
		DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Date start = sdf.parse(startTime);
		Date end = sdf.parse(endTime);
		if (end.compareTo(start)<0){
			System.out.println("Sluttid før Starttid. Skriv inn på nytt. \n");
			return true;
		}
		return false;
	}


	public static void changeAppointment() throws ClassNotFoundException, SQLException{
		ChangingAppointment = true; 
		System.out.println("Skriv inn avtaleID som du ønsker å endre på: ");
		String id = sc.nextLine();
		int idint = Integer.parseInt(id);
		if (!AppointmentFactory.isMeeting(idint)){
			while (ChangingAppointment) {
				System.out.println("Hva vil du endre på \n 1) Endre dato \n 2) Endre starttid \n 3) Endre slutttid \n 4) Endre sted \n 5) Endre beskrivelse \n 6) Ferdig \n");
				String svar = sc.nextLine();
				switch (svar){
				case "1": changeDate(idint);break;
				case "2": changeStartTime(idint); break;
				case "3": changeEndTime(idint); break;
				case "4": changePlace(idint); break;
				case "5": changeDescription(idint); break;
				case "6": ChangingAppointment = false; break;
				default: System.out.println("Skriv inn gyldig tall.");
				}
			}
		}
		else{
			while (ChangingAppointment) {
				System.out.println("Hva vil du endre på? \n 1) Endre dato \n 2) Endre starttid \n 3) Endre slutttid \n 4) Ferdig \n");
				String svar = sc.nextLine();
				switch (svar){
				case "1": changeDate(idint);break;
				case "2": changeStartTime(idint); break;
				case "3": changeEndTime(idint); break;
				case "4": ChangingAppointment = false; break;
				}
				ArrayList<String> list = InviteFactory.getParticipants(idint);
				InviteFactory.deleteInviteAppointment(idint);
				for (String user: list){
					InviteFactory.createInvite(user, idint);
				}
				InviteFactory.updateInviteResponse(username, 2, idint);
			}
		}
	}

	private static void changeDescription(int id) throws ClassNotFoundException, SQLException {
		System.out.println("Skriv inn ny beskrivelse: ");
		String des = sc.nextLine();
		AppointmentFactory.updateAppointmentDescription(id, des);

	}

	private static void changePlace(int id) throws ClassNotFoundException, SQLException {
		System.out.println("Skriv inn nytt sted: ");
		String place = sc.nextLine();
		AppointmentFactory.updateAppointmentPlace(id, place);

	}

	private static void changeEndTime(int id) throws ClassNotFoundException, SQLException {
		System.out.println("Skriv inn ny slutt-tid på formen hh:mm: ");
		String endTime = sc.nextLine() + ":00";
		AppointmentFactory.updateAppointmentEndTime(id, endTime);
	}

	private static void changeStartTime(int id) throws ClassNotFoundException, SQLException {
		System.out.println("Skriv inn ny start-tid på formen hh:mm: ");
		String startTime = sc.nextLine() + ":00";
		AppointmentFactory.updateAppointmentStartTime(id, startTime);

	}
	
	public static void deleteAppointment() throws ClassNotFoundException, SQLException{
		ArrayList<Integer> ownedAppointments = AppointmentFactory.getMeetingWhereOwner(username);
		if (ownedAppointments.isEmpty()){
			System.out.println("Du er ikke eier for noen avtaler. \n");
			return;
		}
		System.out.println("Du er eier for disse avtalene:");
		for (int i: ownedAppointments){
			System.out.println("ID: " + i);
		}
		System.out.println("Skriv inn avtaleID som du ønsker å slette: ");
		String id = sc.nextLine();
		appointmentDeleter(id);
	}

	public static void appointmentDeleter(String id) throws ClassNotFoundException, SQLException {
		
		int idint = Integer.parseInt(id);
		if (!AppointmentFactory.isMeeting(idint)){
			AppointmentFactory.deleteAppointment(idint);
			InviteFactory.deleteInviteAppointment(idint);
		} else if (AppointmentFactory.isMeetingOwner(username, idint)){
				AppointmentFactory.deleteAppointment(idint);
				InviteFactory.deleteInviteAppointment(idint);
		} else {
				InviteFactory.updateInviteResponse(username, 3, idint);
		}
	}
	
	public static void changeDate(int id) throws ClassNotFoundException, SQLException{
		System.out.println("Skriv inn ny dato på format YYYY-MM-DD : ");
		String datestring = sc.nextLine();
		AppointmentFactory.updateAppointmentDate(id, datestring);
	}

	public static void LogOut(){
		LoggedIn=false;
		System.out.println("Du er nå logget ut. \n");
	}

	public static String viewCalendar(String userName, int weekNumber) throws ClassNotFoundException, SQLException{
		ViewingCalendar = true;
		String s = "";
		ArrayList<Appointment> apList = viewCalendarWeek(userName, weekNumber);
		s = weekNumber + "\n";
		for (int i = 0; i < apList.size(); i++){
			Appointment a = apList.get(i);
			s = s + a.toString() + "\n";
			if (a.getOwner().equalsIgnoreCase(user.getUsername()) && a.isMeeting()) {
				s = s + " Møtets status: " + meetingStatus(apList.get(i).getId()) + "\n";
			}
		}
		return s;
	}

	private static ArrayList<Appointment> viewCalendarWeek(String userName, int weekNumber) throws ClassNotFoundException, SQLException {
		ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();
		ArrayList<Integer> aIDList = InviteFactory.getInviteApointmentID(userName);
		for (int i = 0; i < aIDList.size(); i++){
			Appointment a = AppointmentFactory.getAppointment(aIDList.get(i));
			String date = a.getDate();
			setCalendar(date);
			int week = cal.get(Calendar.WEEK_OF_YEAR);
			if (week == weekNumber){
				appointmentList.add(a);
			}
			cal = Calendar.getInstance();
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

	private static void incrementWeekNumber(){
		weekNumber = weekNumber + 1;
	}
	
	private static void decrementWeekNumber(){
		weekNumber = weekNumber - 1;
	}
	
	private static void defaultWeekNumber(){
		Calendar.getInstance();
		weekNumber = cal.get(Calendar.WEEK_OF_YEAR);
	}
	
	private static String meetingStatus(int aID) throws ClassNotFoundException, SQLException {
		String reply = "Ikke alle har besvart invitasjonen";
		boolean someDeclined = InviteFactory.isMeetingDeclined(aID);
		boolean notAllAnswered = InviteFactory.isMeetingUnanswered(aID);
		if (someDeclined && notAllAnswered) {
			reply = "Noen har avslått møtet, men ikke alle har svart";
		} else if (someDeclined && !notAllAnswered) {
			reply = "Noen har avslått møtet, og alle har svart";
		} else if (!notAllAnswered && !someDeclined) {
			reply = "Alle har godtatt møtet";
		}
		return reply;
	}
}