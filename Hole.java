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
		if (gamePieceList.isEmpty()) {
			return false;
		} 
		return true;
	}
	
	public boolean hasRabbit() {
		if (gamePieceList.hasGamePiece()) {
			
		}
	}
	
	public void addGamePiece (Slot piece) {
		if (gamePieceList.isEmpty()) {
			gamePieceList.add(piece);
		}
		
	}
	
	public void removeGamePiece (Slot piece) {
		gamePieceList.remove(piece);
	}
}
