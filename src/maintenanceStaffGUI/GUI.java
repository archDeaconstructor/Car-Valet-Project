package maintenanceStaffGUI;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import maintenanceStaffGUI.UserInformationForParking;

public class GUI {
	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private SlidePuzzle slidePuzzle;
	String data;
	UserInformationForParking parseInfo = new UserInformationForParking();
	public static int timeNumber;
	public static JComboBox timeTag;
	
	public GUI(SlidePuzzle slidePuzzler) {
		this.slidePuzzle = slidePuzzler;
		prepareGUI();
	}

	public static void main(String[] args) {

	}

	public void prepareGUI() {
		mainFrame = new JFrame("Welcome to ACVPP");
		mainFrame.setSize(400, 400);
		mainFrame.setLayout(new GridLayout(3, 1));

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				// System.exit(0);
				mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		headerLabel = new JLabel("", JLabel.CENTER);
		statusLabel = new JLabel("", JLabel.CENTER);
		statusLabel.setSize(350, 100);

		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(statusLabel);
		mainFrame.setVisible(true);
	}

	public void resetGUI() {
		controlPanel.removeAll();
		controlPanel.revalidate();
		controlPanel.repaint();
		statusLabel.setText("");
		showTextFieldDemo();
	}

	public void thank() {
		controlPanel.removeAll();
	}

