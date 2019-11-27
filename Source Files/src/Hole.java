
import java.util.ArrayList;


/**
 * The Hole class of java game based on the children's game "JumpIn". This class
 * contains the constructor which creates a instance of a Hole. The class also
 * has methods which can be invoked on the Hole instance. It extends the Slot
 * class and gets the variables and methods from that class
 * 
 * Milestone 1 author: Sandeep; Milestone 2 author: Tharsan
 * 
 * @author Sudarsana Sandeep
 * @author Tharsan Sivathasan
 *
 */
public class Hole extends Slot {

	private ArrayList<Slot> gamePieceList;

	/**
	 * Constructor for hole class that accepts coordinates and stores those
	 * coordinates
	 * 
	 * @param xPos x coordinate of object
	 * @param yPos x coordinate of object
	 */
	public Hole(int xPos, int yPos) {
		super(xPos, yPos);
		this.gamePieceList = new ArrayList<Slot>();
	}

	/**
	 * Determines if the Hole object has any game piece within itself (i.e Rabbit,
	 * mushroom)
	 * 
	 * @return boolean true if the Hole contains a game piece
	 */
	public boolean hasGamePiece() {
		if (gamePieceList.isEmpty()) { // checks if the ArrayList is empty
			return false;
		} else { // if has game piece
			return true;
		}
	}

	/**
	 * Method determines if the Hole object has a Rabbit within itself
	 * 
	 * @return boolean true if the Hole contains Rabbit
	 */
	public boolean hasRabbit() {
		if (!gamePieceList.isEmpty()) { // checks if the ArrayList isn't empty
			if (gamePieceList.get(0) instanceof Rabbit) {// checks if the piece inside Hole is a Rabbit
				return true;
			}
		}
		return false;
	}

	/**
	 * Method adds a game piece to the hole
	 * 
	 * @param piece Slot object to be held within hole
	 */
	public void addGamePiece(Slot piece) {
		if (this.gamePieceList.isEmpty()) { // if the ArrayList is empty
			this.gamePieceList.add(piece); // add a game piece to the ArrayList
		}
	}

	/**
	 * Method adds a game piece to the hole
	 * 
	 * @return Slot object that was inside the hole
	 */
	public Slot getGamePiece() {
		return gamePieceList.get(0);

	}
	

	/**
	 * Method removes a game piece from inside the hole
	 */
	public void removeGamePiece() {
		this.gamePieceList.remove(0); // removes a game piece from the ArrayList
	}
	
	

//	@Override
//	public Color getColor() {
//		return ((Rabbit)this.getGamePiece()).getColor();
//		
//	}
//
//	@Override
//	public ArrayList<Integer> possibleMoves(Board board) {
//		
//		return ((Rabbit)this.getGamePiece()).possibleMoves(board);
//	}
//
//	@Override
//	public boolean canUndo() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void redo(Slot[][] b) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean undo(Slot[][] b) {
//		// TODO Auto-generated method stub
//		return false;
//	}
}
