import java.awt.Color;

/**
 * 
 * The Rabbit class of a text based version of the "JumpIn" game. This class
 * contains the constructor which creates the game piece Rabbit. The class also
 * has methods which can be invoked on instance of Rabbit object.
 * 
 * Milestone 1:
 * @author Sudarsana Sandeep
 * Milestone 2:
 * @author Tharsan Sivathasan
 *
 */
public class Rabbit extends Slot {

	private Color color;

	/**
	 * Constructor of Rabbit class that initializes its variables
	 */
	public Rabbit(int xPos, int yPos, Color color) {
		super(xPos, yPos);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	private int canHop(Slot[][] board, int row, int col, int direction, int spaces) {
		//if slot being checked is out of the board, returns special int so can be distinguished from "no obstacle"
		if (row < 0 || col < 0 || row >= board.length || col >= board.length) {
			return -2;
		}

		// if there's an obstacle, check if there are more obstacles ahead
		if (board[row][col] instanceof Mushroom || board[row][col] instanceof Rabbit || board[row][col] instanceof Fox
				|| (board[row][col] instanceof Hole && ((Hole) board[row][col]).hasGamePiece())) {

			// check next slot as per direction
			switch (direction) {
			case 1: // up
				return canHop(board, row - 1, col, 1, spaces+1);
			case 2: // down
				return canHop(board, row + 1, col, 2, spaces+1);
			case 3: // right
				return canHop(board, row, col + 1, 3, spaces+1);
			case 4: // left
				return canHop(board, row, col - 1, 4, spaces+1);
			}

		} else if (board[row][col] instanceof Slot || (board[row][col] instanceof Hole && !((Hole) board[row][col]).hasGamePiece())) {
			return spaces;
		}
		// stop recursion when no more obstacles available
		return 0;
	}

	public boolean move(Slot[][] board, int xPos, int yPos) {

		// check if the destination is within the row/column and coordinate is in the
		// board
		if (xPos < 0 || yPos < 0 || xPos >= board.length || yPos >= board.length) {
			return false;
		} else if (this.getX() != xPos && this.getY() != yPos) {
			return false;
		}

		int spaces = -1; // Variable to store how many slots you jump over

		if (this.getY() == yPos) { // if moves up or down
			if (this.getX() > xPos) { // if moves up
				spaces = -canHop(board, this.getX(), this.getY(), 1, 0);
			} else if (this.getX() < xPos) { // if moves down
				spaces = canHop(board, this.getX(), this.getY(), 2, 0);
			}
			// If the players desired coordinate matches the amount of spaces away the
			// rabbit can move
			if ((xPos - this.getX()) != spaces) {
				return false;
			}

		} else { // if moves right or left
			if (this.getY() > yPos) { // if moves left
				spaces = -canHop(board, this.getX(), this.getY(), 4, 0);
			} else if (this.getY() < yPos) { // if moves right
				spaces = canHop(board, this.getX(), this.getY(), 3, 0);
			}
			// If the players desired coordinate matches the amount of spaces away the
			// rabbit can move
			if ((yPos - this.getY()) != spaces) {
				return false;
			}

		}

		// If spaces is 1 it means it only jumped 1 square (Not over an obstacle) which
		// is not a valid move
		if (spaces >= -1 && spaces <= 1) {
			return false;
		}

		// Declare rabbit's new position on the Board (current Xposition - number of
		// possible moves)
		// If that position is a hole, add that rabbit to the hole
		if (board[xPos][yPos] instanceof Hole) {

			((Hole) board[xPos][yPos]).addGamePiece(this);

			// else just change that slot to that rabbit
		} else {
			board[xPos][yPos] = this;

		}

		// clear rabbit's old position
		// If it was a hole, remove the rabbit from the hole
		if (board[this.getX()][this.getY()] instanceof Hole) {

			((Hole) board[this.getX()][this.getY()]).removeGamePiece();
			// else change that position to an empty slot
		} else {
			board[this.getX()][this.getY()] = new Slot(this.getX(), this.getY());
		}
		// Set the rabbit's position
		this.setPos(xPos, yPos);
		
		return true;
	}
}
