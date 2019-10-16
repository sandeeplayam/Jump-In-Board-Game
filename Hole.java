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
<<<<<<< HEAD
		if (gamePieceList.hasGamePiece()) {
			
		}
=======
		if (gamePieceList.get(0) instanceof Rabbit) {
			return true;
		}
		return false;
		
>>>>>>> 674301fe4cddd5f0471bdc80d1737d50b10f1e71
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
