/**
 * @author Danish Butt
 * 
 * This class is used to create a Slot type. A slot is considered to be one piece on the board.
 *  The xPos and yPos variables are used to set/get the location of slot within the 2-D array.
 */

public class Slot {

	private int xPos;
	private int yPos;
	private String name;

	// General Constructor
	public Slot(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.name = "SS";
	}

	// Setter
	public void setPos(int x, int y) {
		this.xPos = x;
		this.yPos = y;
	}

	// Getters
	public int getX() {
		return this.xPos;
	}

	public int getY() {
		return this.yPos;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

}