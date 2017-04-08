package userGUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class GUI {
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;
  

   public GUI(){
      prepareGUI();
   }
   public static void main(String[] args){
      GUI  swingControlDemo = new GUI();      
      swingControlDemo.showTextFieldDemo();
   }
   private void prepareGUI(){
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
   private void showTextFieldDemo(){ 

      JLabel  namelabel= new JLabel("User CC Info: ", JLabel.RIGHT);
      final JTextField userText = new JTextField(16);    

      JButton parkButton = new JButton("Continue");
      parkButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {     
            String data = "Credit Card: " + userText.getText(); 
            statusLabel.setText(data);
            JButton fiveButton = new JButton("Five Mins");
            parkButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {     
                   String time = "User All day ";
                   statusLabel.setText(time); 
                   controlPanel.add(fiveButton);
                }
             });
            JButton tenButton = new JButton("Half a Day");
            parkButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {     
                   String time = "Username " + userText.getText(); 
                   statusLabel.setText(time);  
                   controlPanel.add(tenButton);
                }
             });
            JButton dayButton = new JButton("All Day");
            parkButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {     
                   String time = "Username " + userText.getText(); 
                   statusLabel.setText(time); 
                   controlPanel.add(dayButton);
                }
             });
         }
      }); 
      controlPanel.add(namelabel);
      controlPanel.add(userText);
      controlPanel.add(parkButton);
      
      controlPanel.add(parkButton);
      controlPanel.add(parkButton);
      mainFrame.setVisible(true);  
   }
}