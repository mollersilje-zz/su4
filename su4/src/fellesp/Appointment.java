package fellesp;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;

public class Appointment {
  
	private int id;
	private Date date;
	private Time startTime;
	private Time endTime;
	private String place;
	private String description;
	private String owner;
	private int type; // "1" avtale og "2" er m�te


	public Appointment(int id, Date date, Time startTime, Time endTime, String place, String description, String owner, int type){
		this.id = id;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.place = place;
		this.description = description;
		this.owner = owner;
		this.type = type;
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
	
	public String getPlace(){
		return place;
	}
	
	public String getDescription(){
		return description;
	}

	public String getOwner(){
		return owner;
	}
	
	public int getType(){
		return type;
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


}