	public String showTextFieldDemo() {

		// Menu starts with enter your info
		JLabel nameLabel = new JLabel("User CC Info: ", JLabel.RIGHT);
		final JTextField userText = new JTextField(16);

		// pull up a car spot at one point
		// save info on that card spot

		JButton switchAdmin = new JButton("Admin");
		switchAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// switch to admin page
				mainFrame.setVisible(false);
				mainFrame.dispose();

				// create a parking controller class?
			}
		});
		JButton switchParking = new JButton("Parking View");
		switchParking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				slidePuzzle.main(null);
			}
		});

		JButton parkButton = new JButton("Continue to Parking");
		parkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					data = "Credit Card: " + parseInfo.returnString(userText.getText());
					statusLabel.setText(data);
					JLabel timeLabel = new JLabel("How long will you leave your vehicle with us? ", JLabel.RIGHT);
					JTextField timeInput = new JTextField(3);
					String[] timeTagOptions = { "mins", "hrs" };
					timeTag = new JComboBox(timeTagOptions);
					controlPanel.add(timeLabel);
					controlPanel.add(timeInput);
					controlPanel.add(timeTag);
					nameLabel.setVisible(false);
					userText.setVisible(false);
					switchAdmin.setVisible(false);
					switchParking.setVisible(false);
					parkButton.setVisible(false);
					JButton parkButton2 = new JButton("Confirm");
					parkButton2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							SlidePuzzleGUI.spaces--;
	           				SlidePuzzleGUI.spacesOpen.setText(""+SlidePuzzleGUI.spaces);	            		  
							int timeNumber = Integer.parseInt(timeInput.getText());
							String timeTagged = (String) timeTag.getSelectedItem();
							DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");
							Date date = new Date();
							String dateInit = dateFormat.format(date);
							saveInfo(timeNumber, timeTagged, dateInit);
							Calendar calendar = GregorianCalendar.getInstance();
							calendar.setTime(date);
							int time = (timeNumber + date.getMinutes());

							resetGUI();
							slidePuzzle.main(null);
							thank();
							if (timeTagged == "hrs") {
								int hours = (timeNumber + calendar.get(Calendar.HOUR));
								JLabel thank = new JLabel("<html>Thank you for using ACVPP! <br> Your Time Expires at: "
										+ hours + ":" + dateInit.substring(14, 16) + " " + dateInit.substring(20, 22)
										+ ".</html>", JLabel.CENTER);
								controlPanel.add(thank);
							} else if(time >= 60){
								String curr = String.format("%02d", (time-60));
								int hours = calendar.get(Calendar.HOUR);
								hours++;
								JLabel thank = new JLabel("<html>Thank you for using ACVPP! <br> Your Time Expires at: "
										+ hours + ":" + curr + " " + dateInit.substring(20, 22)
										+ ".</html>", JLabel.CENTER);
								controlPanel.add(thank);
							} else {
								String curr = String.format("%02d", time);
								JLabel thank = new JLabel("<html>Thank you for using ACVPP! <br> Your Time Expires at: "
										+ calendar.get(Calendar.HOUR) + ":" + curr + " " + dateInit.substring(20, 22)
										+ ".</html>", JLabel.CENTER);
								controlPanel.add(thank);
							}
						}
					});
					controlPanel.add(parkButton2);
				} catch (StringIndexOutOfBoundsException fix) {
					JOptionPane.showMessageDialog(mainFrame, "Invalid user input. Please swipe a debit or credit card");
					userText.setText("");
				}
			}
		});
		controlPanel.add(nameLabel);
		controlPanel.add(userText);
		// controlPanel.add(switchAdmin);
		controlPanel.add(switchParking);
		controlPanel.add(parkButton);
		mainFrame.setVisible(true);
		return (timeNumber + " " + timeTag);
	}

	public void showTextFieldExit() {

		// Menu starts with enter your info
		JLabel nameLabel = new JLabel("User CC Info: ", JLabel.RIGHT);
		final JTextField userText = new JTextField(16);

		// pull up a car spot at one point
		// save info on that card spot

		JButton switchAdmin = new JButton("Admin");
		switchAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// switch to admin page
				mainFrame.setVisible(false);
				mainFrame.dispose();
				// create a parking controller class?
			}
		});
		JButton switchParking = new JButton("Parking View");
		switchParking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				slidePuzzle.main(null);
				mainFrame.setVisible(false);
				mainFrame.dispose();
			}
		});

		JButton parkButton = new JButton("Retrieve Your Vehicle");
		parkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) throws StringIndexOutOfBoundsException {
				try {
					data = "Credit Card: " + parseInfo.returnString(userText.getText());
					statusLabel.setText(data);
					int[] location = slidePuzzle.findMyCar(parseInfo.first + " " + parseInfo.last);
					int row = location[0];
					int col = location[1];
					// System.out.print(location[0] + ", " + location[1] +
					// "Platform ID: " + location[2]);

					String timeTagged = (String) timeTag.getSelectedItem();
					if (timeNumber <= 30 && timeTagged == "mins") {
						JLabel Label = new JLabel(
								"<html>Your vehicle is being retrieved.<br> You have been charged $3.00 to your account.<br>Please Come again!</html>", JLabel.CENTER);
						controlPanel.add(Label);
					} else if (timeNumber > 30 && timeNumber < 60 && timeTagged == "mins") {
						JLabel Label = new JLabel(
								"<html>Your vehicle is being retrieved.<br> You have been charged $5.00 to your account.<br>Please Come again!</html>",JLabel.CENTER);
						controlPanel.add(Label);
					} else {
						JLabel Label = new JLabel(
								"<html>Your vehicle is being retrieved.<br> You have been charged $10.00 to your account.<br>Please Come again!</html>", JLabel.CENTER);
						controlPanel.add(Label);
					}
					slidePuzzle.removeCar(row, col);

					// replace the system print with algorithm targeting the
					// tile at this location
					nameLabel.setVisible(false);
					userText.setVisible(false);
					switchAdmin.setVisible(false);
					switchParking.setVisible(false);
					parkButton.setVisible(false);
					mainFrame.setVisible(true);

				} catch (Exception fix) {
					JOptionPane.showMessageDialog(mainFrame,
							"Invalid user input or You do not have a car parked here. Please swipe again", "Error",
							JOptionPane.ERROR_MESSAGE);
					userText.setText("");
				}
			}
		});
		controlPanel.add(nameLabel);
		controlPanel.add(userText);
		// controlPanel.add(switchAdmin);
		controlPanel.add(switchParking);
		controlPanel.add(parkButton);
		mainFrame.setVisible(true);
	}

	public void saveInfo(int timeNumber, String timeTagged, String dateInit) {
		slidePuzzle.transferName(parseInfo.first + " " + parseInfo.last, timeNumber, timeTagged, dateInit);
	}
}