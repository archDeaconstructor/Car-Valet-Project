package maintenanceStaffGUI;

//SlidePuzzleModel.java - Slide pieces to correct position.
//Fred Swartz, 2003-May-10

/////////////////////////////////////////// class SlidePuzzleModel
class SlidePuzzleModel {
	private static final int ROWS = 9;
	private static final int COLS = 8;
	
	private Tile[][] _contents;  // All tiles.
	private Tile     _emptyTile; // The empty space.
	
	
	//================================================= constructor
	public SlidePuzzleModel() {
		_contents = new Tile[ROWS][COLS];
		reset();               // Initialize and shuffle tiles.
	}//end constructor
	
	
	//===================================================== getFace
	// Return the string to display at given row, col.
	String getValue(int row, int col) {
		return _contents[row][col].getValue();
	}//end getFace
	
	
	//======================================================= reset
	// Initialize and shuffle the tiles.
	
	public void reset() {
		for (int r=0; r<ROWS-2; r++) {
			for (int c=0; c<COLS; c++) {
				_contents[r][c] = new Tile(r, c, "");
			}
		}
		_contents[7][0] = new Tile(8, 0, null);
		for (int c=1; c<COLS; c++) {
			_contents[7][c] = new Tile(7, c, "");
		}
		_contents[8][0] = new Tile(8, 0, "Enter");
		for (int c=1; c<COLS-1; c++) {
			_contents[8][c] = new Tile(8, c, "------");
		}
		_contents[8][7] = new Tile(8, 7, "Exit");
	}//end reset
	
	//==================================================== moveTile
	// Move a tile to empty position beside it, if possible.
	// Return true if it was moved, false if not legal.
	public boolean moveTile(int r, int c) {
		//--- It's a legal move if the empty cell is next to it.
		if(_contents[r][c-1].getValue() == null) {
			exchangeTiles(r, c, r, c-1);
		} else if(_contents[r][c+1].getValue() == null) {
			exchangeTiles(r, c, r, c+1);
		} else if(_contents[r-1][c].getValue() == null) {
			exchangeTiles(r, c, r-1, c);
		} else if(_contents[r+1][c].getValue() == null) {
			exchangeTiles(r, c, r+1, c);
		}
		return true;
	}//end moveTile
	
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
		//end instance variables
		//==================================================== constructor
		public Tile(int row, int col, String value) {
			_row = row;
			_col = col;
			_value = value;
		}//end constructor
		
		//======================================================== setFace
		public void setValue(String newValue) {
			_value = newValue;
		}//end getFace
		
		//======================================================== getFace
		public String getValue() {
			return _value;
		}//end getFace
		
		//=============================================== isInFinalPosition
	}//end class Tile
}