package maintenanceStaffGUI;

import java.util.Calendar;

//SlidePuzzleModel.java - Slide pieces to correct position.
//Fred Swartz, 2003-May-10

/////////////////////////////////////////// class SlidePuzzleModel
class SlidePuzzleModel {
	private static final int ROWS = 9;
	private static final int COLS = 8;
	Calendar calendar = Calendar.getInstance();
	
	private Tile[][] _contents;  // All tiles.
	private Tile     _emptyTile = new Tile(7, 0, " ", "N/A"); // The empty space.
	
	
	//================================================= constructor
	public SlidePuzzleModel() {
		_contents = new Tile[ROWS][COLS];
		reset();               // Initialize and shuffle tiles.
	}//end constructor
	
	
	//===================================================== getFace
	// Return the string to display at given row, col.
	String getValue(int row, int col) {
		return _contents[row][col].getValue();
	}//end getValue
	String getName(int row, int col) {
		return _contents[row][col].getName();
	}//end getName
	String getTime(int row, int col) {
		return _contents[row][col].getTime();
	}//end getTime
	String getTimeRemaining(int row, int col) {
		return _contents[row][col].getTimeRemaining();
	}//end getTimeRemaining
		
	//======================================================= reset
	// Initialize and shuffle the tiles.
	
	public void reset() {
		int platformCount = 1;
		for (int r=0; r<ROWS-2; r++) {
			for (int c=0; c<COLS; c++) {
				_contents[r][c] = new Tile(r, c, Integer.toString(platformCount), "Unoccupied");
				platformCount++;
			}
		}
		_contents[7][0] = _emptyTile;
		for (int c=1; c<COLS; c++) {
			_contents[7][c] = new Tile(7, c, Integer.toString(platformCount), "Unoccupied");
			platformCount++;
		}
		_contents[8][0] = new Tile(8, 0, "Enter", "N/A");
		for (int c=1; c<COLS-1; c++) {
			_contents[8][c] = new Tile(8, c, "Error! This is a wall.", "N/A");
		}
		_contents[8][7] = new Tile(8, 7, "Exit", "N/A");
	}//end reset
	
	//==================================================== moveTile
    // Move a tile to empty position beside it, if possible.
    // Return true if it was moved, false if not legal.
    public void moveTileNorth() {
        checkEmpty(_emptyTile.getRow(), _emptyTile.getCol(), -1, 0);
        if (isLegalRowCol(_emptyTile.getRow() -1, _emptyTile.getCol())) {
        	_emptyTile.setRow(-1);
        }
    }//end moveTileNorth.
    public void moveTileSouth() {
    	if (_emptyTile.getRow() + 1 != 8) {
    		checkEmpty(_emptyTile.getRow(), _emptyTile.getCol(), +1, 0);
            if (isLegalRowCol(_emptyTile.getRow() +1, _emptyTile.getCol())) {
            	_emptyTile.setRow(+1);
            }
    	}
    }//end moveTileSouth.
    public void moveTileWest() {
        checkEmpty(_emptyTile.getRow(), _emptyTile.getCol(), 0, -1);
        if (isLegalRowCol(_emptyTile.getRow(), _emptyTile.getCol() -1)) {
        	_emptyTile.setCol(-1);
        }
    }//end moveTileWest.
    public void moveTileEast() {
        checkEmpty(_emptyTile.getRow(), _emptyTile.getCol(), 0, +1);
        if (isLegalRowCol(_emptyTile.getRow(), _emptyTile.getCol() +1)) {
        	_emptyTile.setCol(+1);
        }
    }//end moveTileEast.
    //causes the algorithm to pause
	public void checkEmpty(int r, int c, int rdelta, int cdelta) {
        int rNeighbor = r + rdelta;
        int cNeighbor = c + cdelta;
        //--- Check to see if this neighbor is on board and is empty.
        if (isLegalRowCol(rNeighbor, cNeighbor)) {
        	_contents[rNeighbor][cNeighbor].setRow(rdelta);
        	_contents[rNeighbor][cNeighbor].setRow(cdelta);
            exchangeTiles(r, c, rNeighbor, cNeighbor);
        }
    }//end checkEmpty
	//=============================================== isLegalRowCol
    // Check for legal row, col
    public boolean isLegalRowCol(int r, int c) {
        return r>=0 && r<ROWS && c>=0 && c<COLS;
    }//end isLegalRowCol
	
	//=============================================== exchangeTiles
	// Exchange two tiles.
	private void exchangeTiles(int r1, int c1, int r2, int c2) {
		Tile temp = _contents[r1][c1];
		_contents[r1][c1] = _contents[r2][c2];
		_contents[r2][c2] = temp;
	}//end exchangeTiles
	
	//////////////////////////////////////////////////////////class Tile
	//Represents the individual "tiles" that slide in puzzle.
	class Tile {
		//============================================ instance variables
		private int _row;     // row of final position
		private int _col;     // col of final position
		private String _value;  // string to display 
		private String _name; // name from other source
		private int _hours; // time from other source
		private int _minutes; // time from other source
		private int _seconds; // time from other source
		//end instance variables
		//==================================================== constructor
		public Tile(int row, int col, String value, String name) {
			_row = row;
			_col = col;
			_value = value;
			_name = name;
			_hours = calendar.get(Calendar.HOUR_OF_DAY);
			_minutes = calendar.get(Calendar.MINUTE);
			_seconds = calendar.get(Calendar.SECOND);
		}//end constructor
		//======================================================== setCol
		public void setCol(int delta) {
			_col += delta;
		}//end setCol
		//======================================================== getCol
		public int getCol() {
			return _col;
		}//end getCol
		//======================================================== setRow
		public void setRow(int delta) {
			_row += delta;
		}//end setRow
		//======================================================== getRow
		public int getRow() {
			return _row;
		}//end getRow
		//======================================================== setValue
		public void setValue(String newValue) {
			_value = newValue;
		}//end setValue
		
		//======================================================== getValue
		public String getValue() {
			return _value;
		}//end getValue
		//======================================================== getName
		public String getName() {
			return _name;
		}//end getName
		//======================================================== getHours
		public String getTime() {
			return _hours + ":" + _minutes + ":" + _seconds;
		}//end getTime
		public String getTimeRemaining() {
			return (_hours - calendar.get(Calendar.HOUR_OF_DAY)) + ":" +
		(_minutes - calendar.get(Calendar.MINUTE)) + ":" + (_seconds - calendar.get(Calendar.SECOND));
		}
		//=============================================== isInFinalPosition
	}//end class Tile
}