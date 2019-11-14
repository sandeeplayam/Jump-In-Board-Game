
/**
 * The Board class is what controls the model portion of the MVC for the game 
 * JumpIn'. This class creates and initializes the board with the objects on 
 * the board. The View class calls the methods in this class to update the GUI.
 * 
 * Milestone 1 author: Omar, Milestone 2 author: Tharsan
 * 
 * @author Omar Elberougy
 * @author Tharsan Sivathasan
 *
 */
import java.awt.Color;
import java.util.*;

public class Board {

	// 2d array to hold locations of pieces of type Slot on the board
	// Array lists' to hold the objects of piece Slot that can fit on the board.
	private Slot[][] board;
	private ArrayList<Slot> rabbits;
	private ArrayList<Slot> foxes;
	private ArrayList<Slot> mushrooms;
	private ArrayList<Slot> holes;
	private ActionStorage moves;

	/**
	 * Constructor of the board, that initializes the array lists that will be
	 * filled with slot objects, and initializes the board initial layout with the
	 * challenge number inputed
	 * 
	 * @param challengeNum A number representing a starting board layout
	 */
	public Board(int challengeNum) {

		moves = new ActionStorage();
		board = new Slot[5][5];
		holes = new ArrayList<Slot>();
		rabbits = new ArrayList<Slot>();
		mushrooms = new ArrayList<Slot>();
		foxes = new ArrayList<Slot>();

		// Initialize the 2d array 'board' with slot objects
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				board[i][j] = new Slot(i, j);
			}
		}

		// Creates objects that will be added to the board and it is added to the
		// individual arraylists based on inputed challenge number
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
		this.addPiecesToBoard(); // Adds all the pieces in the arraylists to the board
	}

	/**
	 * Getter for the 2D array holding all the Slots
	 * 
	 * @return Slot[][] of all slots created in board
	 */
	public Slot[][] getBoard() {
		return board;
	}

	/**
	 * Adds all the pieces in the array lists to the board
	 */
	private void addPiecesToBoard() {

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
	 * Checks if player won the game and returns boolean by checking if all the
	 * rabbits are in a hole
	 * 
	 * @return boolean True if won, False otherwise
	 */
	public boolean checkWin() {

		int occupiedHoles = 0;

		// If hole has a rabbit, increment occupied holes with a rabbit
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
	public boolean move(int x1, int y1, int x2, int y2, int undo) {
		boolean success = false;
		if (board[x1][y1].getClass() == Rabbit.class) { // if is a rabbit
			success = ((Rabbit) board[x1][y1]).move(board, x2, y2); // call move method for that rabbit
		} // if is a hole with a rabbit inside
		else if ((board[x1][y1].getClass() == Hole.class && ((Hole) board[x1][y1]).hasRabbit())) {
			// call move method for that rabbit inside the hole
			success = ((Rabbit) ((Hole) board[x1][y1]).getGamePiece()).move(board, x2, y2);
		} else if (board[x1][y1].getClass() == Fox.class) { // if it is a fox
			success = ((Fox) board[x1][y1]).move(board, x2, y2); // call move method for that fox
		}

		if (success && undo > 0) {
			moves.addMove(x1, y1, x2, y2, undo);
		}

		return success;

	}

	public void undo() {
		int numMoves = moves.getNumMoves();

		int extrax = moves.getX(numMoves) - moves.getX(numMoves - 1);
		int extray = moves.getY(moves.getNumMoves()) - moves.getY(moves.getNumMoves() - 1);

		if ((board[moves.getX(numMoves)][moves.getY(numMoves)]).getClass() == Fox.class) {
			if (extray == 0) {
				if (moves.getX(numMoves) > moves.getX(numMoves - 1)) {
					this.move(moves.getX(numMoves) - 1, moves.getY(numMoves), moves.getX(numMoves - 1),
							moves.getY(numMoves - 1), 0);

				} else if (moves.getX(numMoves) < moves.getX(numMoves - 1)) {
					this.move(moves.getX(numMoves), moves.getY(numMoves), moves.getX(numMoves - 1) + 1,
							moves.getY(numMoves - 1), 0);

				}

			} else if (extrax == 0) {
				if (moves.getY(numMoves) > moves.getY(numMoves - 1)) {
					this.move(moves.getX(numMoves), moves.getY(numMoves) - 1, moves.getX(numMoves - 1),
							moves.getY(numMoves - 1), 0);

				} else if (moves.getY(numMoves) < moves.getY(numMoves - 1)) {
					this.move(moves.getX(numMoves), moves.getY(numMoves), moves.getX(numMoves - 1),
							moves.getY(numMoves - 1) + 1, 0);

				}
			}

		} else {

			this.move(moves.getX(numMoves), moves.getY(numMoves), moves.getX(numMoves - 1), moves.getY(numMoves - 1),
					0);

		}

		moves.addUndoMove();
	}

	public void redo() {
		int index = moves.getRedoy().size() - 1;
		this.move(moves.getundoX(index - 1), moves.getundoY(index - 1), moves.getundoX(index), moves.getundoY(index),
				2);
		moves.removeUndo();
	}

	public boolean canUndo() {
		return (moves.getNumMoves() != -1);
	}

	public boolean canRedo() {
		return (!moves.getRedox().isEmpty());
	}
}
