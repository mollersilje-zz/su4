
package fellesp;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;

public class Appointment {
  
	private int id;
	private String date;
	private String startTime;
	private String endTime;
	private String place;
	private String description;
	private String owner;
	private boolean meeting; // "false" avtale og "true" er møte


	public Appointment(int id, String date, String startTime, String endTime, String place, String description, boolean meeting, String owner){
		this.id = id;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.place = place;
		this.description = description;
		this.owner = owner;
		this.meeting = meeting;
		
	}

	public int getId(){
		return id;
	}
	public String getDate(){
		return date;
	}
	public String getStartTime(){
		return startTime;
	}
	public String getEndTime(){
		return endTime;
	}
	
	public String getPlace(){
		return place;
	}
	
	public String getDescription(){
		return description;
	}

	public boolean isMeeting(){
		return meeting;
	}
	
	public String getOwner(){
		return owner;
	}
	
	
	
	public ArrayList<MeetingRoom> ReserveRoom(Date date, Time startTime, Time endTime, boolean meeting, int participants){
		ArrayList<MeetingRoom> rooms = new ArrayList<MeetingRoom>();//Henter rom fra database hvor capacity >= participants
		ArrayList<MeetingRoom> availableRooms = new ArrayList<MeetingRoom>();

		for (int i = 0;i < rooms.size(); i++){
			if (rooms.get(i).isAvailable(date, startTime, endTime)){
				availableRooms.add(rooms.get(i));
			}
		}
	return availableRooms;
	}

}
