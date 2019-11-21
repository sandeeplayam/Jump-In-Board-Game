import java.awt.Color;
import java.util.ArrayList;

public interface MovingPiece {

	
	
	public Color getColor();
	
	public ArrayList<Integer> possibleMoves(Board board);
	public boolean canUndo();
	public void redo(Slot[][] b) ;
	public boolean undo(Slot[][] b);

	public void clearMoves();
	
}
