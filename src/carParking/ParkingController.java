package carParking;
import javax.swing.JFrame;

import infoStorage.Storage;
import userGUI.GUI;


public class ParkingController {
	public ParkingModel model;
	public GUI view;
	Storage start = new Storage();
	
	//constructor
	ParkingController(ParkingModel model, GUI view){
		model = this.model;
		view = this.view;
		start.setArray();
		//add listeners to views???
	}
	
	public static void main(String[] args){
		GUI  swingControlDemo = new GUI(); 
		swingControlDemo.showTextFieldDemo();
	    actionMenu(swingControlDemo);
	}
	
	//we need to get user input from view (or our GUI in that case)
	//we need to call the model that interacts with that information
	//store resulting information 
	//tell the GUI to display the information when necessary
	
	public static void actionMenu(GUI swingControlDemo){
		//swingControlDemo.
		String userCard = "";
		String userTime = "preferable output to a different location";//swingControlDemo.addactionListener;
		
		//swingControlDemo.prepareGUI();
		
		System.out.print(userTime);
	    
		/*try{
		 * 
			//userCard = the credit card string   view.getUserInput()
			//userTime = the time staying
			userCard = "check";
		}catch(NumberFormatException nfex){ // fix this
			//some sort of error response
		}*/
	}
	
	public static void actionParking(){
		
	}
	
	public static void actionAdmin(){
		/* Idk what to do
		JFrame window = new JFrame("Slide Puzzle");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new SlidePuzzleGUI());
        window.pack();  // finalize layout
        window.show();  // make window visible
        window.setResizable(false);*/
	}
	
	
	//any additional features necessary?	````````````````````````````````````
}
