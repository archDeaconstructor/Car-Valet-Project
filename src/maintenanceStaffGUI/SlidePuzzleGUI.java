package maintenanceStaffGUI;

//SlidePuzzleGUI.java - GUI for SlidePuzzle
//Fred Swartz, 2003-May-10, 2004-May-3
//
//The SlidePuzzleGUI class creates a panel which 
//  contains two subpanels.
//  1. In the north is a subpanel for controls (just a button now).
//  2. In the center a graphics
//This needs a few improvements.  
//Both the GUI and Model define the number or rows and columns.
//       How would you set both from one place? 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import maintenanceStaffGUI.SlidePuzzleModel.CarSpots;

 

/////////////////////////////////////////////////// class SlidePuzzleGUI
//This class contains all the parts of the GUI interface
class SlidePuzzleGUI extends JPanel {
	//=============================================== instance variables
	private GraphicsPanel    _puzzleGraphics;
	public SlidePuzzleModel _puzzleModel = new SlidePuzzleModel();
	public static int spaces = 24;
	String infoDumpText = ("-");
	String infoDumpTextShutdown = (infoDumpText + "\nAVCPP is currently shut down. Press RESUME OPERATIONS"
			+ " to resume normal operations.");
	JTextArea infoDump = new JTextArea(infoDumpText, 44, 60);
	static JLabel  spacesOpen = new JLabel(""+spaces);
	JButton openSpots= new JButton("There are "+emptyCount()+ " spots open");
	JButton getInfo= new JButton("Call Your Car");
	JButton arrival= new JButton("New Arrival");
	JButton moveNorth= new JButton("Move North");
	JButton moveSouth= new JButton("Move South");
	JButton moveWest= new JButton("Move West");
	JButton moveEast= new JButton("Move East");
	JButton emergencyShutdown= new JButton("EMERGENCY SHUTDOWN");
	private boolean stillRunning = true;
	//end instance variables
	
	void setCarSpots(int row, int col, String name, int timeNumber, String timeTagged, String dateInit) {
		_puzzleModel.setName(row, col, name);
		_puzzleModel.setSpotTaken(row, col);
		_puzzleModel.setTime(row, col, timeNumber, timeTagged);
		_puzzleModel.setTimeSince(row, col, dateInit);
	}//end getName
	
	boolean isEmptyTile(int row, int col) {
		return _puzzleModel.isEmptyTile(row, col);
	}
	
	public int[] findMyCar(String name) {
		int[] rowCol = new int[3];
		boolean found = false;
		for (int i = 0; i < 5; i++) {
			if (found == false) {
				for (int j = 0; j < 5; j++) {
					if ((_puzzleModel.getName(i, j)).equals(name) == true) {
						rowCol[0] = i;
						rowCol[1] = j;
						rowCol[2] = Integer.parseInt(_puzzleModel.getStorageNumber(i, j));
						found = true;
					}
				}
			}
		}
		if (found == true) {
			return rowCol;
		} else {
			return null;
		}
	}
	//====================================================== constructor
	public SlidePuzzleGUI() {
		//--- Create buttons and text fields
		arrival.addActionListener(new newArrival());
		getInfo.addActionListener(new gettingInfo());
		moveNorth.addActionListener(new movingNorth());
		moveNorth.setVisible(false);
		moveSouth.addActionListener(new movingSouth());
		moveSouth.setVisible(false);
		moveWest.addActionListener(new movingWest());
		moveWest.setVisible(false);
		moveEast.addActionListener(new movingEast());
		moveEast.setVisible(false);
		emergencyShutdown.addActionListener(new shuttingDown());
		infoDump.setEditable(false);
		
		//--- Create control panel
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		
		controlPanel.add(spacesOpen);
		controlPanel.add(arrival);
		controlPanel.add(getInfo);
		controlPanel.add(moveNorth);
		controlPanel.add(moveSouth);
		controlPanel.add(moveWest);
		controlPanel.add(moveEast);
		controlPanel.add(emergencyShutdown);
		
		//--- Create graphics panel
		_puzzleGraphics = new GraphicsPanel();
		
		//--- Create info panel
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new FlowLayout());
		infoPanel.add(infoDump);
		
