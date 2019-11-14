
/**
 * The Fox class of java game based on the children's game "JumpIn". This class
 * contains the constructor which creates a instance of a fox. The class also
 * has methods which can be invoked on the fox instance. It extends the Slot
 * class and gets the variables and methods from that class
 * 
 * Milestone 1 author: Sandeep; Milestone 2 author: Tharsan, Omar
 * 
 * @author Sudarsana Sandeep
 * @author Tharsan Sivathasan
 * @author Omar Elberougy
 *
 */
public class Fox extends Slot {

	private int xPos2; // stores the coordinate of the tail x position
	private int yPos2;// stores the coordinate of the tail y position
	private boolean isVertical; // stores a boolean on whether the fox is vertical on the board

	/**
	 * Constructor of Fox class that initializes its variables. Fox is made of two
	 * pieces, the tail and the head which takes up two spaces on the board
	 * 
	 * @param xPos  Fox head x coordinate
	 * @param yPos  Fox head y coordinate
	 * @param xPos2 Fox tail x coordinate
	 * @param yPos2 Fox tail y coordinate
	 */
	public Fox(int xPos, int yPos, int xPos2, int yPos2) {
		super(xPos, yPos);
		this.xPos2 = xPos2;
		this.yPos2 = yPos2;

		if (yPos == yPos2) { // checking if fox is vertical
			isVertical = true;
		} else {
			isVertical = false;
		}
	}

	/**
	 * Method returns if the Fox is vertical (facing up or down)
	 * 
	 * @return boolean true if the fox is vertically positioned on the board,
	 *         otherwise false
	 */
	public boolean getVertical() {
		return this.isVertical;
	}

	/**
	 * Sets the x and y coordinates for the fox (head and tail coordinates)
	 * 
	 * @param x  Fox head x coordinate
	 * @param y  Fox head y coordinate
	 * @param x2 Fox tail x coordinate
	 * @param y2 Fox tail y coordinate
	 */
	public void setPos(int x, int y, int x2, int y2) {
		super.setPos(x, y); // setting both foxes head pieces
		this.xPos2 = x2; // setting first fox tail piece
		this.yPos2 = y2; // setting second fox tail piece
	}

	/**
	 * Method gets the location of where the tail's x coordinate is located on the
	 * board
	 * 
	 * @return Int the x coordinate of the fox's tail
	 */
	public int getTailX() {
		return xPos2;
	}

	/**
	 * Method gets the location of where the tail's y coordinate is located on the
	 * board
	 * 
	 * @return Int the y coordinate of the fox's tail
	 */
	public int getTailY() {
		return yPos2;
	}

	/**
	 * Calculates maximum number of spaces a fox can slide in a given direction and
	 * returns that value
	 * 
	 * @param board     2d array filled with slot objects
	 * @param row       the current x location of the fox
	 * @param col       the current y location of the fox
	 * @param direction 1 = up, 2, down, 3, right, 4 = left
	 * @param spaces    starting spaces jumped (should always be 0)
	 * @return spaces a fox can slide in a given direction
	 */
	private int canSlide(Slot[][] board, int row, int col, int direction, int spaces) {
		// if slot being checked is out of the board
		if (row < 0 || col < 0 || row >= board.length || col >= board.length) {
			return spaces - 1;
			// Recursively check how many slots are available (ignoring when spaces =0
			// because thats when its the current fox)
		} else if (spaces == 0 || board[row][col].getClass() == Slot.class) {

			// check next slot as per direction
			switch (direction) {
			case 1: // up
				return canSlide(board, row - 1, col, 1, spaces + 1);
			case 2: // down
				return canSlide(board, row + 1, col, 2, spaces + 1);
			case 3: // right
				return canSlide(board, row, col + 1, 3, spaces + 1);
			case 4: // left
				return canSlide(board, row, col - 1, 4, spaces + 1);
			}

		}
		// stop recursion when no more slots available
		return spaces - 1; // minus 1 because current location cannot be moved to
	}

