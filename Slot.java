/**
@author Danish Butt
*/


public class Slot {

	private short xPos;
	private short yPos;
	
	
	//General Constructor
	public Slot (short xPos, short yPos) {
		this.xPos=xPos;
		this.yPos=yPos;
		
	}
	
	
	//Setter
	public void setPos(short x, short y) {
		this.xPos=x;
		this.yPos=y;
	}
	

	//Getters
	public short getX() {
		return xPos;
	}

	public short getY() {
		return yPos;
	}
	

}
