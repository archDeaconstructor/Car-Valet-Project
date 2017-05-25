package models;

import java.util.Date;
import java.util.Timer;

public class ParkingSpot {
	
	static int counter=0;
	int id;
	Date arrival;
	Timer duration;
	String ccInfo;
	boolean empty;
	
	public ParkingSpot(){
		setId(counter++);
		setEmpty(true);
	}
	
	public ParkingSpot(Date arrival, Timer duration, String ccInfo){
		setId(counter++);
		setArrival(arrival);
		setDuration(duration);
		setCcInfo(ccInfo);
		setEmpty(false);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getArrival() {
		return arrival;
	}

	public void setArrival(Date arrival) {
		this.arrival = arrival;
	}

	public Timer getDuration() {
		return duration;
	}

	public void setDuration(Timer duration) {
		this.duration = duration;
	}

	public String getCcInfo() {
		return ccInfo;
	}

	public void setCcInfo(String ccInfo) {
		this.ccInfo = ccInfo;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}	
}
