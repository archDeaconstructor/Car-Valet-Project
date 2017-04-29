package maintenanceStaffGUI;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import maintenanceStaffGUI.UserInformationForParking;
 
public class GUI {
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;
   private SlidePuzzle slidePuzzle = new SlidePuzzle();
   String data;
   UserInformationForParking parseInfo = new UserInformationForParking();
  

   public GUI(){
      prepareGUI();
   }
   public static void main(String[] args){
      GUI  swingControlDemo = new GUI();      
      swingControlDemo.showTextFieldDemo();
   }
   public void prepareGUI(){
      mainFrame = new JFrame("Welcome to AVCPP");
      mainFrame.setSize(400,400);
      mainFrame.setLayout(new GridLayout(3, 1));
      
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
      headerLabel = new JLabel("", JLabel.CENTER);        
      statusLabel = new JLabel("",JLabel.CENTER);    
      statusLabel.setSize(350,100);

      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(statusLabel);
      mainFrame.setVisible(true);  
   }
   public void showTextFieldDemo(){ 

	 //Menu starts with enter your info
      JLabel  namelabel= new JLabel("User CC Info: ", JLabel.RIGHT);
      final JTextField userText = new JTextField(16);   
   
      //pull up a car spot at one point
      //save info on that card spot
      
      JButton switchAdmin = new JButton("Admin");
      switchAdmin.addActionListener(new ActionListener(){
    	 public void actionPerformed(ActionEvent e){
    		//switch to admin page
			 mainFrame.setVisible(false);
			 mainFrame.dispose();
			 
			 
			 //create a parking controller class?
    	 }
      });
      JButton switchParking = new JButton("Parking View");
      switchParking.addActionListener(new ActionListener(){
     	 public void actionPerformed(ActionEvent e) {
     		 SlidePuzzle.main(null);
     		 mainFrame.setVisible(false);
			 mainFrame.dispose();
     	 }
      });

      
      JButton parkButton = new JButton("Continue");
      parkButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {  
        	try{
        		data= "Credit Card: " + parseInfo.returnString(userText.getText());
        		saveInfo();
        	}catch(StringIndexOutOfBoundsException fix){
        		data= "Invalid user input. Please swipe a debit or credit card";
        	}
            statusLabel.setText(data);
            JLabel  timeLabel= new JLabel("How long will you leave your vehicle with us? ", JLabel.RIGHT);
            final JTextField userTime = new JTextField(3);
            String[] timeTagOptions = {" mins", " hrs"};
            JComboBox timeTag = new JComboBox(timeTagOptions);
            JButton fiveButton = new JButton("Five Mins");
            fiveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {     
                   String time = "5 Minutes";
                   statusLabel.setText(data + " | Staying: " + time);
                }
             });
            controlPanel.add(fiveButton);
            JButton tenButton = new JButton("Half a Day");
            tenButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {     
                   String time = "12 Hours"; 
                   statusLabel.setText(data+ " | Staying: " + time);
                }
             });
            controlPanel.add(tenButton);
            JButton dayButton = new JButton("All Day");
            dayButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {     
                   String time = "Whole Day"; 
                   statusLabel.setText(data + " | Staying: " + time);
                }
             });
            controlPanel.add(dayButton);
         }
      }); 
      controlPanel.add(namelabel);
      controlPanel.add(userText);
      controlPanel.add(switchAdmin);
      controlPanel.add(switchParking);
      controlPanel.add(parkButton);
      
      controlPanel.add(parkButton);
      controlPanel.add(parkButton);
      mainFrame.setVisible(true);  
   }
   
   public void saveInfo(){
	   slidePuzzle.transferName(parseInfo.first + " " + parseInfo.last);
   }
}