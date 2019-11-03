
/**
 * The Board class of a text based version of JumpIn'. This
 *  class contains the "board" of the game, which is the 5x5 Grid of slots
 *  for objects(hole, mushroom, rabbit, fox) to be placed in.
 * 
 * Milestone 1:
 * @author Omar Elberougy
 * Milestone 2:
 * @author Tharsan Sivathasan
 *
 */
import java.awt.Color;
import java.util.*;

public class Board {

	private Slot[][] board;
	private ArrayList<Slot> rabbits;
	private ArrayList<Slot> foxes;
	private ArrayList<Slot> mushrooms;
	private ArrayList<Slot> holes;

	/**
	 * Constructor of the board, that initializes the array lists that will be
	 * filled with slot objects, and initializes the board with the challenge number
	 * inputed
	 * 
	 * @param challengeNum A number representing a starting board layout
	 */
	public Board(int challengeNum) {
		board = new Slot[5][5];
		holes = new ArrayList<Slot>();
		rabbits = new ArrayList<Slot>();
		mushrooms = new ArrayList<Slot>();
		foxes = new ArrayList<Slot>();

		// Initialize the entire 2d array with slot objects
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				board[i][j] = new Slot(i, j);
			}
		}

		// Set up the Board initial layout based on inputed challenge number
		switch (challengeNum) {
		case 1:
			rabbits.add(new Rabbit(0, 1, Color.WHITE));
			rabbits.add(new Rabbit(4, 2, Color.ORANGE));
			holes.add(new Hole(0, 0));
			holes.add(new Hole(0, 4));
			holes.add(new Hole(2, 2));
			holes.add(new Hole(4, 0));
			holes.add(new Hole(4, 4));
			mushrooms.add(new Mushroom(0, 3));
			mushrooms.add(new Mushroom(1, 2));
			mushrooms.add(new Mushroom(3, 2));
			break;

		case 2:
			rabbits.add(new Rabbit(1, 3, Color.WHITE));
			rabbits.add(new Rabbit(2, 4, Color.ORANGE));
			rabbits.add(new Rabbit(4, 3, Color.GRAY));
			foxes.add(new Fox(1, 1, 1, 0));
			foxes.add(new Fox(3, 1, 2, 1));
			holes.add(new Hole(0, 0));
			holes.add(new Hole(0, 4));
			holes.add(new Hole(2, 2));
			holes.add(new Hole(4, 0));
			holes.add(new Hole(4, 4));
			mushrooms.add(new Mushroom(0, 4));
			((Hole) holes.get(1)).addGamePiece(mushrooms.get(0));
			mushrooms.add(new Mushroom(4, 0));
			((Hole) holes.get(3)).addGamePiece(mushrooms.get(1));
			mushrooms.add(new Mushroom(3, 2));
			break;
		}
		this.addPiecesToBoard();
	}

	/**
	 * Getter for the 2D array of Slots
	 * 
	 * @return Slot[][] of all slots created in board
	 */
	public Slot[][] getBoard() {
		return board;
	}

	/**
	 * Adds a game piece to the board
	 * 
	 * @param s A slot which can be any type of game piece (Hole, Mushroom, Fox, or
	 *          rabbit)
	 */
	public void addPiecesToBoard() {

		Slot temp;
		Iterator<Slot> iter = holes.iterator();
		while (iter.hasNext()) {
			temp = iter.next();
			board[temp.getX()][temp.getY()] = temp;
		}

		iter = rabbits.iterator();
		while (iter.hasNext()) {
			temp = iter.next();
			board[temp.getX()][temp.getY()] = temp;
		}

		iter = foxes.iterator();
		while (iter.hasNext()) {
			temp = iter.next();
			board[temp.getX()][temp.getY()] = temp;
			board[((Fox) temp).getTailX()][((Fox) temp).getTailY()] = temp;
		}

		iter = mushrooms.iterator();
		while (iter.hasNext()) {
			temp = iter.next();
			board[temp.getX()][temp.getY()] = temp;
		}

	}

	/**
	 * Checks if player won the game
	 * 
	 * @return boolean True if All rabbits are in a Hole, False otherwise
	 */
	public boolean checkWin() {

		int occupiedHoles = 0;

		// If hole has a rabbit, increment number of holes with a rabbit
		for (Slot h : holes) {
			if (((Hole) h).hasRabbit()) {
				occupiedHoles++;
			}
		}
		// If number of occupied holes is the same as number of rabbits on the board
		if (occupiedHoles == rabbits.size()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Move method gets beginning and end coordinates and calls the rabbit or fox
	 * move method to delegate moving to that piece
	 * 
	 * @param x1 Beginning x value of coordinate of the object that will be moved
	 * @param y1 Beginning y value of coordinate of the object that will be moved
	 * @param x2 Destination x value of coordinate of the object that will be moved
	 * @param y2 Destination y value of coordinate of the object that will be moved
	 * @return boolean whether the piece was moved correctly
	 */
	public boolean move(int x1, int y1, int x2, int y2) {/////////////////////////////////// FIX USING INSTANCEOF ISSUE
		if (board[x1][y1] instanceof Rabbit) { // is a rabbit
			return ((Rabbit) board[x1][y1]).move(board, x2, y2);
		} else if ((board[x1][y1] instanceof Hole && ((Hole) board[x1][y1]).hasRabbit())) { // is a hole with a rabbit
																							// inside
			return ((Rabbit) ((Hole) board[x1][y1]).getGamePiece()).move(board, x2, y2);
		} else { // else it is a fox
			return ((Fox) board[x1][y1]).move(board, x2, y2);
		}
	}

	////////////////////////////////////////////////// JUST TO TEST REMOVE BEFORE SUBMITTING//////////
	/**
	 * Gets the string representation/view of the board
	 * 
	 * @return A String representing the current state/view of the board
	 */
	public String toStringBoard() {
		String b = "";
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {

				if (board[i][j] instanceof Rabbit) {
					Color m = ((Rabbit) board[i][j]).getColor();
					if (m.equals(Color.WHITE)) {
						b += "RW";
					} else if (m.equals(Color.ORANGE)) {
						b += "RO";
					} else if (m.equals(Color.GRAY)) {
						b += "RG";
					}
				} else if (board[i][j] instanceof Mushroom) {
					b += "MM";
				} else if (board[i][j] instanceof Hole) {
					if (((Hole) board[i][j]).hasGamePiece()) {
						if (((Hole) board[i][j]).hasRabbit()) {
							Color m = ((Rabbit) ((Hole) board[i][j]).getGamePiece()).getColor();
							if (m.equals(Color.WHITE)) {
								b += "RW";
							} else if (m.equals(Color.ORANGE)) {
								b += "RO";
							} else if (m.equals(Color.GRAY)) {
								b += "RG";
							}
						} else {
							b += "MM";
						}
					} else {
						b += "HH";
					}
				} else if (board[i][j] instanceof Fox) {
					if (((Fox)board[i][j]).getX() == i && ((Fox)board[i][j]).getY() == j) {
						b += "HF";
					} else if (((Fox)board[i][j]).getTailX() == i && ((Fox)board[i][j]).getTailY() == j) {
						b += "TF";
					}
				} else if (board[i][j] instanceof Slot) {
					b += "SS";
				}
//				b += board[i][j].toString();
				b += " ";
			}
			b += "\n";
		}
		return b;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////
}
