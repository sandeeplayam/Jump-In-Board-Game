
/**
 * The Fox class of a text based version of the "JumpIn" game. This
 * class contains the constructor which creates the game piece Fox.
 * The class also has methods which can be invoked on instances of Fox object.
 * 
 * @author Sudarsana Sandeep (100963087)
 *
 */
public class Fox extends Slot {

	private int xPos2;
	private int yPos2;
	private boolean isVertical;
	
	/**
	 * 
	 * Constructor of Fox class that initializes its variables
	 * Fox is made of two pieces, the tail and the head which takes up two spaces on the board
	 * 
	 */
	public Fox(int xPos, int yPos, int xPos2, int yPos2, String name) {
		super(xPos, yPos);
		this.xPos2 = xPos2;
		this.yPos2 = yPos2;
		super.setName(name);

		if (yPos == yPos2) { 	//checking if fox is vertical
			isVertical = true;
		} else {
			isVertical = false;
		}
	}
	
	/**
	 * 
	 * Method determines if the Fox is vertical
	 * 
	 * @return boolean true if the fox is vertical positioned on the board, otherwise false
	 * 
	 */
	public boolean getVertical() {
		return this.isVertical;
	}

	/**
	 * 
	 * Method sets the position of the Fox on the board
	 * 
	 */
	public void setPos(int x, int y, int x2, int y2) {
		super.setPos(x, y); //setting both foxes head pieces
		this.xPos2 = x2; //setting first fox tail piece
		this.yPos2 = y2; //setting second fox tail piece
	}
	
	/**
	 * 
	 * Method gets the location of where the tail is located on the board of the first Fox
	 * 
	 * @return Int the coordinate of the tail of the first Fox object
	 * 
	 */
	public int getTailX() {
		return xPos2;
	}

	/**
	 * 
	 * Method gets the location of where the tail is located on the board of the second Fox
	 * 
	 * @return Int the coordinate of the tail of the second Fox object
	 * 
	 */
	public int getTailY() {
		return yPos2;
	}
}
