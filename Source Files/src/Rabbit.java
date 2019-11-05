import java.awt.Color;

/**
 * The Rabbit class of java game based on the children's game "JumpIn". This class
 * contains the constructor which creates a instance of a Rabbit. The class also
 * has methods which can be invoked on the rabbit instance. It extends the Slot
 * class and gets the variables and methods from that class
 * 
 * Milestone 1 author: Sandeep; Milestone 2 author: Tharsan
 * 
 * @author Sudarsana Sandeep
 * @author Tharsan Sivathasan
 *
 */
public class Rabbit extends Slot {

	private Color color; // Stores the color of the rabbit instance

	/**
	 * Constructor of Rabbit class that initializes its variables
	 * 
	 * @param xPos  x coordinate of rabbit object
	 * @param yPos  y coordinate of rabbit object
	 * @param color color of rabbit object
	 */
	public Rabbit(int xPos, int yPos, Color color) {
		super(xPos, yPos);
		this.color = color;
	}

	/**
	 * Returns the color of the instance variable
	 * 
	 * @return Color object from java.awt
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Calculates how many spaces a rabbit can hop in a given direction
	 * 
	 * @param board     2d array filled with slot objects
	 * @param row       the current x location of the rabbit
	 * @param col       the current y location of the rabbit
	 * @param direction 1 = up, 2, down, 3, right, 4 = left
	 * @param spaces    starting spaces jumped (should always be 0)
	 * @return spaces jumped
	 */
	private int canHop(Slot[][] board, int row, int col, int direction, int spaces) {
		// if slot being checked is out of the board, returns special int so can be
		// distinguished from "no obstacle"
		if (row < 0 || col < 0 || row >= board.length || col >= board.length) {
			return -2;
		}

		// if there's an obstacle (mushroom, rabbit, fox, or hole with a piece inside),
		// check if there are more obstacles ahead
		else if (board[row][col].getClass() == Mushroom.class || board[row][col].getClass() == Rabbit.class
				|| board[row][col].getClass() == Fox.class
				|| (board[row][col].getClass() == Hole.class && ((Hole) board[row][col]).hasGamePiece())) {
			// check next slot as per direction
			switch (direction) {
			case 1: // up
				return canHop(board, row - 1, col, 1, spaces + 1);
			case 2: // down
				return canHop(board, row + 1, col, 2, spaces + 1);
			case 3: // right
				return canHop(board, row, col + 1, 3, spaces + 1);
			case 4: // left
				return canHop(board, row, col - 1, 4, spaces + 1);
			}

		}
		// if there's an empty slot or a hole without a piece inside
		else if (board[row][col].getClass() == Slot.class
				|| (board[row][col].getClass() == Hole.class && !((Hole) board[row][col]).hasGamePiece())) {
			return spaces;
		}
		// stop recursion when no more obstacles available
		return 0;
	}

	/**
	 * Tries to move a rabbit to the inputed x and y coordinate in the board 2d
	 * array inputed and returns a boolean on whether or not the move was successful
	 * 
	 * @param board 2d array filled with slot objects
	 * @param xPos  the desired x location to move the rabbit
	 * @param yPos  the desired x location to move the rabbit
	 * @return boolean whether a move was performed
	 */
	public boolean move(Slot[][] board, int xPos, int yPos) {

		// is in the board
		if (xPos < 0 || yPos < 0 || xPos >= board.length || yPos >= board.length) {
			return false;
		} // check if the destination is within the row or column
		else if (this.getX() != xPos && this.getY() != yPos) {
			return false;
		}

		int spaces = 0; // Variable to store how many slots you jump over

		if (this.getY() == yPos) { // if moves up or down
			if (this.getX() > xPos) { // if moves up
				spaces = -canHop(board, this.getX(), this.getY(), 1, 0);
			} else if (this.getX() < xPos) { // if moves down
				spaces = canHop(board, this.getX(), this.getY(), 2, 0);
			}
			// If desired coordinate is not equal to the amount of spaces able to be moved
			if ((xPos - this.getX()) != spaces) {
				return false;
			}

		} else { // if moves right or left
			if (this.getY() > yPos) { // if moves left
				spaces = -canHop(board, this.getX(), this.getY(), 4, 0);
			} else if (this.getY() < yPos) { // if moves right
				spaces = canHop(board, this.getX(), this.getY(), 3, 0);
			}
			// If desired coordinate is not equal to the amount of spaces able to be moved
			if ((yPos - this.getY()) != spaces) {
				return false;
			}

		}

		// If spaces is 1 it means it only jumped 1 square (and not over an obstacle) it
		// is not a valid move and should return false
		if (spaces >= -1 && spaces <= 1) {
			return false;
		}

		// Add new rabbits position to board
		if (board[xPos][yPos].getClass() == Hole.class) {// If desired destination position is a hole
			// Add current rabbit to the hole
			((Hole) board[xPos][yPos]).addGamePiece(this);

		} else {// else just change that slot to that rabbit
			board[xPos][yPos] = this;

		}

		// clear rabbit's old position
		if (board[this.getX()][this.getY()].getClass() == Hole.class) { // if current rabbit position is in hole
			// remove the rabbit from the hole
			((Hole) board[this.getX()][this.getY()]).removeGamePiece();
		} else { // else change that position to an empty slot
			board[this.getX()][this.getY()] = new Slot(this.getX(), this.getY());
		}
		// Set the rabbit's position
		this.setPos(xPos, yPos);

		return true;
	}
}
