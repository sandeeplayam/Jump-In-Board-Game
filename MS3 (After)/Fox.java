import java.awt.Color;
import java.util.ArrayList;

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
public class Fox extends Slot implements MovingPiece {

	private int xPos2; // stores the coordinate of the tail x position
	private int yPos2;// stores the coordinate of the tail y position
	private boolean isVertical; // stores a boolean on whether the fox is vertical on the board
	private Color color;
	private ActionStorage moves;

	/**
	 * Constructor of Fox class that initializes its variables. Fox is made of two
	 * pieces, the tail and the head which takes up two spaces on the board
	 * 
	 * @param xPos  Fox head x coordinate
	 * @param yPos  Fox head y coordinate
	 * @param xPos2 Fox tail x coordinate
	 * @param yPos2 Fox tail y coordinate
	 */
	public Fox(int xPos, int yPos, int xPos2, int yPos2, Color c) {
		super(xPos, yPos);
		this.xPos2 = xPos2;
		this.yPos2 = yPos2;
		this.color = c;
		moves = new ActionStorage();

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

	public boolean forward(int direction) {
		boolean d = false;
		switch (direction) {
		case 1:// up

			if (this.getX() < this.getTailX()) {
				d = true;
			}
			break;
		case 2:// down
			if (this.getX() > this.getTailX()) {
				d = true;
			}
			break;
		case 3:// right

			if (this.getY() > this.getTailY()) {
				d = true;
			}
			break;
		case 4:// left
			if (this.getY() < this.getTailY()) {
				d = true;
			}
			break;

		}

		return d;
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
	public int canSlide(Slot[][] board, int row, int col, int direction, int spaces) {
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
	public boolean move(Slot[][] board, int xPos, int yPos, int undo) {

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

		int leadX = this.getX(), leadY = this.getY(); // coordinate that is closest to direction that is being moved
		int followX = this.getTailX(), followY = this.getTailY();

		if (this.getY() == yPos) { // if moves up or down
			if (!isVertical) {
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
			if (isVertical) {
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

			// System.out.println("spaces "+spaces);

			xPos2 = this.getTailX();
//			yPos2 = followY + spaces;
			yPos2 = followY + (yPos - leadY);
		}

		if (spaces == 0) { // If fox doesnt move
			return false;
		}

		if (this.getY() > yPos || this.getX() > xPos) {// if going left or up
			followY = leadY;// use lead as starting point for moving fox
			followX = leadX;
		}

		if (undo > 0) {
			moves.addMove(followX, followY, xPos, yPos, undo);
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
		} else if (this.getX() < xPos && getTailX() == getX() + 1) { // move down looking up

			int xTemp;
			xTemp = xPos;
			xPos = xPos2;
			xPos2 = xTemp;
		} else if (this.getY() < yPos && this.getTailY() == getY() + 1) { // move right looking left

			int yTemp;
			yTemp = yPos;
			yPos = yPos2;
			yPos2 = yTemp;
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

	@Override
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void redo(Slot[][] b) {
		int index = moves.getRedoy().size() - 1;

		if (this.move(b, moves.getundoX(index), moves.getundoY(index), 2)) {
			moves.removeUndo();
		}

	}

	public boolean undo(Slot[][] b) {
		int numMoves = moves.getNumMoves();
		boolean success = false;

		int extrax = moves.getX(numMoves) - moves.getX(numMoves - 1);
		int extray = moves.getY(moves.getNumMoves()) - moves.getY(moves.getNumMoves() - 1);

		if (extray == 0) {
			if (moves.getX(numMoves) > moves.getX(numMoves - 1)) {
				success = this.move(b, moves.getX(numMoves - 1), moves.getY(numMoves - 1), 0);

			} else if (moves.getX(numMoves) < moves.getX(numMoves - 1)) {
				success = this.move(b, moves.getX(numMoves - 1) + 1, moves.getY(numMoves - 1), 0);

			}

		} else if (extrax == 0) {
			if (moves.getY(numMoves) > moves.getY(numMoves - 1)) {
				success = this.move(b, moves.getX(numMoves - 1), moves.getY(numMoves - 1), 0);

			} else if (moves.getY(numMoves) < moves.getY(numMoves - 1)) {
				success = this.move(b, moves.getX(numMoves - 1), moves.getY(numMoves - 1) + 1, 0);

			}
		}

		// System.out.println("succesundo "+success);
		if (success) {

			moves.addUndoMove();
		}
		return success;

	}

	public boolean canUndo() {
		return (!moves.getMovex().isEmpty());
	}

	public ArrayList<Integer> possibleMoves(Board board) {

		int dir = 1;
		ArrayList<Integer> foxMoves = new ArrayList<Integer>();

		while (dir < 5) {

			int spaces = 0;

			if (this.forward(dir)) {

				spaces = (this.canSlide(board.getBoard(), this.getX(), this.getY(), dir, 0));// uses head as lead

			} else {// use tail
				spaces = (this.canSlide(board.getBoard(), this.getTailX(), this.getTailY(), dir, 0));// uses tail as the
																										// lead
			}

			if (((dir < 3) && this.getVertical()) || ((dir > 2) && (!this.getVertical()))) {// If
																							// going
																							// proper
																							// direction

				if (spaces > 0) {
					foxMoves.add(dir);
					foxMoves.add(spaces);
				}
			}

			dir++;
		}
		
		return foxMoves;
	}
}
