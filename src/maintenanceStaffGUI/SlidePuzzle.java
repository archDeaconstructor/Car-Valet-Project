package maintenanceStaffGUI;
// SlidePuzzle.java - Puzzle to slide pieces to correct position.
// Fred Swartz, 2003-May, 2004-May
//   The SlidePuzzle program consists of three files:
//   SlidePuzzle.java      - this file with main to create window.
//   SlidePuzzleGUI.java   - implements the GUI interface.
//   SlidePuzzleModel.java - the logical functioning.

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;


///////////////////////////////////////////// class SlidePuzzle
class SlidePuzzle{
    //============================================= method main
	static SlidePuzzleGUI slidePuzzleGUI = new SlidePuzzleGUI();
	static JFrame window = new JFrame("Slide Puzzle");
	public void transferName(String name, int timeNumber, String timeTagged, String dateInit) {
		if (slidePuzzleGUI.isEmptyTile(4, 4) == false) {
			slidePuzzleGUI.setCarSpots(4, 4, name, timeNumber, timeTagged, dateInit);
		}
	}
	public void setAlreadyHaveNewArrival() {
		slidePuzzleGUI.setAlreadyHaveNewArrival();
	}
	public int[] findMyCar(String name) {
		return (slidePuzzleGUI.findMyCar(name));
	}
	
	public void removeCar(int row, int col){
		//for(int x=0; x<2; x++)
		slidePuzzleGUI.removeCar(row, col);
	}
	public void setEmergencyShutdownVisible(){
		slidePuzzleGUI.setEmergencyShutdownVisible();
	}
    public static void main(String[] args) {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(slidePuzzleGUI);
        window.pack();  // finalize layout
        window.show();  // make window visible
        window.setResizable(false);
    }//end main
}//endclass SlidePuzzle