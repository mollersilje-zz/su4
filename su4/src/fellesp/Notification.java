package Calendar;

import java.sql.Time;

public class Notification {
	
	private Time alarm;

	public Notification() {
		
	}
	public Time getAlarm() {
		return alarm;
	}

	public void setAlarm(Time alarm) {
		this.alarm = alarm;
	}
	
	public boolean acceptInvite(){
		return false;
	}
	
	public boolean rejectInvite(){
		return false;
	}
}
