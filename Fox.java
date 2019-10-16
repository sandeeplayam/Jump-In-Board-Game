
//Author
//Sudarsana Sandeep
//100963087
public class Fox extends Slot {
	
	private short xPos2;
	private short yPos2;
	private boolean isVertical;
	private String foxColour;
	
	public Fox (short xPos, short yPos, short xPos2, short yPos2, String foxColour) {
		super(xPos, yPos);
		this.xPos2 = xPos2;
		this.yPos2 = yPos2;
		this.foxColour = foxColour;
		
		if (yPos == yPos2) {
			isVertical = true;
		} else {
			isVertical = false;
		}
	}
	
	public boolean getVertical() {
		return this.isVertical;
	}
	
	public void setFox (short x, short y, short x2, short y2) {
		super.setPos(x, y);
		this.xPos2 = x2;
		this.yPos2 = y2;
	}
	
	public Slot getTailX() {
		return xPos2;
	}
	
	public Slot getTailY() {
		return yPos2;
	}
}
