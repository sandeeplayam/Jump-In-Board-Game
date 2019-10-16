/**
 * @author Danish Butt
 * 
 * This class is used to create a Slot type. A slot is considered to be one piece on the board.
 * The xPos and yPos variables are used for the location of slot within the 2-D array. 
 * Example: [xPos][yPos] 
 */

public class Slot {

	private int xPos;
	private int yPos;
	private String name;

	/**
	 * General Constructor for Slot class
	 */
	public Slot(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.name = "SS";
	}

	/**
	 * This is a setter which is used to set the initial values of x and y.
	 * @param x This corresponds to the first value [x][]
	 * @param y This corresponds to the second value [] [y] 
	 * @return void
	 */
	public void setPos(int x, int y) {
		this.xPos = x;
		this.yPos = y;
	}

	
	/**
	 * Getters
	 */
	public int getX() {
		return this.xPos;
	}

	public int getY() {
		return this.yPos;
	}

	/**
	 * This is a setter which is used to set the name of the object
	 * @param name Name of the object (Ex. WR corresponds to white rabbit)
	 * @return void
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	public String toString() {
		return this.name;
	}

}