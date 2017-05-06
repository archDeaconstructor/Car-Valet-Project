package parkingvisuals;

class logicclass {
	 private static final int ROWS = 5;
	 private static final int COLS = 5;
	 
	 private Tile[][] _contents; 
	 private Tile     _emptyTile; 
	 
	 public logicclass() {
	     _contents = new Tile[ROWS][COLS];
	      reset();               // Initialize and shuffle tiles.
	 }//end constructor
	 
	 // Return the string to display at given row, col.
	 String getFace(int row, int col) {
	     return _contents[row][col].getFace();
	 }//end getFace
	 // Initialize and shuffle the tiles.
	 public void reset() {
	     for (int r=0; r<ROWS; r++) {
	         for (int c=0; c<COLS; c++) {
	             _contents[r][c] = new Tile(r, c, "" + (r*COLS+c+1));
	         }
	     }
	     //--- Set tile face to null to mark empty space
	     _emptyTile = _contents[ROWS-5][COLS-4];
	     _emptyTile.setFace(null);
	        
	 }//end reset
	 
	 // Move a tile to empty position beside it, if possible.
	 // Return true if it was moved, false if not legal.
	 public boolean moveTile(int r, int c) {
	     //--- It's a legal move if the empty cell is next to it.
	     return checkEmpty(r, c, -1, 0) || checkEmpty(r, c, 1, 0) || checkEmpty(r, c, 0, -1) || checkEmpty(r, c, 0, 1);
	 }//end moveTile
	 
	 // Check to see if there is an empty position beside tile.
	 // Return true and exchange if possible, else return false.
	 private boolean checkEmpty(int r, int c, int rdelta, int cdelta) {
	     int rNeighbor = r + rdelta;
	     int cNeighbor = c + cdelta;
	     //--- Check to see if this neighbor is on board and is empty.
	     if (isLegalRowCol(rNeighbor, cNeighbor) && _contents[rNeighbor][cNeighbor] == _emptyTile) {
	         exchangeTiles(r, c, rNeighbor, cNeighbor);
	         return true;
	     }
	     return false;
	 }//end checkEmpty
	 
	 // Check for legal row, col
	 public boolean isLegalRowCol(int r, int c) {
	     return r>=0 && r<ROWS && c>=0 && c<COLS;
	 }//end isLegalRowCol
	  
	 // Exchange two tiles.
	 private void exchangeTiles(int r1, int c1, int r2, int c2) {
	     Tile temp = _contents[r1][c1];
	     _contents[r1][c1] = _contents[r2][c2];
	     _contents[r2][c2] = temp;
	 }//end exchangeTiles

	}//end class SlidePuzzleModel
	  
	//Represents the individual "tiles" that slide in puzzle.
	class Tile {
	 //============================================ instance variables
	 private int _row;     // row of final position
	 private int _col;     // col of final position
	 private String _face;  // string to display 
	 //end instance variables
	 
	 //==================================================== constructor
	 public Tile(int row, int col, String face) {
	     _row = row;
	     _col = col;
	     _face = face;
	 }//end constructor
	 
	 //======================================================== setFace
	 public void setFace(String newFace) {
	     _face = newFace;
	 }//end getFace
	 
	 //======================================================== getFace
	 public String getFace() {
	     return _face;
	 }//end getFace

	 public boolean isInFinalPosition(int r, int c) {
	     return r==_row && c==_col;
	 }//end isInFinalPosition
	}//end class Tile
