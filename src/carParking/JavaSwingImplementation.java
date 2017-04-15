package carParking;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JavaSwingImplementation {

	public static void main(String[] args) {
		JFrame frame = new JFrame("FrameDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel label1 = new JLabel();
		ImageIcon icon = new ImageIcon("car.jpg");
		label1 = new JLabel("hello world", JLabel.CENTER);
		label1.setVerticalTextPosition(JLabel.BOTTOM);
		label1.setHorizontalTextPosition(JLabel.CENTER);
		
		//label1.setVisible(true);
		//frame.getContentPane().add(new JLabel(new ImageIcon("../car.jpg")));
		frame.getContentPane().add(label1, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

}
