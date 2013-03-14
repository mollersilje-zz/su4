package fellesp;

import java.sql.Date;
import java.sql.Time;

public class MeetingRoom {
	
	private int roomNumber;
	private int capacity;
	
	public MeetingRoom(int roomnumber, int capacity){
		this.roomNumber=roomnumber;
		this.capacity=capacity;
	}
	
	public int getRoomNumber(){
		return this.roomNumber;
	}
	
	public int getCapacity(){
		return this.capacity;
	}
	
	public void setRoomNumber(int roomNumber){
		this.roomNumber=roomNumber;
	}
	
	public void setCapacity(int capacity){
		this.capacity=capacity;
	}

	public boolean isAvailable(Date date, Time startTime, Time endTime) {
		// TODO Auto-generated method stub
		return false;
	}
}
