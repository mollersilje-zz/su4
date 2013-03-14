package fellesp;


public class Invite {
  

	private int response; // 1 = No response, 2 = Godtatt, 3 = Avslått.
	private String username;
	private int appointmentID;
	
	public Invite (String username, int aID) {
		response = 1;
		this.username = username;
		this.appointmentID = aID;
	}
	
	
	
	public String getUsername() {
		return this.username;
	}
	
	public int getAppointmentID() {
		return this.appointmentID;
	}
	
	
	
	
	
}
