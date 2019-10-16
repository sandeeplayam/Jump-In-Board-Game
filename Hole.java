import java.util.ArrayList;

/*Author
Sudarsana Sandeep
100963087*/
public class Hole extends Slot {

	private ArrayList<Slot> gamePieceList;

	public Hole(int xPos, int yPos) {
		super(xPos, yPos);
		this.gamePieceList = new ArrayList<Slot>();
		super.setName("HH");
	}

	public boolean hasGamePiece() {
		if (gamePieceList.isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean hasRabbit() {
<<<<<<< HEAD
<<<<<<< HEAD
		if (gamePieceList.hasGamePiece()) {
			
		}
=======
		if (gamePieceList.get(0) instanceof Rabbit) {
			return true;
		}
		return false;
		
>>>>>>> 674301fe4cddd5f0471bdc80d1737d50b10f1e71
=======
		if (!gamePieceList.isEmpty()) {
			if (gamePieceList.get(0) instanceof Rabbit) {
				return true;
			}
		}
		return false;

>>>>>>> c4a8f8da078a60ec3c9b97453c85560f2fe6fd37
	}

	public void addGamePiece(Slot piece) {
		if (this.gamePieceList.isEmpty()) {
			this.gamePieceList.add(piece);
		}

	}

	public void removeGamePiece() {
		this.gamePieceList.remove(0);
	}
	
	@Override
	public String toString() {
		if (this.hasGamePiece()) {
			return gamePieceList.get(0).toString();
		} else {
			return super.toString();
		}
		
	}
}
