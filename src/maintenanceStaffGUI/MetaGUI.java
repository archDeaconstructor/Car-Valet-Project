package maintenanceStaffGUI;
// SlidePuzzle.java - Puzzle to slide pieces to correct position.
// Fred Swartz, 2003-May, 2004-May
//   The SlidePuzzle program consists of three files:
//   SlidePuzzle.java      - this file with main to create window.
//   SlidePuzzleGUI.java   - implements the GUI interface.
//   SlidePuzzleModel.java - the logical functioning.

import javax.swing.JFrame;

///////////////////////////////////////////// class SlidePuzzle
class MetaGUI {
    //============================================= method main
	static SlidePuzzle slidePuzzle = new SlidePuzzle();
    public static void main(String[] args) {
    	slidePuzzle.main(null);
    	slidePuzzle.window.setVisible(true);
//    	GUI ExitGUI = new GUI(slidePuzzle);
//		ExitGUI.showTextFieldExit();
    }//end main
}//endclass SlidePuzzle33332255