		//--- Set the layout and add the components
		this.setLayout(new BorderLayout());
		this.add(controlPanel, BorderLayout.NORTH);
		this.add(_puzzleGraphics, BorderLayout.CENTER);
		this.add(infoPanel, BorderLayout.EAST);
	}//end constructor
	
	
	//////////////////////////////////////////////// class GraphicsPanel
	// This is defined inside the outer class so that
	// it can use the outer class instance variables.
	class GraphicsPanel extends JPanel implements MouseListener {
		private static final int ROWS = 6;
		private static final int COLS = 5;
		private boolean canGetInfo = false;
		private boolean canForceCarLeave = false;
		
		private static final int CELL_SIZE = 90; // Pixels
		private Font _biggerFont;
		
		
		//================================================== constructor
		public GraphicsPanel() {
			_biggerFont = new Font("SansSerif", Font.BOLD, CELL_SIZE/4);
			this.setPreferredSize(new Dimension(CELL_SIZE * COLS, CELL_SIZE*ROWS));
			this.setBackground(Color.black);
			this.addMouseListener(this);  // Listen own mouse events.
		}//end constructor
		
		//=======================================x method paintComponent
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			/*_contents[8][0] = new Tile(8, 0, "Enter");
			for (int c=1; c<COLS-1; c++) {
				_contents[8][c] = new Tile(8, c, "------");
			}
			_contents[8][7] = new Tile(8, 7, "Exit");
			*/
			for (int r=0; r<ROWS-1; r++) {
				for (int c=0; c<COLS; c++) {
					int x = c * CELL_SIZE;
					int y = r * CELL_SIZE;
					String text = _puzzleModel.getStorageNumber(r, c);
					if (text != null) {
						if(_puzzleModel.getSpotTaken(r, c)){
							g.setColor(Color.red);					
							g.fillRect(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);						
							g.setColor(Color.black);						
							g.setFont(_biggerFont);						
							g.drawString(text, x + 20, y + (3 * CELL_SIZE) / 4);
							
						} else {
						g.setColor(Color.gray);
						g.fillRect(x+2, y+2, CELL_SIZE-4, CELL_SIZE-4);
						g.setColor(Color.black);
						g.setFont(_biggerFont);
						g.drawString(text, x+20, y+(3*CELL_SIZE)/4);
						}
					}
				}
			}
			// paints ENTRY square
			g.setColor(Color.darkGray);
			g.fillRect(2, (5*CELL_SIZE)+2, CELL_SIZE-4, CELL_SIZE-4);
			g.setColor(Color.black);
			g.setFont(_biggerFont);
			g.drawString(_puzzleModel.getStorageNumber(5, 0), 20, (5*CELL_SIZE)+(3*CELL_SIZE)/4);
			
			// paints walls in bottom row
			for (int c=1; c<COLS-1; c++) {
				int x = c * CELL_SIZE;
				String text = _puzzleModel.getStorageNumber(5, c);
				if (text != null) {
					g.setColor(Color.black);
					g.fillRect(x+2, (5*CELL_SIZE)+2, CELL_SIZE-4, CELL_SIZE-4);
				}
			}
			// paints EXIT square
			g.setColor(Color.darkGray);
			g.fillRect(4*CELL_SIZE+2, (5*CELL_SIZE)+2, CELL_SIZE-4, CELL_SIZE-4);
			g.setColor(Color.black);
			g.setFont(_biggerFont);
			g.drawString(_puzzleModel.getStorageNumber(5, 4), (4*CELL_SIZE)+20, (5*CELL_SIZE)+(3*CELL_SIZE)/4);
		}//end paintComponent
		//toggles whether or not clicking a given platform gives you information
		public void toggleGetInfo() {
			if (canGetInfo == false) {
				canGetInfo = true;
			} else {
				canGetInfo = false;
			}
		}//end toggleGetInfo
		public void toggleCarForceLeave() {
			if (canForceCarLeave == false) {
				canForceCarLeave = true;
			} else {
				canForceCarLeave = false;
			}
		}//end toggleGetInfo
		public int[] findMyCar(String name) {
			int[] rowCol = new int[2];
			boolean found = false;
			for (int i = 0; i < ROWS - 1; i++) {
				for (int j = 0; j < COLS - 1; j++) {
					if ((_puzzleModel.getName(i, j)).equals(name) == true) {
						rowCol[0] = i;
						rowCol[1] = j;
						found = true;
					}
				}
			}
			if (found == true) {
				return rowCol;
			} else {
				return null;
			}
		}
		//======================================== listener mousePressed
		public void mousePressed(MouseEvent e) {
			int col = e.getX()/CELL_SIZE;
            int row = e.getY()/CELL_SIZE;
            if(_puzzleModel.getStorageNumber(row, col).equals("Error! This is a wall.")) {
            	// do nothing
            } else if (_puzzleModel.getSpotTaken(row, col)==true) {
				
	            String setAsInfoDumpText = "Platform No: " + _puzzleModel.getStorageNumber(row, col) +
	            		"\nPosition: " + (col+1) + ", " + (row+1) + "\nOccupied: " + _puzzleModel.getSpotTaken(row, col) + "\nCar Owner: " +
	            		_puzzleModel.getName(row, col) + "\nTime Due: " + _puzzleModel.getTime(row, col) +
	            		"\nSpot Taken Since: " + _puzzleModel.getTimeSince(row, col);
	            infoDumpText = setAsInfoDumpText;
	            if (stillRunning == true) {
	            	infoDump.setText(infoDumpText);
	            } else {
	            	String infoDumpTextShutdown = (infoDumpText + "\nAVCPP is currently shut down. "
	            			+ "Press RESUME OPERATIONS to resume normal operations.");
	            	infoDump.setText(infoDumpTextShutdown);
	            }
	            toggleGetInfo();
	            if (stillRunning == false) {
	            }
			} else {
				if(col == 4 && row == 4){
					// done
				}else if(_puzzleModel._emptyTile.getCol()>col){
					for (int i = _puzzleModel._emptyTile.getCol();i>col;i--){
						_puzzleModel.moveTileWest();
						_puzzleGraphics.repaint();
						
						}
						for (int i = _puzzleModel._emptyTile.getRow();i>row;i--){
							_puzzleModel.moveTileNorth();
							_puzzleGraphics.repaint();
						}
						for (int i = _puzzleModel._emptyTile.getRow();i<3;i++){
						_puzzleModel.moveTileEast();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileSouth();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileSouth();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileWest();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileNorth();
						_puzzleGraphics.repaint();
						}
						_puzzleModel.moveTileEast();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileSouth();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileWest();
						_puzzleGraphics.repaint();
						for (int i = 0;i<3-col;i++){
							_puzzleModel.moveTileNorth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileEast();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileEast();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
							}
				} else if (col == 4) {
					for (int i = _puzzleModel._emptyTile.getCol();i<col;i++){
						_puzzleModel.moveTileEast();
						_puzzleGraphics.repaint();
						}
					for (int i = _puzzleModel._emptyTile.getRow();i>row;i--){
						_puzzleModel.moveTileNorth();
						_puzzleGraphics.repaint();
					}
					for (int i = _puzzleModel._emptyTile.getRow();i<3;i++){
						_puzzleModel.moveTileWest();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileSouth();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileSouth();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileEast();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileNorth();
						_puzzleGraphics.repaint();
						}
//				} else if (col == 0) {
//					for (int i = _puzzleModel._emptyTile.getCol();i>=col;i--){
//						_puzzleModel.moveTileWest();
//						_puzzleGraphics.repaint();
//						}
//					for (int i = _puzzleModel._emptyTile.getRow();i>row;i--){
//						_puzzleModel.moveTileNorth();
//						_puzzleGraphics.repaint();
//					}
//					for (int i = _puzzleModel._emptyTile.getRow();i<3;i++){
//						_puzzleModel.moveTileEast();
//						_puzzleGraphics.repaint();
//						_puzzleModel.moveTileSouth();
//						_puzzleGraphics.repaint();
//						_puzzleModel.moveTileSouth();
//						_puzzleGraphics.repaint();
//						_puzzleModel.moveTileWest();
//						_puzzleGraphics.repaint();
//						_puzzleModel.moveTileNorth();
//						_puzzleGraphics.repaint();
//						}
//					for (int i = 0;i<3-col;i++){
//						_puzzleModel.moveTileNorth();
//						_puzzleGraphics.repaint();
//						_puzzleModel.moveTileEast();
//						_puzzleGraphics.repaint();
//						_puzzleModel.moveTileEast();
//						_puzzleGraphics.repaint();
//						_puzzleModel.moveTileSouth();
//						_puzzleGraphics.repaint();
//						_puzzleModel.moveTileWest();
//						_puzzleGraphics.repaint();
//						}
					
				} else {
				for (int i = _puzzleModel._emptyTile.getCol();i<col;i++){
					
				_puzzleModel.moveTileEast();
				_puzzleGraphics.repaint();
				}
				for (int i = _puzzleModel._emptyTile.getRow();i>row;i--){
					_puzzleModel.moveTileNorth();
					_puzzleGraphics.repaint();
				}
				for (int i = _puzzleModel._emptyTile.getRow();i<3;i++){
				_puzzleModel.moveTileEast();
				_puzzleGraphics.repaint();
				_puzzleModel.moveTileSouth();
				_puzzleGraphics.repaint();
				_puzzleModel.moveTileSouth();
				_puzzleGraphics.repaint();
				_puzzleModel.moveTileWest();
				_puzzleGraphics.repaint();
				_puzzleModel.moveTileNorth();
				_puzzleGraphics.repaint();
				}
				_puzzleModel.moveTileEast();
				_puzzleGraphics.repaint();
				_puzzleModel.moveTileSouth();
				_puzzleGraphics.repaint();
				_puzzleModel.moveTileWest();
				_puzzleGraphics.repaint();
				for (int i = 0;i<3-col;i++){
					_puzzleModel.moveTileNorth();
					_puzzleGraphics.repaint();
					_puzzleModel.moveTileEast();
					_puzzleGraphics.repaint();
					_puzzleModel.moveTileEast();
					_puzzleGraphics.repaint();
					_puzzleModel.moveTileSouth();
					_puzzleGraphics.repaint();
					_puzzleModel.moveTileWest();
					_puzzleGraphics.repaint();
					}
				}
//			_puzzleModel.getCarSpots(row, col).setColor(Color.blue);
//				spaces--;
//				spacesOpen.setText(""+spaces);
			
			GUI EntranceGUI = new GUI(MetaGUI.slidePuzzle);
			EntranceGUI.showTextFieldDemo();
				
			}
			if (canForceCarLeave == true) {
				
	            String setAsInfoDumpText = "Platform No: " + _puzzleModel.getStorageNumber(row, col) +
	            		"\nPosition: " + (col+1) + ", " + (row+1) + "\nOccupied: " + _puzzleModel.getSpotTaken(row, col) + "\nCar Owner: " +
	            		_puzzleModel.getName(row, col) + "\nTime Due: " + _puzzleModel.getTime(row, col) +
	            		"\nSpot Taken Since: " + _puzzleModel.getTimeSince(row, col);
	            infoDumpText = setAsInfoDumpText;
	            if (stillRunning == true) {
	            	infoDump.setText(infoDumpText);
	            } else {
	            	String infoDumpTextShutdown = (infoDumpText + "\nAVCPP is currently shut down. "
	            			+ "Press RESUME OPERATIONS to resume normal operations.");
	            	infoDump.setText(infoDumpTextShutdown);
	            }
	            toggleGetInfo();
	            if (stillRunning == false) {
	            	getInfo.setVisible(true);
	            }
			}
		}//end mousePressed	
		
		//========================================== ignore these events
		public void mouseClicked (MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered (MouseEvent e) {}
		public void mouseExited  (MouseEvent e) {}
	}//end class GraphicsPanel
	
	////////////////////////////////////////// inner class gettingInfo
	public class newArrival implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			outerloop:
			for (int r=0; r<5; r++) {
				for (int c=0; c<5; c++) {
					if(!_puzzleModel.getSpotTaken(r, c)){
						if(c == 4 && r == 4){
							// done
						}else if(_puzzleModel._emptyTile.getCol()>c){
							for (int i = _puzzleModel._emptyTile.getCol();i>c;i--){
								_puzzleModel.moveTileWest();
								_puzzleGraphics.repaint();
								
								}
								for (int i = _puzzleModel._emptyTile.getRow();i>r;i--){
									_puzzleModel.moveTileNorth();
									_puzzleGraphics.repaint();
								}
								for (int i = _puzzleModel._emptyTile.getRow();i<3;i++){
								_puzzleModel.moveTileEast();
								_puzzleGraphics.repaint();
								_puzzleModel.moveTileSouth();
								_puzzleGraphics.repaint();
								_puzzleModel.moveTileSouth();
								_puzzleGraphics.repaint();
								_puzzleModel.moveTileWest();
								_puzzleGraphics.repaint();
								_puzzleModel.moveTileNorth();
								_puzzleGraphics.repaint();
								}
								_puzzleModel.moveTileEast();
								_puzzleGraphics.repaint();
								_puzzleModel.moveTileSouth();
								_puzzleGraphics.repaint();
								_puzzleModel.moveTileWest();
								_puzzleGraphics.repaint();
								for (int i = 0;i<3-c;i++){
									_puzzleModel.moveTileNorth();
									_puzzleGraphics.repaint();
									_puzzleModel.moveTileEast();
									_puzzleGraphics.repaint();
									_puzzleModel.moveTileEast();
									_puzzleGraphics.repaint();
									_puzzleModel.moveTileSouth();
									_puzzleGraphics.repaint();
									_puzzleModel.moveTileWest();
									_puzzleGraphics.repaint();
									}
						} else if (c == 4) {
							for (int i = _puzzleModel._emptyTile.getCol();i<c;i++){
								_puzzleModel.moveTileEast();
								_puzzleGraphics.repaint();
								}
							for (int i = _puzzleModel._emptyTile.getRow();i>r;i--){
								_puzzleModel.moveTileNorth();
								_puzzleGraphics.repaint();
							}
							for (int i = _puzzleModel._emptyTile.getRow();i<3;i++){
								_puzzleModel.moveTileWest();
								_puzzleGraphics.repaint();
								_puzzleModel.moveTileSouth();
								_puzzleGraphics.repaint();
								_puzzleModel.moveTileSouth();
								_puzzleGraphics.repaint();
								_puzzleModel.moveTileEast();
								_puzzleGraphics.repaint();
								_puzzleModel.moveTileNorth();
								_puzzleGraphics.repaint();
								}
//						} else if (col == 0) {
//							for (int i = _puzzleModel._emptyTile.getCol();i>=col;i--){
//								_puzzleModel.moveTileWest();
//								_puzzleGraphics.repaint();
//								}
//							for (int i = _puzzleModel._emptyTile.getRow();i>row;i--){
//								_puzzleModel.moveTileNorth();
//								_puzzleGraphics.repaint();
//							}
//							for (int i = _puzzleModel._emptyTile.getRow();i<3;i++){
//								_puzzleModel.moveTileEast();
//								_puzzleGraphics.repaint();
//								_puzzleModel.moveTileSouth();
//								_puzzleGraphics.repaint();
//								_puzzleModel.moveTileSouth();
//								_puzzleGraphics.repaint();
//								_puzzleModel.moveTileWest();
//								_puzzleGraphics.repaint();
//								_puzzleModel.moveTileNorth();
//								_puzzleGraphics.repaint();
//								}
//							for (int i = 0;i<3-col;i++){
//								_puzzleModel.moveTileNorth();
//								_puzzleGraphics.repaint();
//								_puzzleModel.moveTileEast();
//								_puzzleGraphics.repaint();
//								_puzzleModel.moveTileEast();
//								_puzzleGraphics.repaint();
//								_puzzleModel.moveTileSouth();
//								_puzzleGraphics.repaint();
//								_puzzleModel.moveTileWest();
//								_puzzleGraphics.repaint();
//								}
							
						} else {
						for (int i = _puzzleModel._emptyTile.getCol();i<c;i++){
							
						_puzzleModel.moveTileEast();
						_puzzleGraphics.repaint();
						}
						for (int i = _puzzleModel._emptyTile.getRow();i>r;i--){
							_puzzleModel.moveTileNorth();
							_puzzleGraphics.repaint();
						}
						for (int i = _puzzleModel._emptyTile.getRow();i<3;i++){
						_puzzleModel.moveTileEast();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileSouth();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileSouth();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileWest();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileNorth();
						_puzzleGraphics.repaint();
						}
						_puzzleModel.moveTileEast();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileSouth();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileWest();
						_puzzleGraphics.repaint();
						for (int i = 0;i<3-c;i++){
							_puzzleModel.moveTileNorth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileEast();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileEast();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
							}
						}
						
						GUI EntranceGUI = new GUI(MetaGUI.slidePuzzle);
						EntranceGUI.showTextFieldDemo();
//					_puzzleModel.getCarSpots(row, col).setColor(Color.blue);
						break outerloop;
					}
				}
				}
		}
	}
	public class gettingInfo implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			GUI ExitGUI = new GUI(MetaGUI.slidePuzzle);
			ExitGUI.showTextFieldExit();
		}
	}//end inner class gettingInfo
	public class movingNorth implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			_puzzleModel.moveTileNorth();
			_puzzleGraphics.repaint();
		}
	}//end inner class movingNorth
	public class movingSouth implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			_puzzleModel.moveTileSouth();
			_puzzleGraphics.repaint();
		}
	}//end inner class movingSouth
	public class movingWest implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			_puzzleModel.moveTileWest();
			_puzzleGraphics.repaint();
		}
	}//end inner class movingWest
	public class movingEast implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			_puzzleModel.moveTileEast();
			_puzzleGraphics.repaint();
		}
	}//end inner class movingEast
	public class carLeaving implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String entryName = _puzzleModel.getName(4, 0);
			if (entryName.equals("N/A") == false && entryName.equals("Unoccupied") == false) {
				_puzzleModel.restoreDefaults(4, 0);
				_puzzleGraphics.repaint();
			}
		}
	}//end inner class carLeaving
	public class carForceLeaving implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			_puzzleGraphics.toggleCarForceLeave();
			getInfo.setVisible(false);
		}
	}//end inner class carForceLeaving
	public class shuttingDown implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (stillRunning == true) {
				emergencyShutdown.setText("RESUME OPERATIONS");
				String infoDumpTextShutdown = (infoDumpText + "\nAVCPP is currently shut down. Press RESUME"
						+ " OPERATIONS to resume normal operations.");
				infoDump.setText(infoDumpTextShutdown);
				infoDump.setBackground(Color.ORANGE);
				stillRunning = false;
				moveNorth.setVisible(true);
				moveSouth.setVisible(true);
				moveWest.setVisible(true);
				moveEast.setVisible(true);
			} else {
				emergencyShutdown.setText("EMERGENCY SHUTDOWN");
				infoDump.setText(infoDumpText);
				infoDump.setBackground(Color.WHITE);
				stillRunning = true;
				getInfo.setVisible(true);
				moveNorth.setVisible(false);
				moveSouth.setVisible(false);
				moveWest.setVisible(false);
				moveEast.setVisible(false);
			}
		}
	}//end inner class shuttingDown
	
	public int count(){
		int count = 24;
		for (int r=0; r<5; r++) {
			for (int c=0; c<5; c++) {
				if(_puzzleModel.getSpotTaken(r,c)){
					count--;
				}
			}
		}
		return count;
		
	}
	
	public void removeCar(int row, int col){
		Timer time = new Timer();
		
		//basic delay for 1 second
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				//implemented task we want
			}
		};
		time.schedule(task, 1000);
		
		
		
		TimerTask task1 = new TimerTask() {
			@Override
			public void run() {
				_puzzleModel.moveTileSouth();
				_puzzleModel.moveTileSouth();
			}
		};
		time.schedule(task1, 1000);
		
			
		if(row == 4 && col == 0){
		// in spot needed do nothing	
			
		// in col 0
		}else if(row!=4&&col==0 ){
			// blank above same col
			if(_puzzleModel._emptyTile.getRow()<row&&_puzzleModel._emptyTile.getCol()==0){
				for(int i = _puzzleModel._emptyTile.getRow();i<row+1;i++){
					TimerTask task2 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task2, 1000);
					
				}
				
				for(int i =3 - _puzzleModel._emptyTile.getRow();i>0;i--){
					TimerTask task3 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileEast();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileNorth();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task3, 1000);
					
				}
				// blank under same colom
			} else if (_puzzleModel._emptyTile.getRow()>row&&_puzzleModel._emptyTile.getCol()==0){
				TimerTask task4 = new TimerTask() {
					@Override
					public void run() {
						_puzzleModel.moveTileNorth();
						_puzzleGraphics.repaint();
					}
				};
				time.schedule(task4, 1000);
				
				for(int i =3 - _puzzleModel._emptyTile.getRow();i>0;i--){
					TimerTask task5 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileEast();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileNorth();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task5, 1000);
					
				}
				// blank over to right
			} else if (_puzzleModel._emptyTile.getRow()<row&&_puzzleModel._emptyTile.getCol()>0){
				for(int i = _puzzleModel._emptyTile.getCol();i>col+1;i--){
					TimerTask task6 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task6, 1000);
					
				}
				for(int i = _puzzleModel._emptyTile.getRow();i<row+1;i++){
					TimerTask task7 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task7, 1000);
					
				}
				TimerTask task8 = new TimerTask() {
					@Override
					public void run() {
						_puzzleModel.moveTileWest();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileNorth();
						_puzzleGraphics.repaint();
					}
				};
				time.schedule(task8, 1000);
				
				for(int i =3 - _puzzleModel._emptyTile.getRow();i>0;i--){
					TimerTask task9 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileEast();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileNorth();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task9, 1000);
					
				}
				// blank under to right
			} else if (_puzzleModel._emptyTile.getRow()<row&&_puzzleModel._emptyTile.getCol()>0){
				for(int i = _puzzleModel._emptyTile.getCol();i>col+1;i--){
					TimerTask task10 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task10, 1000);
					
				}
				for(int i = _puzzleModel._emptyTile.getRow();i>row+1;i--){
					TimerTask task11 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileNorth();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task11, 1000);
					
				}
				TimerTask task12 = new TimerTask() {
					@Override
					public void run() {
						_puzzleModel.moveTileWest();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileNorth();
						_puzzleGraphics.repaint();
					}
				};
				time.schedule(task12, 1000);
				
				for(int i =3 - _puzzleModel._emptyTile.getRow();i>0;i--){
					TimerTask task13 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileEast();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileNorth();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task13, 1000);
					
				}
				//blank same row to the right
			} else if (_puzzleModel._emptyTile.getRow()==row&&_puzzleModel._emptyTile.getCol()>0){
				TimerTask task14 = new TimerTask() {
					@Override
					public void run() {
						_puzzleModel.moveTileSouth();
						_puzzleGraphics.repaint();
					}
				};
				time.schedule(task14, 1000);
				
				for(int i = _puzzleModel._emptyTile.getCol();i>col;i--){
					TimerTask task15 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task15, 1000);
					
				}
				
				TimerTask task16 = new TimerTask() {
					@Override
					public void run() {
						_puzzleModel.moveTileNorth();
						_puzzleGraphics.repaint();
					}
				};
				time.schedule(task16, 1000);
				
				for(int i =3 - _puzzleModel._emptyTile.getRow();i>0;i--){
					TimerTask task17 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileEast();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileNorth();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task17, 1000);
					
				}
				
			}
		} else if(_puzzleModel._emptyTile.getCol()>col&& row==4){
			for(int i = _puzzleModel._emptyTile.getRow();i<3;i++){
				TimerTask task18 = new TimerTask() {
					@Override
					public void run() {
						_puzzleModel.moveTileSouth();
						_puzzleGraphics.repaint();
					}
				};
				time.schedule(task18, 1000);
				
			}
			for(int i = _puzzleModel._emptyTile.getCol();i>col+1;i--){
				TimerTask task19 = new TimerTask() {
					@Override
					public void run() {
						_puzzleModel.moveTileWest();
						_puzzleGraphics.repaint();
					}
				};
				time.schedule(task19, 1000);
				
			}
			for(int i = _puzzleModel._emptyTile.getCol()-1;i>0;i--){
				TimerTask task20 = new TimerTask() {
					@Override
					public void run() {
						_puzzleModel.moveTileNorth();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileWest();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileWest();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileSouth();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileEast();
						_puzzleGraphics.repaint();
					}
				};
				time.schedule(task20, 1000);
				
			}
//		} 
//		else if (_puzzleModel._emptyTile.getCol()<col&& row==4){
//		}
			
			// blank square to the right not in col 0 not equal col and row not 4
		} else if(_puzzleModel._emptyTile.getCol()>col&& row!=4){
			// blank under
			if(_puzzleModel._emptyTile.getRow()>row){
				for(int i = _puzzleModel._emptyTile.getRow(); i>row+1;i--){
					TimerTask task21 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileNorth();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task21, 1000);
					
				}
				for (int i = _puzzleModel._emptyTile.getCol();i>col;i--){
					TimerTask task22 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task22, 1000);
					
					
					}
				for (int i = _puzzleModel._emptyTile.getRow();i>row;i--){
					TimerTask task23 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileNorth();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task23, 1000);
					
				}
				for (int i = 3 - _puzzleModel._emptyTile.getRow();i>0;i--){
					TimerTask task24 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileEast();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileNorth();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task24, 1000);
					
					}
				TimerTask task25 = new TimerTask() {
					@Override
					public void run() {
						_puzzleModel.moveTileWest();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileSouth();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileEast();
						_puzzleGraphics.repaint();
					}
				};
				time.schedule(task25, 1000);
				
				
				for (int i = _puzzleModel._emptyTile.getCol() - 1; i>0;i--){
					TimerTask task26 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileNorth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileEast();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task26, 1000);
					
				}
			} else if(_puzzleModel._emptyTile.getRow()<=row){
				for(int i = _puzzleModel._emptyTile.getRow();i<row+1;i++){
					TimerTask task27 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task27, 1000);
					
				}
				for(int i = _puzzleModel._emptyTile.getCol();i<col;i--){
					TimerTask task28 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task28, 1000);
					
				}
				TimerTask task29 = new TimerTask() {
					@Override
					public void run() {
						_puzzleModel.moveTileNorth();
						_puzzleGraphics.repaint();
					}
				};
				time.schedule(task29, 1000);
				
				for(int i = 3 - _puzzleModel._emptyTile.getRow();i>0;i--){
					TimerTask task30 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileEast();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileNorth();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task30, 1000);
					
				}
				TimerTask task31 = new TimerTask() {
					@Override
					public void run() {
						_puzzleModel.moveTileWest();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileSouth();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileEast();
						_puzzleGraphics.repaint();
					}
				};
				time.schedule(task31, 1000);
				
				
				for (int i = _puzzleModel._emptyTile.getCol() - 1; i>0;i--){
					TimerTask task32 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileNorth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileEast();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task32, 1000);
					
				}
				
				
			}
				// blank under
				if(_puzzleModel._emptyTile.getRow()>row){
					for(int i = _puzzleModel._emptyTile.getRow(); i>row+1;i--){
						TimerTask task33 = new TimerTask() {
							@Override
							public void run() {
								_puzzleModel.moveTileNorth();
								_puzzleGraphics.repaint();
							}
						};
						time.schedule(task33, 1000);
						
					}
					for (int i = _puzzleModel._emptyTile.getCol();i>col;i--){
						TimerTask task34 = new TimerTask() {
							@Override
							public void run() {
								_puzzleModel.moveTileWest();
								_puzzleGraphics.repaint();
							}
						};
						time.schedule(task34, 1000);
						
						
						}
					for (int i = _puzzleModel._emptyTile.getRow();i>row;i--){
						TimerTask task35 = new TimerTask() {
							@Override
							public void run() {
								_puzzleModel.moveTileNorth();
								_puzzleGraphics.repaint();
							}
						};
						time.schedule(task35, 1000);
						
					}
					for (int i = 3 - _puzzleModel._emptyTile.getRow();i>0;i--){
						TimerTask task36 = new TimerTask() {
							@Override
							public void run() {
								_puzzleModel.moveTileWest();
								_puzzleGraphics.repaint();
								_puzzleModel.moveTileSouth();
								_puzzleGraphics.repaint();
								_puzzleModel.moveTileSouth();
								_puzzleGraphics.repaint();
								_puzzleModel.moveTileEast();
								_puzzleGraphics.repaint();
								_puzzleModel.moveTileNorth();
								_puzzleGraphics.repaint();
							}
						};
						time.schedule(task36, 1000);
						
						}
					TimerTask task37 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileEast();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task37, 1000);
					
					
					for (int i = _puzzleModel._emptyTile.getCol() - 1; i>0;i--){
						TimerTask task38 = new TimerTask() {
							@Override
							public void run() {
								_puzzleModel.moveTileNorth();
								_puzzleGraphics.repaint();
								_puzzleModel.moveTileWest();
								_puzzleGraphics.repaint();
								_puzzleModel.moveTileWest();
								_puzzleGraphics.repaint();
								_puzzleModel.moveTileSouth();
								_puzzleGraphics.repaint();
								_puzzleModel.moveTileEast();
								_puzzleGraphics.repaint();
							}
						};
						time.schedule(task38, 1000);
						
					}
				
			// blank over or same row	
			}  else if (_puzzleModel._emptyTile.getCol()<=col){
				for(int i = _puzzleModel._emptyTile.getRow(); i<row+1;i++){
					TimerTask task39 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task39, 1000);
					
				}
				for (int i = _puzzleModel._emptyTile.getCol();i>col;i--){
					TimerTask task40 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task40, 1000);
					
					
					}
				for (int i = _puzzleModel._emptyTile.getRow();i>row;i--){
					TimerTask task41 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileNorth();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task41, 1000);
					
				}
				for (int i = 3 - _puzzleModel._emptyTile.getRow();i>0;i--){
					TimerTask task42 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileEast();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileNorth();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task42, 1000);
					
					}
				TimerTask task43 = new TimerTask() {
					@Override
					public void run() {
						_puzzleModel.moveTileWest();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileSouth();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileEast();
						_puzzleGraphics.repaint();
					}
				};
				time.schedule(task43, 1000);
				
				
				for (int i = _puzzleModel._emptyTile.getCol() - 1; i>0;i--){
					TimerTask task44 = new TimerTask() {
						@Override
						public void run() {
							_puzzleModel.moveTileNorth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileWest();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileSouth();
							_puzzleGraphics.repaint();
							_puzzleModel.moveTileEast();
							_puzzleGraphics.repaint();
						}
					};
					time.schedule(task44, 1000);
					
				}
				
			}
			
			
				
				
