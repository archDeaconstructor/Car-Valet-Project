package parkingvisuals;

//The SlidePuzzle program consists of three files:
//SlidePuzzle.java      - this file with main to create window.
//SlidePuzzleGUI.java   - implements the GUI interface.
//SlidePuzzleModel.java - the logical functioning.

import javax.swing.JFrame;

public class createwindow {
	
	public static void main(String[] args) {
        
		JFrame window = new JFrame("Parking Valet");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new createvisuals());
        window.pack(); 
        window.show();  
        window.setResizable(false);
    }
}