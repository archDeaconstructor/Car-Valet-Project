package maintenanceStaffGUI;


//SlidePuzzleModel.java - Slide pieces to correct position.
//Fred Swartz, 2003-May-10

/////////////////////////////////////////// class SlidePuzzleModel
class SlidePuzzleModel {
	private static final int ROWS = 6;
	private static final int COLS = 5;
	
	private CarSpots[][] _contents;  // All tiles.
	private CarSpots     _emptyTile = new CarSpots(4, 0, " ", "N/A"); // The empty space.
	
	
	//================================================= constructor
	public SlidePuzzleModel() {
		_contents = new CarSpots[ROWS][COLS];
		reset();               // Initialize and shuffle tiles.
	}//end constructor
	
	
	//===================================================== getFace
	// Return the string to display at given row, col.
	String getStorageNumber(int row, int col) {
		return _contents[row][col].getStorageNumber();
	}//end getValue
	void setName(int row, int col, String name) {
		_contents[row][col].setName(name);
	}//end getValue
	void restoreDefaults(int row, int col) {
		_contents[row][col].setName("Unoccupied");
		_contents[row][col].setTime(0, "mins");
		_contents[row][col].toggleSpotTaken();
		_contents[row][col].setTimeSince("N/A");
	}//end getValue
	String getName(int row, int col) {
		return _contents[row][col].getName();
	}//end getName
	CarSpots getCarSpots(int row, int col) {
		return _contents[row][col];
	}//end getName
	boolean getSpotTaken(int row, int col) {
		return _contents[row][col].getSpotTaken();
	}//end getSpotTaken
	void setSpotTaken(int row, int col) {
		_contents[row][col].toggleSpotTaken();
	}//end getTimeRemaining
	String getTime(int row, int col) {
		return _contents[row][col].getTime();
	}//end getTime
	void setTime(int row, int col, int timeNumber, String timeTagged) {
		_contents[row][col].setTime(timeNumber, timeTagged);
	}//end getTime
	String getTimeSince(int row, int col) {
		return _contents[row][col].getTimeSince();
	}//end getTimeRemaining
	void setTimeSince(int row, int col, String dateInit) {
		_contents[row][col].setTimeSince(dateInit);
	}//end getTimeRemaining
		
	//======================================================= reset
	// Initialize and shuffle the tiles.
	
	public void reset() {
		int platformCount = 1;
		for (int r=0; r<ROWS-2; r++) {
			for (int c=0; c<COLS; c++) {
				_contents[r][c] = new CarSpots(r, c, Integer.toString(platformCount), "Unoccupied");
				platformCount++;
			}
		}
		_contents[4][0] = _emptyTile;
		for (int c=1; c<COLS; c++) {
			_contents[4][c] = new CarSpots(4, c, Integer.toString(platformCount), "Unoccupied");
			platformCount++;
		}
		_contents[5][0] = new CarSpots(5, 0, "Exit", "N/A");
		for (int c=1; c<COLS-1; c++) {
			_contents[5][c] = new CarSpots(5, c, "Error! This is a wall.", "N/A");
		}
		_contents[5][4] = new CarSpots(5, 4, "Enter", "N/A");
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
    	if (_emptyTile.getRow() + 1 != 5) {
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
		CarSpots temp = _contents[r1][c1];
		_contents[r1][c1] = _contents[r2][c2];
		_contents[r2][c2] = temp;
	}//end exchangeTiles
	
	//////////////////////////////////////////////////////////class Tile
	//Represents the individual "tiles" that slide in puzzle.
	class CarSpots {
		//============================================ instance variables
		private int _row;     // row of final position
		private int _col;     // col of final position
		private String _storageNumber;  // string to display for identification
		private String _name; // name from other source
		private boolean _spotTaken; // is there a car?
		private int _hours; // time from other source
		private int _minutes; // time from other source
		private String _dateInit; // time the vehicle was given to us
		//end instance variables
		//==================================================== constructor
		public CarSpots(int row, int col, String storageNumber, String name) {
			_row = row;
			_col = col;
			_storageNumber = storageNumber;
			_spotTaken = false;
			_name = name;
			_hours = 0;
			_minutes = 0;
			_dateInit = "N/A";
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
		//======================================================== getValue
		public String getStorageNumber() {
			return _storageNumber;
		}//end getValue
		//======================================================== getName
		public String getName() {
			return _name;
		}//end getName
		//======================================================== setName
		public void setName(String newName) {
			_name = newName;
		}//end setValue
		//======================================================== setTime
		public void setTime(int timeNumber, String timeTagged) {
			if (timeTagged.equals("hrs") == true) {
				_hours = timeNumber;
			} else if (timeTagged.equals("mins") == true) {
				_minutes = timeNumber;
			}
		}//end setTime
		//======================================================== getSpotTaken
		public boolean getSpotTaken() {
			return _spotTaken;
		}//end getSpotTaken
		//======================================================== toggleSpotTaken
		public void toggleSpotTaken() {
			if (_spotTaken == false) {
				_spotTaken = true;
			} else if (_spotTaken == true) {
				_spotTaken = false;
			}
		}//end toggleSpotTaken
		//======================================================== getTime
		public String getTime() {
			if (_hours != 0) {
				return (_hours + " hours");
			} else {
				return (_minutes + " minutes");
			}
		}//end getTime
		//=============================================== getTimeSince
		public String getTimeSince() {
			return (_dateInit);
		}
		//=============================================== setTimeSince
		public void setTimeSince(String dateInit) {
			_dateInit = dateInit;
		}
	}//end class CarSpots
}