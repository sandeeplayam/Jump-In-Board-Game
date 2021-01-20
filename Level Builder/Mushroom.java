/**
 * The Mushroom class of java game based on the children's game "JumpIn". This class
 * contains the constructor which creates a instance of a Mushroom. The class also
 * has methods which can be invoked on the Mushroom instance. It extends the Slot
 * class and gets the variables and methods from that class
 * 
 * Milestone 1 author: Danish; Milestone 2 author: Tharsan
 * 
 * @author Danish Butt
 * @author Tharsan Sivathasan
 */

public class Mushroom extends Slot {

	/**
	 * Constructor for Mushroom class that accepts coordinates and stores those
	 * coordinates
	 * 
	 * @param xPos x coordinate of object
	 * @param yPos x coordinate of object
	 */
	public Mushroom(int xPos, int yPos) {
		super(xPos, yPos);
	}
}