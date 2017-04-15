package carParking;
import userGUI.GUI;

public class ParkingController {
	public ParkingModel model;
	public GUI view;
	
	//constructor
	ParkingController(ParkingModel model, GUI view){
		model = this.model;
		view = this.view;
		
		//add listeners to views???
		
	}
	
	public static void main(String[] args){
		GUI  swingControlDemo = new GUI();      
	    swingControlDemo.showTextFieldDemo();
	    action(swingControlDemo);
	}
	
	
	//we need to get user input from view (or our GUI in that case)
	//we need to call the model that interacts with that information
	//store resulting information 
	//tell the GUI to display the information when necessary
	
	public static void action(GUI swingControlDemo){
		String userCard = "";
		String userTime = swingControlDemo.toString();
		
		System.out.print(userTime);
	    
		/*try{
			//userCard = the credit card string   view.getUserInput()
			//userTime = the time staying
			userCard = "check";
		}catch(NumberFormatException nfex){ // fix this
			//some sort of error response
		}*/
	}
	
	//any additional features necessary?	````````````````````````````````````
}
