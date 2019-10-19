/**
 * @author Danish Butt
 * This class is used to create a mushroom that is placed on the board
 */

public class Mushroom extends Slot {

	/** General Constructor
	 * 
	 * Set's the initial position of the mushroom. Also set's the name of the mushroom (MM).
	 */
	public Mushroom(int xPos, int yPos) {
		super(xPos, yPos);
		super.setName("MM");
	}
}