
/**
 * The Fox class of a text based version of the "JumpIn" game. This
 * class contains the constructor which creates the game piece Fox.
 * The class also has methods which can be invoked on instances of Fox object.
 * 
 * Milestone 1:
 * @author Sudarsana Sandeep
 * Milestone 2:
 * @author Tharsan Sivathasan
 *
 */
public class Fox extends Slot {

	private int xPos2;
	private int yPos2;
	private boolean isVertical;
	
	/**
	 * Constructor of Fox class that initializes its variables
	 * Fox is made of two pieces, the tail and the head which takes up two spaces on the board
	 */
	public Fox(int xPos, int yPos, int xPos2, int yPos2) {
		super(xPos, yPos);
		this.xPos2 = xPos2;
		this.yPos2 = yPos2;

		if (yPos == yPos2) { 	//checking if fox is vertical
			isVertical = true;
		} else {
			isVertical = false;
		}
	}
	
	/**
	 * Method determines if the Fox is vertical
	 * @return boolean true if the fox is vertical positioned on the board, otherwise false
	 */
	public boolean getVertical() {
		return this.isVertical;
	}

	/**
	 * Method sets the position of the Fox on the board
	 */
	public void setPos(int x, int y, int x2, int y2) {
		super.setPos(x, y); //setting both foxes head pieces
		this.xPos2 = x2; //setting first fox tail piece
		this.yPos2 = y2; //setting second fox tail piece
	}
	
	/**
	 * Method gets the location of where the tail is located on the board of the first Fox
	 * @return Int the coordinate of the tail of the first Fox object
	 */
	public int getTailX() {
		return xPos2;
	}

	/**
	 * Method gets the location of where the tail is located on the board of the second Fox
	 * @return Int the coordinate of the tail of the second Fox object
	 */
	public int getTailY() {
		return yPos2;
	}
	
	private int canSlide(Slot[][] board, int row, int col, int direction, int spaces) {
		//if slot being checked is out of the board
		if (row < 0 || col < 0 || row >= board.length || col >= board.length) {
			return spaces-1;
		// Recursively check how many slots are available ignoring when spaces =0 because thats when its the current fox
		} else if (spaces == 0 || board[row][col] instanceof Slot) {

			// check next slot as per direction
			switch (direction) {
			case 1: // up
				return canSlide(board, row - 1, col, 1, spaces+1);
			case 2: // down
				return canSlide(board, row + 1, col, 2, spaces+1);
			case 3: // right
				return canSlide(board, row, col + 1, 3, spaces+1);
			case 4: // left
				return canSlide(board, row, col - 1, 4, spaces+1);
			}

		}
		// stop recursion when no more slots available
		return spaces-1;
	}
	
	public boolean move(Slot[][] board, int xPos, int yPos) {

		// check if the destination is within the row/column and coordinate is in the
		// board
		if (xPos < 0 || yPos < 0 || xPos >= board.length || yPos >= board.length) {
			return false;
		} else if (this.getX() != xPos && this.getY() != yPos) {
			return false;
		}

		int spaces = -1; // Variable to store how many slots slid over
		int xPos2 = -1;
		int yPos2 = -1;

		if (this.getY() == yPos) { // if moves up or down
			if (this.getX() > xPos) { // if moves up
				spaces = -canSlide(board, this.getX(), this.getY(), 1, 0);
			} else if (this.getX() < xPos) { // if moves down
				spaces = canSlide(board, this.getX(), this.getY(), 2, 0);
			}
			// If the players desired coordinate matches the amount of spaces away the
			// rabbit can move
			if (Math.abs(xPos - this.getX()) > spaces) {
				return false;
			}
			
			xPos2 = this.getTailX() + spaces;
			yPos2 = this.getTailY();

		} else { // if moves right or left
			if (this.getY() > yPos) { // if moves left
				spaces = -canSlide(board, this.getX(), this.getY(), 4, 0);
			} else if (this.getY() < yPos) { // if moves right
				spaces = canSlide(board, this.getX(), this.getY(), 3, 0);
			}
			// If the players desired coordinate matches the amount of spaces away the
			// rabbit can move
			if (Math.abs(yPos - this.getY()) > spaces) {
				return false;
			}
			
			xPos2 = this.getTailX();
			yPos2 = this.getTailY() + spaces;
		}

		if (spaces == 0) { // If fox doesnt move
			return false;
		}
		
		
		//Change current fox position on board to slot
		board[this.getX()][this.getY()] = new Slot(this.getX(),this.getY());
		board[this.getTailX()][this.getTailY()] = new Slot(this.getTailX(),this.getTailY());
		
		//Change destination in board to fox
		board[xPos][yPos] = this;
		board[xPos2][yPos2] = this;
		
		this.setPos(xPos, yPos, xPos2, yPos2);
		
		return true;
	}
}
