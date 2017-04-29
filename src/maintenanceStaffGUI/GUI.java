package maintenanceStaffGUI;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
      JLabel  nameLabel= new JLabel("User CC Info: ", JLabel.RIGHT);
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
        		statusLabel.setText(data);
                JLabel  timeLabel= new JLabel("How long will you leave your vehicle with us? ", JLabel.RIGHT);
                Integer[] timeNumberOptions = new Integer[60];
                for (int i = 0; i < 60; i++) {
                	timeNumberOptions[i] = i+1;
                }
                JComboBox timeNumbers = new JComboBox(timeNumberOptions);
                String[] timeTagOptions = {"mins", "hrs"};
                JComboBox timeTag = new JComboBox(timeTagOptions);
               controlPanel.add(timeLabel);
               controlPanel.add(timeNumbers);
               controlPanel.add(timeTag);
               nameLabel.setVisible(false);
               userText.setVisible(false);
               switchAdmin.setVisible(false);
               switchParking.setVisible(false);
               parkButton.setVisible(false);
               JButton parkButton2 = new JButton("Confirm");
               parkButton2.addActionListener(new ActionListener() {
            	   public void actionPerformed(ActionEvent e) {
            		   int timeNumber = (int)timeNumbers.getSelectedItem();
            		   String timeTagged = (String)timeTag.getSelectedItem();
            		   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            		   Date date = new Date();
            		   String dateInit = dateFormat.format(date);
            		   saveInfo(timeNumber, timeTagged, dateInit);
            		   SlidePuzzle.main(null);
            		   mainFrame.setVisible(false);
            		   mainFrame.dispose();
            	   }
               });
               controlPanel.add(parkButton2);
        	}catch(StringIndexOutOfBoundsException fix){
        		data= "Invalid user input. Please swipe a debit or credit card";
        	}
         }
      }); 
      controlPanel.add(nameLabel);
      controlPanel.add(userText);
      controlPanel.add(switchAdmin);
      controlPanel.add(switchParking);
      controlPanel.add(parkButton);
      mainFrame.setVisible(true);  
   }
   
   public void saveInfo(int timeNumber, String timeTagged, String dateInit){
	   slidePuzzle.transferName(parseInfo.first + " " + parseInfo.last, timeNumber, timeTagged, dateInit);
   }
}