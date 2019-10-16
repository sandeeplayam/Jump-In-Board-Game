import java.util.ArrayList;

/*Author
Sudarsana Sandeep
100963087*/
public class Hole extends Slot {
	
	private ArrayList<Slot> gamePieceList;
	
	public Hole (short xPos, short yPos) {
		super(xPos, yPos);
		ArrayList<Slot> gamePieceList = new ArrayList<Slot>();
	}
	
	public boolean hasGamePiece () {
		if (gamePieceList.contains() == true) {
			return true;
		} else {
			return false;
		}
	}
	
	public void addGamePiece (Slot piece) {
		gamePieceList.add(piece);
	}
	
	public void removeGamePiece (Slot piece) {
		gamePieceList.remove(piece);
	}
}
