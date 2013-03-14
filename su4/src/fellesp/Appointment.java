
package fellesp;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;

public class Appointment {
  
	private int id;
	private Date date;
	private Time startTime;
	private Time endTime;
	private String room;
	private String description;
	private String owner;
	private boolean meeting; // "1" avtale og "2" er møte


	public Appointment(int id, Date date, Time startTime, Time endTime, String description, String owner, boolean meeting){
		this.id = id;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
		this.owner = owner;
		this.meeting = meeting;
	}

	public int getId(){
		return id;
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
	
	public MeetingRoom getMeetingRoom(){
		return room;
	}
	
	public String getDescription(){
		return description;
	}

	public String getOwner(){
		return owner;
	}
	
	public boolean isMeeting(){
		return meeting;
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