	/**
	 * Tries to move a fox to the inputed x and y coordinate in the board 2d array
	 * inputed, and returns a boolean on whether or not the move was successful
	 * 
	 * @param board 2d array filled with slot objects
	 * @param xPos  the desired x location to move the fox
	 * @param yPos  the desired x location to move the fox
	 * @return boolean whether a move was performed
	 */
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

		int leadX = -1, leadY = -1; // coordinate that is closest to direction that is being moved
		int followX = -1, followY = -1;

		if (this.getY() == yPos) { // if moves up or down
			if(!isVertical) {
				return false;
			}
			if (this.getX() > xPos) { // if moves up
				if (getTailX() == getX() - 1) { // looking down
					leadX = getTailX();
					followX = getX();
				} else { // looking up
					leadX = getX();
					followX = getTailX();
				}
				spaces = -canSlide(board, leadX, this.getY(), 1, 0);
			} else if (this.getX() < xPos) { // if moves down
				if (getTailX() == getX() - 1) { // looking down
					leadX = getX();
					followX = getTailX();
				} else { // looking up
					leadX = getTailX();
					followX = getX();
				}
				spaces = canSlide(board, leadX, this.getY(), 2, 0);
			}
			// If the players desired destination is within the movable distance
			if (Math.abs(xPos - leadX) > Math.abs(spaces)) {
				return false;
			}

//			xPos2 = followX + spaces;
			xPos2 = followX + (xPos - leadX);
			yPos2 = this.getTailY();

		} else { // if moves right or left
			if(isVertical) {
				return false;
			}
			if (this.getY() > yPos) { // if moves left
				if (this.getTailY() == getY() - 1) { // looking right
					leadY = getTailY();
					followY = getY();
				} else { // looking left
					leadY = getY();
					followY = getTailY();
				}
				spaces = -canSlide(board, this.getX(), leadY, 4, 0);
			} else if (this.getY() < yPos) { // if moves right
				if (this.getTailY() == getY() - 1) { // looking right
					leadY = getY();
					followY = getTailY();
				} else { // looking left
					leadY = getTailY();
					followY = getY();
				}
				spaces = canSlide(board, this.getX(), leadY, 3, 0);
			}
			// If the players desired coordinate matches the amount of spaces away the
			// rabbit can move
			if (Math.abs(yPos - leadY) > Math.abs(spaces)) {
				return false;
			}

			xPos2 = this.getTailX();
//			yPos2 = followY + spaces;
			yPos2 = followY + (yPos - leadY);
		}

		if (this.getX() > xPos && getTailX() == getX() - 1) { // move up looking down
			int xTemp;
			xTemp = xPos;
			xPos = xPos2;
			xPos2 = xTemp;
		} else if (this.getY() > yPos && this.getTailY() == getY() - 1) { // move left looking right
			int yTemp;
			yTemp = yPos;
			yPos = yPos2;
			yPos2 = yTemp;
		}else if(this.getX() < xPos && getTailX() == getX() + 1) { // move down looking up
			
			int xTemp;
			xTemp = xPos;
			xPos = xPos2;
			xPos2 = xTemp;
			
		}else if (this.getY() < yPos && this.getTailY() == getY() + 1) { // move right looking left
			
			int yTemp;
			yTemp = yPos;
			yPos = yPos2;
			yPos2 = yTemp;
			
		}

		if (spaces == 0) { // If fox doesnt move
			return false;
		}

		// Change current fox position on board to slot
		board[this.getX()][this.getY()] = new Slot(this.getX(), this.getY());
		board[this.getTailX()][this.getTailY()] = new Slot(this.getTailX(), this.getTailY());

		// Change destination in board to fox
		board[xPos][yPos] = this;
		board[xPos2][yPos2] = this;

		this.setPos(xPos, yPos, xPos2, yPos2);

		return true;
	}
}
