/**
 * The Slot class of java game based on the children's game "JumpIn". This class
 * contains the constructor which creates a instance of a Slot. The class also
 * has methods which can be invoked on the Slot instance
 * 
 * This class is used to create an instance of type Slot. A slot is considered
 * to be one piece on the board.
 * 
 * Milestone 1 author: Danish; Milestone 2 author: Tharsan
 * 
 * @author Danish Butt
 * @author Tharsan Sivathasan
 */

public class Slot {

	private int xPos;
	private int yPos;

	/**
	 * Constructor for slot class that accepts coordinates and stores those
	 * coordinates
	 * 
	 * @param xPos x coordinate of object
	 * @param yPos x coordinate of object
	 */
	public Slot(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	/**
	 * Set the position of the rabbit
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public void setPos(int x, int y) {
		this.xPos = x;
		this.yPos = y;
	}

	/**
	 * Returns x coordinate
	 * 
	 * @return x coordinate
	 */
	public int getX() {
		return this.xPos;
	}

	/**
	 * Returns y coordinate
	 * 
	 * @return y coordinate
	 */
	public int getY() {
		return this.yPos;
	}

}