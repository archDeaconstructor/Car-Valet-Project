package userGUI;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;

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