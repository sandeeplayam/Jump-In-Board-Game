import java.awt.Color;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 * A class that is used to convert a Board into an XML file.
 * 
 * @author David Ou
 *
 */
public class Saver {
	private int mode;
	private SaveFile saves;
	private Loader gameLoader;

	/**
	 * 
	 * @param mode 0 for saving progress, 1 for saving custom boards
	 */
	public Saver(int mode) {
		this.mode = mode;
		this.gameLoader = new Loader(mode);
		initialize();
	}

	/**
	 * Checks if there are existing saves and gets them
	 */
	private void initialize() {
		if (gameLoader.getSaves() != null) {
			saves = gameLoader.getSaves();
		} else {
			saves = new SaveFile();
		}
	}

	/**
	 * Converts a board parameter to a 2D String array
	 * 
	 * @param board Board to be converted
	 * @return 2D String array version of Board
	 */
	public String[][] parseBoardToString(Board board) {
		String[][] stringBoard = new String[5][5];
		Slot[][] currBoard = board.getBoard();

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (currBoard[i][j].getClass() == Fox.class) {
					stringBoard[i][j] = parseFoxPiece((Fox) currBoard[i][j], i, j);
				} else {
					stringBoard[i][j] = parseNonFoxPiece(currBoard[i][j]);
				}
			}
		}
		return stringBoard;
	}

	/**
	 * Converts a non-fox piece into a String encoded version of it
	 * 
	 * @param piece Piece to be encoded
	 * @return Encoded String
	 */
	public String parseNonFoxPiece(Slot piece) {
		String pieceString = ""; // default to empty string if regular slot
		if (piece.getClass() == Rabbit.class) {
			Color rabbitColour = ((Rabbit) piece).getColor();
			pieceString += "R";
			if (rabbitColour == Color.WHITE) {
				pieceString += "W";
			} else if (rabbitColour == Color.BLACK) {
				pieceString += "B";
			} else if (rabbitColour == Color.ORANGE) {
				pieceString += "O";
			} else { // grey by default
				pieceString += "G";
			}
		} else if (piece.getClass() == Mushroom.class) {
			pieceString += "M";
		} else if (piece.getClass() == Hole.class) {
			pieceString += "H";
			if (((Hole) piece).hasGamePiece()) {
				if (((Hole) piece).getGamePiece().getClass() == Rabbit.class) {
					Rabbit holeRabbit = (Rabbit) ((Hole) piece).getGamePiece();
					Color rabbitColour = holeRabbit.getColor();
					pieceString += "R";
					if (rabbitColour == Color.WHITE) {
						pieceString += "W";
					} else if (rabbitColour == Color.BLACK) {
						pieceString += "B";
					} else if (rabbitColour == Color.ORANGE) {
						pieceString += "O";
					} else { // grey by default
						pieceString += "G";
					}
				} else if (((Hole) piece).getGamePiece().getClass() == Mushroom.class) {
					pieceString += "M";
				}
			}
		}
		return pieceString;
	}

	/**
	 * Converts a fox pieceinto a String encoded version of it
	 * 
	 * @param piece Fox piece to be converted
	 * @param x     Current x coordinate
	 * @param y     Current y coordinate
	 * @return Encoded String
	 */
	public String parseFoxPiece(Fox piece, int x, int y) {
		String pieceString = "";
		// check if head or not; if not head, treat as regular slot
		if (piece.getX() == x && piece.getY() == y) {
			pieceString += "F";
		} else {
			return pieceString;
		}
		// check colour
		if (piece.getColor() == Color.RED) {
			pieceString += "R";
		} else { // default to black
			pieceString += "B";
		}
		// check if vertical or not
		if (piece.getVertical()) {
			if (piece.getTailX() == x + 1) { // means that the tail is below the fox; head facing up
				pieceString += "U";
			} else {// else, tail must be above the fox; head facing down
				pieceString += "D";
			}
		} else {
			if (piece.getTailY() == y + 1) {// means that the tail is right of the fox; head facing left
				pieceString += "L";
			} else {// else, tail must be left of the fox; head facing right
				pieceString += "R";
			}
		}
		return pieceString;
	}

	/**
	 * Saves a board to the save slot indicated by saveSlot parameter
	 * 
	 * @param board    Progress to be saved
	 * @param saveSlot Save slot number to be saved to
	 * @throws Exception potential JAXBException when converting to XML file
	 */
	public void saveToFile(Board board, int saveSlot) throws Exception {
		String fileName = "temp";
		String[][] stringBoard = parseBoardToString(board);
		JAXBContext saver = JAXBContext.newInstance(SaveFile.class);
		Marshaller saverHelper = saver.createMarshaller();
		saverHelper.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		printStringBoard(stringBoard);// debug

		if (mode == 0) {
			fileName = "saveFile.xml";
		} else if (mode == 1) {
			fileName = "customBoards.xml";
		}

		if (!fileName.contentEquals("temp")) {
			switch (saveSlot) {
			case 1:
				saves.setBoardSave1(stringBoard);
				break;
			case 2:
				saves.setBoardSave2(stringBoard);
				break;
			case 3:
				saves.setBoardSave3(stringBoard);
				break;
			}
			saverHelper.marshal(saves, System.out);
			saverHelper.marshal(saves, new FileOutputStream(fileName));
		}
	}

	public void printStringBoard(String[][] pieces) { // for debug
		String print = "";
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				print += pieces[i][j] + " ";
			}
			print += "\n";
		}
		System.out.println(print);
	}
}