//				for (int i = 0;i<3-col;i++){
//					_puzzleModel.moveTileNorth();
//					_puzzleGraphics.repaint();
//					_puzzleModel.moveTileWest();
//					_puzzleGraphics.repaint();
//					_puzzleModel.moveTileWest();
//					_puzzleGraphics.repaint();
//					_puzzleModel.moveTileSouth();
//					_puzzleGraphics.repaint();
//					_puzzleModel.moveTileEast();
//					_puzzleGraphics.repaint();
//					}
//		} else if (col == 0) {
//			for (int i = _puzzleModel._emptyTile.getCol();i<col;i++){
//				_puzzleModel.moveTileEast();
//				_puzzleGraphics.repaint();
//				}
//			for (int i = _puzzleModel._emptyTile.getRow();i>row;i--){
//				_puzzleModel.moveTileNorth();
//				_puzzleGraphics.repaint();
//			}
//			for (int i = _puzzleModel._emptyTile.getRow();i<3;i++){
//				_puzzleModel.moveTileEast();
//				_puzzleGraphics.repaint();
//				_puzzleModel.moveTileSouth();
//				_puzzleGraphics.repaint();
//				_puzzleModel.moveTileSouth();
//				_puzzleGraphics.repaint();
//				_puzzleModel.moveTileWest();
//				_puzzleGraphics.repaint();
//				_puzzleModel.moveTileNorth();
//				_puzzleGraphics.repaint();
//				}
//
//			
		} else {
		// blank square to the left
			
			//move tile down
			for(int r =_puzzleModel._emptyTile.getRow(); r<4;r++){
				TimerTask task45 = new TimerTask() {
					@Override
					public void run() {
						_puzzleModel.moveTileSouth();
						_puzzleGraphics.repaint();
					}
				};
				time.schedule(task45, 1000);
				
			}
			
		// move blank to the right
		for (int i = _puzzleModel._emptyTile.getCol();i<col;i++){
			TimerTask task46 = new TimerTask() {
				@Override
				public void run() {
					_puzzleModel.moveTileEast();
					_puzzleGraphics.repaint();
				}
			};
			time.schedule(task46, 1000);
		
		}
		// move blank up to where desiered square is 
		for (int i = _puzzleModel._emptyTile.getRow();i>row;i--){
			TimerTask task47 = new TimerTask() {
				@Override
				public void run() {
					_puzzleModel.moveTileNorth();
					_puzzleGraphics.repaint();
				}
			};
			time.schedule(task47, 1000);
			
		}
//		if(row == 4 && col == 4){
		if(row == 4){
			// on right
			for (int i = col-1;i>0;i--){
				TimerTask task48 = new TimerTask() {
					@Override
					public void run() {
						_puzzleModel.moveTileNorth();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileWest();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileWest();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileSouth();
						_puzzleGraphics.repaint();
						_puzzleModel.moveTileEast();
						_puzzleGraphics.repaint();
					}
				};
				time.schedule(task48, 1000);
			
			}
//		} else if(row == 4 && col ==5){
//			_puzzleModel.moveTileWest();
//			_puzzleGraphics.repaint();
//			_puzzleModel.moveTileSouth();
//			_puzzleGraphics.repaint();
//			_puzzleModel.moveTileEast();
//			_puzzleGraphics.repaint();
//			for (int i = _puzzleModel._emptyTile.getCol();i>1;i--){
//				_puzzleModel.moveTileNorth();
//				_puzzleGraphics.repaint();
//				_puzzleModel.moveTileWest();
//				_puzzleGraphics.repaint();
//				_puzzleModel.moveTileWest();
//				_puzzleGraphics.repaint();
//				_puzzleModel.moveTileSouth();
//				_puzzleGraphics.repaint();
//				_puzzleModel.moveTileEast();
//				_puzzleGraphics.repaint();
//				}
//			
//		}
			
			
		
//		for (int i = _puzzleModel._emptyTile.getRow();i<3;i++){
//		_puzzleModel.moveTileWest();
//		_puzzleGraphics.repaint();
//		_puzzleModel.moveTileSouth();
//		_puzzleGraphics.repaint();
//		_puzzleModel.moveTileSouth();
//		_puzzleGraphics.repaint();
//		_puzzleModel.moveTileEast();
//		_puzzleGraphics.repaint();
//		_puzzleModel.moveTileNorth();
//		_puzzleGraphics.repaint();
//		}
//		_puzzleModel.moveTileWest();
//		_puzzleGraphics.repaint();
//		_puzzleModel.moveTileSouth();
//		_puzzleGraphics.repaint();
//		_puzzleModel.moveTileEast();
//		_puzzleGraphics.repaint();
//		for (int i = 0;i<3-col;i++){
//			_puzzleModel.moveTileNorth();
//			_puzzleGraphics.repaint();
//			_puzzleModel.moveTileWest();
//			_puzzleGraphics.repaint();
//			_puzzleModel.moveTileWest();
//			_puzzleGraphics.repaint();
//			_puzzleModel.moveTileSouth();
//			_puzzleGraphics.repaint();
//			_puzzleModel.moveTileEast();
//			_puzzleGraphics.repaint();
//			}
		} else if (row!=4) {
			// to the top
			TimerTask task49 = new TimerTask() {
				@Override
				public void run() {
					_puzzleModel.moveTileWest();
					_puzzleGraphics.repaint();
					_puzzleModel.moveTileSouth();
					_puzzleGraphics.repaint();
					_puzzleModel.moveTileEast();
					_puzzleGraphics.repaint();
				}
			};
			time.schedule(task49, 1000);
						
						for (int i = col -1;i>0;i--){
							TimerTask task50 = new TimerTask() {
								@Override
								public void run() {
									_puzzleModel.moveTileNorth();
									_puzzleGraphics.repaint();
									_puzzleModel.moveTileWest();
									_puzzleGraphics.repaint();
									_puzzleModel.moveTileWest();
									_puzzleGraphics.repaint();
									_puzzleModel.moveTileSouth();
									_puzzleGraphics.repaint();
									_puzzleModel.moveTileEast();
									_puzzleGraphics.repaint();
								}
							};
							time.schedule(task50, 1000);
							
							}
		}
		}
		String entryName = _puzzleModel.getName(4, 0);
		if (entryName.equals("N/A") == false && entryName.equals("Unoccupied") == false) {
			spaces++;
			spacesOpen.setText(""+spaces);
			_puzzleModel.restoreDefaults(4, 0);
			_puzzleGraphics.repaint();
		}
	}
	
	public int emptyCount(){
		
		int count = 0;
		for(int i = 0;i<5;i++){
			for(int j = 0;j<5;j++){
				CarSpots temp = _puzzleModel.getCarSpots(i,j);
				if(!temp.getSpotTaken()){
					count = count + 1;
				}
			}
		}
		return count-2;
		
	}
	
}//end class SlidePuzzleGUI