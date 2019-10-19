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
		if (!gamePieceList.isEmpty()) {
			if (gamePieceList.get(0) instanceof Rabbit) {
				return true;
			}
		}
		return false;
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
