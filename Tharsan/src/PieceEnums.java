import java.awt.Color;

public enum PieceEnums implements PiecesInterface {
	
	SLOT,
	MUSHROOM,
	RABBIT,
	FOX;

	private int xPos;
	private int yPos;
	private Color color;
	
	////////////////////////Fox variables////////////////////////////////////////////////////////////////////
	private boolean isFacingUp;
	private boolean isFacingRight;
	////////////////////////Fox variables////////////////////////////////////////////////////////////////////
	
	private PieceEnums() {
		this.xPos = -1;
		this.yPos = -1;
	}
	
	/**
	 * This is a setter which is used to set the initial values of x and y.
	 * @param x This corresponds to the first value [x][]
	 * @param y This corresponds to the second value [] [y] 
	 * @return void
	 */
	@Override
	public void setPos(int x, int y) {
		this.xPos = x;
		this.yPos = y;
	}
	
	public void setVertialHorizontalDir(boolean v, boolean h) {
		this.isFacingUp = v;
		this.isFacingRight = h;
	}

	@Override
	public int getX() {
		return this.xPos;
	}

	@Override
	public int getY() {
		return this.yPos;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor(Color color) {
		return this.color;
	}
	
}
