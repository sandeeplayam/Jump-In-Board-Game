import java.awt.Color;
import java.util.ArrayList;

public interface MovingPiece {

	
	
	public Color getColor();
	
	public ArrayList<Integer> possibleMoves(Board board);
}
