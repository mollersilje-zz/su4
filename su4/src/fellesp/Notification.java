package fellesp;

import java.sql.Time;

public class Notification {
	
	private Time alarm;
	public int i;
	public int participants;

	public void setAlarm(Time alarm) {
		this.alarm = alarm;
	}
	
	public Time getAlarm() {
		return alarm;
	}
	
	public String response(int i, int participants){
		participants = 0;
		if (i == 1){
			return "Accept";
			participants++;
			}
		else if (i == 2){return "Decline";}
		else {return "NoResponce";}
	}
	
}
