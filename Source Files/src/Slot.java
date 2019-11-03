/** 
 * This class is used to create a Slot type. A slot is considered to be one piece on the board.
 * The xPos and yPos variables are used for the location of slot within the 2-D array. 
 * Example: [xPos][yPos] 
 * 
 * Milestone 1:
 * @author Danish Butt
 * Milestone 2:
 * @author Tharsan Sivathasan
 */

public class Slot {

	private int xPos;
	private int yPos;

	/**
	 * General Constructor for Slot class
	 */
	public Slot(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
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

}