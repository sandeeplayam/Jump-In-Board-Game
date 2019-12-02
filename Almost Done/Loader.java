import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * A class used to load the contents of an XML file and convert it into a Board
 * for the user to play
 * 
 * @author David
 *
 */
public class Loader {
	private SaveFile saves;

	/**
	 * 
	 * @param mode 0 for loading progress, 1 for loading custom boards
	 */
	public Loader(int mode) {
		this.saves = loadFromFile(mode);
	}

	/**
	 * Loads a SaveFile object from an XML file
	 * 
	 * @param mode 0 for loading progress, 1 for loading custom boards
	 * @return A SaveFile object that has all the saves
	 */
	public SaveFile loadFromFile(int mode) {
		try {
			String fileName = "temp";
			if (mode == 0) {
				fileName = "saveFile.xml";
			} else if (mode == 1) {
				fileName = "customBoards.xml";
			}

			if (!fileName.equals("temp")) {
				File file = new File(fileName);
				if (file.exists()) {
					JAXBContext loader = JAXBContext.newInstance(SaveFile.class);
					Unmarshaller loaderHelper = loader.createUnmarshaller();
					SaveFile saveFile = (SaveFile) loaderHelper.unmarshal(file);
					return saveFile;
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Converts the specified contents of a SaveFile object into a playable board
	 * for the user
	 * 
	 * @param saveNumber The number of the save slot the user wants to load
	 * @return Returns a board that the user interacts with
	 */
	public Board parseToBoard(int saveNumber) {
		Board board = new Board(0); // filler board
		String[][] stringBoard;

		switch (saveNumber) {
		case 1:
			stringBoard = saves.getBoardSave1();
			break;
		case 2:
			stringBoard = saves.getBoardSave2();
			break;
		default: // else must be save 3;
			stringBoard = saves.getBoardSave3();
			break;
		}

		ArrayList<Slot> rabbits = board.getRabbits();
		ArrayList<Slot> foxes = board.getFoxes();
		ArrayList<Slot> mushrooms = board.getMushrooms();
		ArrayList<Slot> holes = board.getHoles();

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				String currString = stringBoard[i][j];
				if (currString.length() == 0) {
					(board.getBoard())[i][j] = new Slot(i, j);
				} else {
					switch (currString.substring(0, 1)) {
					case "R":
						Color tempColour = Color.GRAY;
						switch (currString.substring(1)) {
						case "W":
							tempColour = Color.WHITE;
							break;
						case "B":
							tempColour = Color.BLACK;
							break;
						case "O":
							tempColour = Color.ORANGE;
							break;
						}
						rabbits.add(new Rabbit(i, j, tempColour));
						break;
					case "F":
						tempColour = Color.BLACK; // default colour is black
						if (currString.substring(1, 2) == "R") {
							tempColour = Color.RED;
						}
						switch (currString.substring(2, 3)) {
						case "U":
							foxes.add(new Fox(i, j, i + 1, j, tempColour));
							break;
						case "D":
							foxes.add(new Fox(i, j, i - 1, j, tempColour));
							break;
						case "L":
							foxes.add(new Fox(i, j, i, j + 1, tempColour));
							break;
						case "R":
							foxes.add(new Fox(i, j, i, j - 1, tempColour));
							break;
						}
						break;
					case "H":
						Hole tempHole = new Hole(i, j);
						holes.add(tempHole);
						if (currString.length() == 2) {
							Mushroom tempShroom = new Mushroom(i, j);
							mushrooms.add(tempShroom);
							tempHole.addGamePiece(tempShroom);
						} else if (currString.length() == 3) {
							tempColour = Color.GRAY;
							switch (currString.substring(2, 3)) {
							case "W":
								tempColour = Color.WHITE;
								break;
							case "B":
								tempColour = Color.BLACK;
								break;
							case "O":
								tempColour = Color.ORANGE;
								break;
							}
							Rabbit tempRabbit = new Rabbit(i, j, tempColour);
							rabbits.add(tempRabbit);
							tempHole.addGamePiece(tempRabbit);
						}
						break;
					case "M":
						mushrooms.add(new Mushroom(i, j));
						break;
					}
				}
			}
		}

		board.addPiecesToBoard();
		return board;
	}

	/**
	 * Check if a save exists in a specified save slot
	 * 
	 * @param saveNumber The save slot number to check
	 * @return If the save exists or not
	 */
	public boolean saveExists(int saveNumber) {
		boolean saveExists = false;

		switch (saveNumber) {
		case 1:
			saveExists = saves.getBoardSave1() != null;
			break;
		case 2:
			saveExists = saves.getBoardSave2() != null;
			break;
		case 3:
			saveExists = saves.getBoardSave3() != null;
			break;
		}

		return saveExists;
	}

	/**
	 * Returns the SaveFile object
	 * 
	 * @return The SaveFile object to be returned
	 */
	public SaveFile getSaves() {
		return this.saves;
	}
}
