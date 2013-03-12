package prosjekt;

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
	private Employee owner;
	ArrayList<Employee> participants;

	public Appointment(Date date, Time startTime, Time endTime, String des, boolean type, Employee owner){
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.des = des;
		this.type = type;
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
	public Arraylist<MeetingRoom> ReserveRoom(date, startTime, endTime, type){
		rooms = new ArrayList<MeetingRoom>();
		for (int i = 0;i < rooms.size(); i++){
			if (i.date != date){
				return rooms;
			}else{
				if((i.startTime > startTime) && (i.endTime <= endTime))
			}
		}
	}
	
	public String getDes(){
		return des;
	}
	public boolean getType(){
		return type;
	}

}