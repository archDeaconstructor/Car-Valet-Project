package infoStorage;

import java.util.ArrayList;

public class CarSpots {

	//Constructed initial values for the things carried within the class
	int sotrageNumber = 0;
	boolean spotTaken = false;
	String members = new String();
	int time = 0;

	public String getMembers() {
		return members;
	}

	public void setMembers(String members) {
		this.members = members;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getSotrageNumber() {
		return sotrageNumber;
	}

	public void setSotrageNumber(int sotrageNumber) {
		this.sotrageNumber = sotrageNumber;
	}

	public boolean isSpotTaken() {
		return spotTaken;
	}

	public void setSpotTaken(boolean spotTaken) {
		this.spotTaken = spotTaken;
	}

	public CarSpots(){
		
	}
	
}
