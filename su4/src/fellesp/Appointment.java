package fellesp;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;

public class Appointment {
  

	private Date date;
	private Time startTime;
	private Time endTime;
	private MeetingRoom room;
	private String des;
	private boolean type;
	private User owner;
	ArrayList<User> participants;

	public Appointment(Date date, Time startTime, Time endTime, String des, boolean type, User owner){
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.des = des;
		this.type = type;
		this.owner = owner;
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
	
	public String getDes(){
		return des;
	}
	public boolean getType(){
		return type;
	}

}
