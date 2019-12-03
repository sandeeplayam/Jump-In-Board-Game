
/**
 * The Board class is what controls the model portion of the MVC for the game 
 * JumpIn'. This class creates and initializes the board with the objects on 
 * the board. The View class calls the methods in this class to update the GUI.
 * 
 * Milestone 1 author: Omar, Milestone 2 author: Tharsan
 * 
 * @author Omar Elberougy
 * @author Tharsan Sivathasan
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

	private Board() {
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
	}

	/**
	 * Constructor of the board, that initializes the array lists that will be
	 * filled with slot objects, and initializes the board initial layout with the
	 * challenge number inputed
	 * 
	 * @param challengeNum A number representing a starting board layout
	 */
	public Board(int challengeNum) {

		this();

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
			holes.add(new Hole(0, 0));
			holes.add(new Hole(0, 4));
			holes.add(new Hole(4, 0));
			holes.add(new Hole(4, 4));
			rabbits.add(new Rabbit(0, 1, Color.WHITE));
			mushrooms.add(new Mushroom(2, 2));
			mushrooms.add(new Mushroom(2, 0));
			mushrooms.add(new Mushroom(3, 2));
			foxes.add(new Fox(3, 1, 4, 1, Color.BLACK));
			break;

		case 3:
			Rabbit r = new Rabbit(0, 2, Color.WHITE);
			rabbits.add(r);
			rabbits.add(new Rabbit(2, 1, Color.orange));
			holes.add(new Hole(0, 0));
			holes.add(new Hole(0, 4));
			holes.add(new Hole(2, 2));
			holes.add(new Hole(4, 0));
			holes.add(new Hole(4, 4));
			mushrooms.add(new Mushroom(1, 2));
			mushrooms.add(new Mushroom(4, 3));
			foxes.add(new Fox(3, 3, 3, 4, Color.BLACK));
			break;

		case 4:
			holes.add(new Hole(0, 0));
			holes.add(new Hole(0, 4));
			holes.add(new Hole(2, 2));
			holes.add(new Hole(4, 0));
			holes.add(new Hole(4, 4));
			rabbits.add(new Rabbit(2, 4, Color.WHITE));
			rabbits.add(new Rabbit(4, 2, Color.ORANGE));
			mushrooms.add(new Mushroom(1, 0));
			mushrooms.add(new Mushroom(2, 1));
			mushrooms.add(new Mushroom(3, 2));
			foxes.add(new Fox(1, 3, 0, 3, Color.BLACK));
			break;

		case 5:
			rabbits.add(new Rabbit(1, 3, Color.WHITE));
			rabbits.add(new Rabbit(2, 4, Color.ORANGE));
			rabbits.add(new Rabbit(4, 3, Color.GRAY));
			foxes.add(new Fox(1, 1, 1, 0, Color.RED));
			foxes.add(new Fox(3, 1, 2, 1, Color.BLACK));
			holes.add(new Hole(0, 0));
			holes.add(new Hole(0, 4));
			holes.add(new Hole(2, 2));
			holes.add(new Hole(4, 0));
			holes.add(new Hole(4, 4));
			mushrooms.add(new Mushroom(0, 4));
			mushrooms.add(new Mushroom(4, 0));
			mushrooms.add(new Mushroom(3, 2));
			break;
		}
		this.addPiecesToBoard(); // Adds all the pieces in the arraylists to the board
	}
	
	public ArrayList<Slot> getAllPieces(){
		
		ArrayList<Slot> pieces = new ArrayList<Slot>(this.getFoxes());
		pieces.addAll(this.getHoles());
		pieces.addAll(this.getMushrooms());
		pieces.addAll(this.getRabbits());
		return pieces;
	}

	public Board(Board copy) {
		
		this(copy.getAllPieces());
		
		ActionStorage possMoves = copy.getMoves();
		for (int i = 0; i < possMoves.getNumMoves(); i++) {
			this.move(possMoves.getX(i), possMoves.getY(i), possMoves.getX(i + 1), possMoves.getY(i + 1), 1);
		}
		this.clearMoves();
	}

	public Board(ArrayList<Slot> pieces) {
		this();

		for (Slot i : pieces) {
			if (i.getClass() == Rabbit.class) {
				Rabbit temp = (Rabbit) i;
				rabbits.add(new Rabbit(temp.getX(), temp.getY(), temp.getColor()));
			} else if (i.getClass() == Fox.class) {
				Fox temp = (Fox) i;
				foxes.add(new Fox(temp.getX(), temp.getY(), temp.getTailX(), temp.getTailY(), temp.getColor()));
			} else if (i.getClass() == Mushroom.class) {
				Mushroom temp = (Mushroom) i;
				mushrooms.add(new Mushroom(temp.getX(), temp.getY()));
			} else if (i.getClass() == Hole.class) {
				Hole temp = (Hole) i;
				holes.add(new Hole(temp.getX(), temp.getY()));
			}
		}
		this.addPiecesToBoard(); // Adds all the pieces in the arraylists to the board
	}

	/**
	 * Adds all the pieces in the array lists to the board
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
			if (board[temp.getX()][temp.getY()].getClass() != Hole.class) {
				board[temp.getX()][temp.getY()] = temp;
			} else {
				((Hole) board[temp.getX()][temp.getY()]).addGamePiece(temp);
			}
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
			if (board[temp.getX()][temp.getY()].getClass() != Hole.class) {
				board[temp.getX()][temp.getY()] = temp;
			} else {
				((Hole) board[temp.getX()][temp.getY()]).addGamePiece(temp);
			}
		}
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
		
		System.out.println(occupiedHoles + " " +rabbits.size());
	
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
			success = ((Rabbit) board[x1][y1]).move(board, x2, y2, 1); // call move method for that rabbit
		} // if is a hole with a rabbit inside
		else if ((board[x1][y1].getClass() == Hole.class && ((Hole) board[x1][y1]).hasRabbit())) {
			// call move method for that rabbit inside the hole
			success = ((Rabbit) ((Hole) board[x1][y1]).getGamePiece()).move(board, x2, y2, 1);
		} else if (board[x1][y1].getClass() == Fox.class) { // if it is a fox
			success = ((Fox) board[x1][y1]).move(board, x2, y2, 1); // call move method for that fox
		}

		if (success && undo > 0) {
			moves.addMove(x1, y1, x2, y2, undo);
		}

		return success;

	}

	public void clearMoves() {
		moves.clearMoves();
		for (MovingPiece piece : this.getGamePieces()) {
			piece.clearMoves();
		}

	}

	public ActionStorage getMoves() {
		return moves;
	}

	/**
	 * Returns an Arraylist of all the rabbit and foxes
	 * 
	 * @return Arraylist of rabbit and fox objects
	 */
	public ArrayList<MovingPiece> getGamePieces() {
		ArrayList<MovingPiece> al = new ArrayList<MovingPiece>();
		for (Slot r : rabbits) {
			al.add((MovingPiece) r);

		}

		for (Slot f : foxes) {
			al.add((MovingPiece) f);
		}

//		this.gamePieces = al;
		return al;
	}

	/**
	 * undo method checks if the undo is on a fox or rabbit and then delegates the
	 * undo itself to either the rabbit or fox class
	 */
	public void undo() {
		int numMoves = moves.getNumMoves();

		if ((board[moves.getX(numMoves)][moves.getY(numMoves)]).getClass() == Fox.class) {

			((Fox) board[moves.getX(numMoves)][moves.getY(numMoves)]).undo(board);

		} else if ((board[moves.getX(numMoves)][moves.getY(numMoves)]).getClass() == Rabbit.class) {

			((Rabbit) board[moves.getX(numMoves)][moves.getY(numMoves)]).undo(board);

		} else if ((board[moves.getX(numMoves)][moves.getY(numMoves)]).getClass() == Hole.class
				&& ((Hole) (board[moves.getX(numMoves)][moves.getY(numMoves)])).hasRabbit()) {

			((Rabbit) (((Hole) board[moves.getX(numMoves)][moves.getY(numMoves)]).getGamePiece())).undo(board);
		}
		moves.addUndoMove();
	}

	/**
	 * redo method redo's a move that was undone
	 */
	public void redo() {
		int index = moves.getRedoy().size() - 1;
		this.move(moves.getundoX(index - 1), moves.getundoY(index - 1), moves.getundoX(index), moves.getundoY(index),
				2);
		moves.removeUndo();
	}

	/**
	 * canUndo method checks if a undo operation can be performed
	 * 
	 * @return true if moves were made, false if no moves were made
	 */
	public boolean canUndo() {
		return (!moves.getMovex().isEmpty());
	}

	/**
	 * canRedo method checks if a redo operation can be performed
	 * 
	 * @return false if no moves to redo and true if there are moves to redo
	 */
	public boolean canRedo() {
		return (!moves.getRedox().isEmpty());
	}

	/**
	 * reset method resets the board to the original state, basically keeps undo'ing
	 * till the board is back to its original state
	 */
	public void reset() {
		while (this.canUndo()) {
			this.undo();
		}
	}

	public ArrayList<Slot> getRabbits() {
		return rabbits;
	}

	public ArrayList<Slot> getFoxes() {
		return foxes;
	}

	public ArrayList<Slot> getMushrooms() {
		return mushrooms;
	}

	public ArrayList<Slot> getHoles() {
		return holes;
	}
}
