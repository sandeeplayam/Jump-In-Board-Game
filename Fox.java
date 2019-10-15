//Author
//Sudarsana Sandeep
//100963087
public class Fox extends Slot {
	
	private short xPos2;
	private short yPos2;
	private boolean isVertical;
	
	public Fox (short xPos, short yPos, short xPos2, short yPos2) {
		super(xPos, yPos);
		this.xPos2 = xPos2;
		this.yPos2 = yPos2;
		
		if (yPos == yPos2) {
			isVertical = true;
		} else {
			isVertical = false;
		}
	}
	
	public boolean getVertical() {
		return this.isVertical;
	}
}
