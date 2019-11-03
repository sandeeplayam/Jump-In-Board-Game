import java.awt.Color;
import java.util.ArrayList;

/**
 * 
 * The Hole class of a text based version of the "JumpIn" game. This
 * class contains the constructor which creates the game piece Hole.
 * The class also has methods which can be invoked on instances of Hole object.
 * 
 * @author Sudarsana Sandeep (100963087)
 *
 */
public class Hole implements PiecesInterface {

	private int xPos;
	private int yPos;
	private ArrayList<PiecesInterface> gamePieceList;
	

	/**
	 * Constructor of Hole class that initializes its variables
	 */
	public Hole(int x, int y) {
		this.xPos = x;
		this.yPos = y;
		this.gamePieceList = new ArrayList<PiecesInterface>();
	}

	@Override
	public void setPos(int x, int y) {
		this.xPos = x;
		this.yPos = y;
		
	}

	@Override
	public int getX() {
		return xPos;
	}

	@Override
	public int getY() {
		return yPos;
	}
	
	/**
	 * Method determines if the Hole object has any game piece within itself (i.e Rabbit, mushroom)
	 * @return boolean true if the Hole contains a game piece
	 */
	public boolean hasGamePiece() {
		if (gamePieceList.isEmpty()) {	//checks if the ArrayList is empty
			return false;
		}
		return true;
	}

	/**
	 * Method determines if the Hole object has a Rabbit within itself
	 * @return boolean true if the Hole contains Rabbit
	 */
	public boolean hasRabbit() {
		if (!gamePieceList.isEmpty()) {		//checks if the ArrayList is empty
			if (gamePieceList.get(0) instanceof Rabbit) {//checks if the piece inside Hole is a Rabbit
				return true;
			}
		}
		return false;
	}

	/**
	 * Method adds a game piece to the hole
	 */
	public void addGamePiece(PiecesInterface piece) {
		if (this.gamePieceList.isEmpty()) { //checks if the ArrayList is empty
			this.gamePieceList.add(piece);	//adds a game piece to the ArrayList if its empty
		}
	}

	/**
	 * Method removes a game piece from the hole
	 */
	public void removeGamePiece() {
		this.gamePieceList.remove(0);	//removes a game piece from the ArrayList
	}
}
