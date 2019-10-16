/**
 * @author Danish Butt
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
