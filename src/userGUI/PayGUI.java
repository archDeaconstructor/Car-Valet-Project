package userGUI;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PayGUI {
	private JFrame mainFrame;
	   private JLabel headerLabel;
	   private JLabel statusLabel;
	   private JPanel controlPanel;
	   
	   private JPanel payPanel;
	
	public PayGUI(int time){
	      preparePayGUI(time);
	}

	public static void main(String[] args) {
		PayGUI  swingControlDemo = new PayGUI(0);      
	    swingControlDemo.showTextFieldDemo();
	}
	
	public void preparePayGUI(int time){
	      mainFrame = new JFrame("Payment");
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
		int price = 1;
		JLabel  namelabel= new JLabel("You owe $ "+price+"");
	    //final JTextField userText = new JTextField(16);
		
	    JButton exitButton = new JButton("Back to Menu");
	    JButton payButton = new JButton("Pay Now");
	    
	    exitButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {  
	        	 mainFrame.setVisible(false);
	   			 mainFrame.dispose();
	         }
	      }); 
        payButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { //prices refer here 
            	String data = "Thank you for Choosing ACVPP!"; 	
            	statusLabel.setText(data);
            	controlPanel.remove(payButton);
   			 	controlPanel.add(exitButton);
            }
         });
	      
	      controlPanel.add(namelabel);
	      controlPanel.add(payButton);
	      
	      mainFrame.setVisible(true); 
	}
}
