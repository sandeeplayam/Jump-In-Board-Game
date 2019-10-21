/**
 * 
 * The Rabbit class of a text based version of the "JumpIn" game. This
 * class contains the constructor which creates the game piece Rabbit.
 * The class also has methods which can be invoked on instance of Rabbit object.
 * 
 * @author Sudarsana Sandeep (100963087)
 *
 */
public class Rabbit extends Slot {

	/**
	 * 
	 * Constructor of Rabbit class that initializes its variables
	 * 
	 */
	public Rabbit(int xPos, int yPos, String name) {
		super(xPos, yPos);
		super.setName(name);
	}
}
