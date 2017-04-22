package userGUI;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.*;

import parkingvisuals.createvisuals;
import parkingvisuals.createwindow;
 
public class GUI {
   public JFrame mainFrame;
   public JLabel headerLabel;
   public JLabel statusLabel;
   public JPanel controlPanel;
  

   public GUI(){
      prepareGUI();
   }
   public static void main(String[] args){
      GUI  swingControlDemo = new GUI();      
      swingControlDemo.showTextFieldDemo();
   }
   public void prepareGUI(){
      mainFrame = new JFrame("Welcome");
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
   
   private static String returnString(String username){
		//Scanner
				Scanner sc = new Scanner(System.in);
				int cardnumber =0;
				//String username = new String();
				//System.out.print("Please enter your card \n");
				
				//System.out.print(splitString());
				
				String test = sc.next();
				String[] piece1 = test.split(Pattern.quote("^"));
				
				String cardNumber = piece1[0];
				cardNumber = cardNumber.substring(2, 17);
				
				String FullName	  = piece1[1];
				String[] piece2 = FullName.split(Pattern.quote("/"));
				String firstName  = piece2[0];
				String lastName   = piece2[1];
				
				String Value = "Thank you "+firstName+" "+lastName+"\nFor the sake of Demonstration "
						+ "your card number is "+cardNumber;
		return Value;
	}
   
   public void showTextFieldDemo(){ 

      JLabel  namelabel= new JLabel("User CC Info: ", JLabel.RIGHT);
      final JTextField userCC = new JTextField(16);
      
      JButton fiveButton = new JButton("Five Mins");
      fiveButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
        	  Date arrival = new Date();
              String duration = "5 minutes";
             String time = "User selected 5 Minutes";
             
          }
       });
      JButton tenButton = new JButton("10 Minutes");
      tenButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {     
        	  Date arrival = new Date();
              String duration = "10 minutes";
             String time = "User selected 10 Minutes";
          }
       });
      
      JButton dayButton = new JButton("All Day");
      dayButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {     
        	  Date arrival = new Date();
              String duration = "All Day";
             String time = "User selected All Day";
             JFrame window = new JFrame("Parking Valet");
             window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             window.setContentPane(new createvisuals());
             window.pack(); 
             window.show();  
             window.setResizable(false);
          }
       });

      JButton parkButton = new JButton("Continue");
      parkButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 String data = "Credit Card: " + userCC.getText();
//            String data = "Credit Card: " + returnString(userCC.getText()); 
            statusLabel.setText(data);
            controlPanel.remove(parkButton);
            controlPanel.add(fiveButton);
            controlPanel.add(tenButton);
            controlPanel.add(dayButton);
            
            
            
         }
      });
      
      controlPanel.add(namelabel);
      controlPanel.add(userCC);
      controlPanel.add(parkButton);
      mainFrame.setVisible(true);  
   }
}