import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class GameLoader {

	
	public Board loadFromFile() {
		Board board = new Board(0); //filler board
		try {
			File file = new File("saveFile.xml");
			JAXBContext loader = JAXBContext.newInstance(SaveFile.class);
			Unmarshaller loaderHelper = loader.createUnmarshaller();
			SaveFile saveFile = (SaveFile) loaderHelper.unmarshal(file);
			parseToBoard(saveFile, board);//debug
		} catch(JAXBException e) {
			e.printStackTrace();
		}
		board.addPiecesToBoard();
		return board;
	}
	
	private void parseToBoard(SaveFile saveFile, Board board) {
		//Slot[][] slotBoard = board.getBoard();
		String[][] stringBoard = saveFile.getBoard();
		ArrayList<Slot> rabbits = board.getRabbits();
		ArrayList<Slot> foxes = board.getFoxes();
		ArrayList<Slot> mushrooms = board.getMushrooms();
		ArrayList<Slot> holes = board.getHoles();
		//board.addPiecesToBoard();
		printStringBoard(stringBoard);//debug
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				String currString = stringBoard[i][j];
				if(currString.length() == 0) {
					(board.getBoard())[i][j] = new Slot(i, j);
				}
				else {
					switch(currString.substring(0, 1)) {
					case "R":
						Color tempColour = Color.GRAY;
						switch(currString.substring(1)) {
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
						tempColour = Color.BLACK; //default colour is black
						if(currString.substring(1, 2) == "R") {
							tempColour = Color.RED;
						}
						switch(currString.substring(2,3)) {
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
						if(currString.length() == 2) {
							Mushroom tempShroom = new Mushroom(i, j);
							mushrooms.add(tempShroom);
							tempHole.addGamePiece(tempShroom);
						}
						else if(currString.length() == 3) {
							tempColour = Color.GRAY;
							switch(currString.substring(2,3)) {
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
	}

	
	private void printStringBoard(String[][] pieces) { //for debug
		String print = "";
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				print += pieces[i][j] + " ";
			}
			print += "\n";
		}
		System.out.println(print);
	}
	
	
}
