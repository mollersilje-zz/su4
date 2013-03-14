package fellesp;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;

public class Appointment {
  
	private int id;
	private Date date;
	private Time startTime;
	private Time endTime;
	private MeetingRoom room;
	private String description;
	private boolean type; // "0" er avtale, "1" er møte
	private String owner;
	ArrayList<User> participants;

	
	public Appointment(int id, Date date, Time startTime, Time endTime, String description, boolean type, String owner){
		this.id = id;
	}	
		
	public Appointment(Date date, Time startTime, Time endTime, String description, boolean type, String owner){

		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
		this.type = type;
		this.setOwner(owner);
	}
	
	public Date getDate(){
		return date;
	}
	public Time getStartTime(){
		return startTime;
	}
	public Time getEndTime(){
		return endTime;
	}

	public ArrayList<MeetingRoom> ReserveRoom(Date date, Time startTime, Time endTime, boolean type, int participants){
		ArrayList<MeetingRoom> rooms = new ArrayList<MeetingRoom>();//Henter rom fra database hvor capacity >= participants
		ArrayList<MeetingRoom> availableRooms = new ArrayList<MeetingRoom>();

		for (int i = 0;i < rooms.size(); i++){
			if (rooms.get(i).isAvailable(date, startTime, endTime)){
				availableRooms.add(rooms.get(i));
			}
		}
	return availableRooms;
	}
	
	public String getDescription(){
		return description;
	}
	public boolean getType(){
		return type;
	}

	public String getOwner() {
		return owner;
	}

}
