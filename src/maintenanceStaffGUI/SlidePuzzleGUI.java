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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import maintenanceStaffGUI.SlidePuzzleModel.Tile;

/////////////////////////////////////////////////// class SlidePuzzleGUI
//This class contains all the parts of the GUI interface
class SlidePuzzleGUI extends JPanel {
	//=============================================== instance variables
	private GraphicsPanel    _puzzleGraphics;
	private SlidePuzzleModel _puzzleModel = new SlidePuzzleModel();
	int emptyXLocation = 0;
	int emptyYLocation = 7;
	//end instance variables
	
	
	//====================================================== constructor
	public SlidePuzzleGUI() {
		//--- Create buttons and text fields
		JButton getInfo= new JButton("Get Platform Info");
		getInfo.addActionListener(new gettingInfo());
		JButton moveNorth= new JButton("Move North");
		moveNorth.addActionListener(new movingNorth());
		JButton moveSouth= new JButton("Move South");
		moveSouth.addActionListener(new movingSouth());
		JButton moveWest= new JButton("Move West");
		moveWest.addActionListener(new movingWest());
		JButton moveEast= new JButton("Move East");
		moveEast.addActionListener(new movingEast());
		JButton emergencyShutdown= new JButton("EMERGENCY SHUTDOWN");
		emergencyShutdown.addActionListener(new shuttingDown());
		JTextArea infoDump = new JTextArea("EMPTY", 44, 60);
		infoDump.setEditable(false);
		
		//--- Create control panel
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
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
		private static final int ROWS = 9;
		private static final int COLS = 8;
		
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
					String text = _puzzleModel.getValue(r, c);
					if (text != null) {
						g.setColor(Color.gray);
						g.fillRect(x+2, y+2, CELL_SIZE-4, CELL_SIZE-4);
						g.setColor(Color.black);
						g.setFont(_biggerFont);
						g.drawString(text, x+20, y+(3*CELL_SIZE)/4);
					}
				}
			}
			// paints ENTRY square
			g.setColor(Color.darkGray);
			g.fillRect(0, (8*CELL_SIZE)+2, CELL_SIZE-4, CELL_SIZE-4);
			g.setColor(Color.black);
			g.setFont(_biggerFont);
			g.drawString(_puzzleModel.getValue(8, 0), 20, (8*CELL_SIZE)+(3*CELL_SIZE)/4);
			// paints walls in bottom row
			for (int c=1; c<COLS-1; c++) {
				int x = c * CELL_SIZE;
				String text = _puzzleModel.getValue(8, c);
				if (text != null) {
					g.setColor(Color.black);
					g.fillRect(x+2, (8*CELL_SIZE)+2, CELL_SIZE-4, CELL_SIZE-4);
				}
			}
			// paints EXIT square
			g.setColor(Color.darkGray);
			g.fillRect(7*CELL_SIZE, (8*CELL_SIZE)+2, CELL_SIZE-4, CELL_SIZE-4);
			g.setColor(Color.black);
			g.setFont(_biggerFont);
			g.drawString(_puzzleModel.getValue(8, 7), (7*CELL_SIZE)+20, (8*CELL_SIZE)+(3*CELL_SIZE)/4);
		}//end paintComponent
		
		
		//======================================== listener mousePressed
		public void mousePressed(MouseEvent e) {
			/*/--- map x,y coordinates into a row and col.
			int col = e.getX()/CELL_SIZE;
			int row = e.getY()/CELL_SIZE;
			
			if (!_puzzleModel.moveTileNorth()) {
				// moveTile moves tile if legal, else returns false.
				Toolkit.getDefaultToolkit().beep();
			}
			
			this.repaint();  // Show any updates to model.
		*/}//end mousePressed
		
		
		//========================================== ignore these events
		public void mouseClicked (MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered (MouseEvent e) {}
		public void mouseExited  (MouseEvent e) {}
	}//end class GraphicsPanel
	
	////////////////////////////////////////// inner class gettingInfo
	public class gettingInfo implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			_puzzleModel.moveTileNorth(emptyXLocation, emptyYLocation);
			_puzzleGraphics.repaint();
		}
	}//end inner class gettingInfo
	public class movingNorth implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			_puzzleModel.moveTileNorth(emptyXLocation, emptyYLocation);
			_puzzleGraphics.repaint();
		}
	}//end inner class movingNorth
	public class movingSouth implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			_puzzleModel.moveTileNorth(emptyXLocation, emptyYLocation);
			_puzzleGraphics.repaint();
		}
	}//end inner class movingSouth
	public class movingWest implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			_puzzleModel.moveTileNorth(emptyXLocation, emptyYLocation);
			_puzzleGraphics.repaint();
		}
	}//end inner class movingWest
	public class movingEast implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			_puzzleModel.moveTileNorth(emptyXLocation, emptyYLocation);
			_puzzleGraphics.repaint();
		}
	}//end inner class movingEast
	public class shuttingDown implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			_puzzleModel.moveTileNorth(emptyXLocation, emptyYLocation);
			_puzzleGraphics.repaint();
		}
	}//end inner class shuttingDown
	
}//end class SlidePuzzleGUI