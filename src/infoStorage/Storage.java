package infoStorage;

import java.util.ArrayList;

public class Storage {

	CarSpots[][] carSpots = new CarSpots[5][5];
	
	public Storage(){
		//setArray(); //May or may not need this
	}
	
	public void setArray(){
		int x=0;int y=0;int z=0;int temp=0;
		for(x =0; x<5; x++){
			for(y=0; y<5; y++){
				z++;
				carSpots[x][y].setSotrageNumber(z);
				carSpots[x][y].setSpotTaken(false);
				//carSpots[x][y].setMembers(string);
				//carSpots[x][y].setTime(int);
			}
		}
	}

}
