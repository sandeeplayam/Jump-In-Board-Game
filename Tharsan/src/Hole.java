import java.util.ArrayList;

/**
 * 
 * The Hole class of a text based version of the "JumpIn" game. This class
 * contains the constructor which creates the game piece Hole. The class also
 * has methods which can be invoked on instances of Hole object.
 * 
 * Milestone 1:
 * @author Sudarsana Sandeep
 * Milestone 2:
 * @author Tharsan Sivathasan
 *
 */
public class Hole extends Slot {

	private ArrayList<Slot> gamePieceList;

	/**
	 * Constructor of Hole class that initializes its variables
	 */
	public Hole(int xPos, int yPos) {
		super(xPos, yPos);
		this.gamePieceList = new ArrayList<Slot>();
	}

	/**
	 * Method determines if the Hole object has any game piece within itself (i.e
	 * Rabbit, mushroom)
	 * 
	 * @return boolean true if the Hole contains a game piece
	 */
	public boolean hasGamePiece() {
		if (gamePieceList.isEmpty()) { // checks if the ArrayList is empty
			return false;
		}
		return true;
	}

	/**
	 * Method determines if the Hole object has a Rabbit within itself
	 * 
	 * @return boolean true if the Hole contains Rabbit
	 */
	public boolean hasRabbit() {
		if (!gamePieceList.isEmpty()) { // checks if the ArrayList is empty
			if (gamePieceList.get(0) instanceof Rabbit) {// checks if the piece inside Hole is a Rabbit
				return true;
			}
		}
		return false;
	}

	/**
	 * Method adds a game piece to the hole
	 */
	public void addGamePiece(Slot piece) {
		if (this.gamePieceList.isEmpty()) { // checks if the ArrayList is empty
			this.gamePieceList.add(piece); // adds a game piece to the ArrayList if its empty
		}
	}

	/**
	 * Method adds a game piece to the hole
	 * 
	 * @return
	 */
	public Slot getGamePiece() {
		return gamePieceList.get(0);

	}

	/**
	 * Method removes a game piece from the hole
	 */
	public void removeGamePiece() {
		this.gamePieceList.remove(0); // removes a game piece from the ArrayList
	}
}
