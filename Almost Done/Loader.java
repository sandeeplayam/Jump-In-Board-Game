import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Loader {
//	private int saveNumber;
	private SaveFile saves;
	
	/*
	 * 0 for saving progress, 1 for saving boards
	 */
	public Loader(int mode) {//int saveNumber) {
//		this.saveNumber = saveNumber;
		this.saves = loadFromFile(mode);
	}
	
	private SaveFile loadFromFile(int mode) {
//		Board board = new Board(0); //filler board
		try {
			String fileName = "temp";
			if(mode == 0) {
				fileName = "saveFile.xml";
			}
			else if(mode == 1) {
				fileName = "customBoards.xml";
			}
			
			if(!fileName.equals("temp")) {
				File file = new File(fileName);
				if(file.exists()) {
					JAXBContext loader = JAXBContext.newInstance(SaveFile.class);
					Unmarshaller loaderHelper = loader.createUnmarshaller();
					SaveFile saveFile = (SaveFile) loaderHelper.unmarshal(file);
					return saveFile;
				}
			}
//			File file = new File("saveFile.xml");
//			if(file.exists()) {
//				JAXBContext loader = JAXBContext.newInstance(SaveFile.class);
//				Unmarshaller loaderHelper = loader.createUnmarshaller();
//				SaveFile saveFile = (SaveFile) loaderHelper.unmarshal(file);
//				return saveFile;
//			}
//			JAXBContext loader = JAXBContext.newInstance(SaveFile.class);
//			Unmarshaller loaderHelper = loader.createUnmarshaller();
//			SaveFile saveFile = (SaveFile) loaderHelper.unmarshal(file);
//			return saveFile;
//			parseToBoard(saveFile, board);//debug
		} catch(JAXBException e) {
			e.printStackTrace();
		}
		return null;
//		board.addPiecesToBoard();
//		return board;
	}
	
	public Board parseToBoard(int saveNumber) {//SaveFile saveFile, Board board) {
		Board board = new Board(0); //filler board
		String[][] stringBoard;// = saveFile.getBoard();
		
		switch(saveNumber) {
		case 1:
			stringBoard = saves.getBoardSave1();
			break;
		case 2:
			stringBoard = saves.getBoardSave2();
			break;
		default: //else must be save 3;
			stringBoard = saves.getBoardSave3();
			break;
		}
		
		ArrayList<Slot> rabbits = board.getRabbits();
		ArrayList<Slot> foxes = board.getFoxes();
		ArrayList<Slot> mushrooms = board.getMushrooms();
		ArrayList<Slot> holes = board.getHoles();
		//board.addPiecesToBoard();
		printStringBoard(stringBoard);//debug
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				String currString = stringBoard[i][j];
//				System.out.println("making a :" + currString);//debug
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
//						System.out.println("adding rabbit");//debug
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
//						System.out.println("adding fox");//debug
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
//							System.out.println("adding rabbit");//debug
						}
//						System.out.println("adding hole");//debug
						break;
					case "M":
						mushrooms.add(new Mushroom(i, j));
//						System.out.println("adding mushroom");
						break;
					}
				}
			}
		}
		board.addPiecesToBoard();
		return board;
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
	
	public boolean saveExists(int saveNumber) {
		boolean saveExists = false;	
		
//		if(saves == null) {
//			return saveExists;
//		}
//		
		switch(saveNumber) {
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
	
	public SaveFile getSaves() {
		return saves;
	}
	
//	public static void main(String args[]) {
//		GameLoader gameLoader = new GameLoader(0);
//		
//	}
	
}
