
//Author
//Sudarsana Sandeep
//100963087
public class Fox extends Slot {

	private int xPos2;
	private int yPos2;
	private boolean isVertical;

	public Fox(int xPos, int yPos, int xPos2, int yPos2, String name) {
		super(xPos, yPos);
		this.xPos2 = xPos2;
		this.yPos2 = yPos2;
		super.setName(name);

		if (yPos == yPos2) {
			isVertical = true;
		} else {
			isVertical = false;
		}
	}

	public boolean getVertical() {
		return this.isVertical;
	}

	public void setPos(int x, int y, int x2, int y2) {
		super.setPos(x, y);
		this.xPos2 = x2;
		this.yPos2 = y2;
	}

	public int getTailX() {
		return xPos2;
	}

	public int getTailY() {
		return yPos2;
	}
}
