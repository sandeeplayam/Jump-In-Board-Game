import java.awt.Color;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class Saver {
	private int mode;
	private SaveFile saves;
	private Loader gameLoader;
	
	/*
	 * 0 for saving progress, 1 for saving boards
	 */
	public Saver(int mode) {
		this.mode = mode;
		this.gameLoader = new Loader(mode);
		initialize();
	}
	
	private void initialize() {
		if(gameLoader.getSaves() != null) {
			saves = gameLoader.getSaves();
		}
		else {
			saves = new SaveFile();
		}
		
	}
	
	private String[][] parseBoardToString(Board board) {
		String[][] stringBoard = new String[5][5];
		Slot[][] currBoard = board.getBoard();
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if(currBoard[i][j].getClass() == Fox.class) {
					stringBoard[i][j] = parseFoxPiece((Fox) currBoard[i][j], i, j);
//					System.out.println("parsing fox");
				}
				else {
					stringBoard[i][j] = parseNonFoxPiece(currBoard[i][j]);
//					System.out.println("parsing non fox");
				}
			}
		}
		
		return stringBoard;
	}
	
	private String parseNonFoxPiece(Slot piece) {
		String pieceString = ""; //default to empty string if regular slot
		if(piece.getClass() == Rabbit.class) {
			Color rabbitColour = ((Rabbit)piece).getColor();
			pieceString += "R";
			if(rabbitColour == Color.WHITE) {
				pieceString += "W";
			}
			else if(rabbitColour == Color.BLACK) {
				pieceString += "B";
			}
			else if(rabbitColour == Color.ORANGE) {
				pieceString += "O";
			}
			else { //grey by default
				pieceString += "G";
			}
		}
//		else if(piece.getClass() == Fox.class) {
//			pieceString += "F";
//		}
		else if(piece.getClass() == Mushroom.class) {
			pieceString += "M";
		}
		else if(piece.getClass() == Hole.class) {
			pieceString += "H";
			if(((Hole)piece).hasGamePiece()) {
				if(((Hole)piece).getGamePiece().getClass() == Rabbit.class) {
					Rabbit holeRabbit = (Rabbit) ((Hole)piece).getGamePiece();
					Color rabbitColour = holeRabbit.getColor();
					pieceString += "R";
					if(rabbitColour == Color.WHITE) {
						pieceString += "W";
					}
					else if(rabbitColour == Color.BLACK) {
						pieceString += "B";
					}
					else if(rabbitColour == Color.ORANGE) {
						pieceString += "O";
					}
					else { //grey by default
						pieceString += "G";
					}
				}
				else if(((Hole)piece).getGamePiece().getClass() == Mushroom.class){
					pieceString += "M";
				}
			}
		}
		return pieceString; 
	}
	
	private String parseFoxPiece(Fox piece, int x, int y) {
		String pieceString = "";
		//check if head or not; if not head, treat as regular slot
		if(piece.getX() == x && piece.getTailY() == y) {
			pieceString += "F";
		}
		else {
			return pieceString;
		}
		//check colour
		if(piece.getColor() == Color.RED) {
			pieceString += "R";
		}
		else { //default to black
			pieceString += "B";
		}
		//check if vertical or not
		if(piece.getVertical()) {
//			pieceString += "V";
			if(piece.getTailX() == x + 1) { //means that the tail is below the fox; head facing up
				pieceString += "U";
			}
			else {//else, tail must be above the fox; head facing down
				pieceString += "D";
			}
		}
		else {
//			pieceString += "H";
			if(piece.getTailY() == y + 1) {//means that the tail is right of the fox; head facing left
				pieceString += "L";
			}
			else {//else, tail must be left of the fox; head facing right
				pieceString += "R";
			}
		}
		
		//check if head or not
		return pieceString;
	}
	
	public void saveToFile(Board board, int saveSlot) throws Exception{
		//File file = new File("saveFile.xml");
		String fileName = "temp";
		String[][] stringBoard = parseBoardToString(board);
		JAXBContext saver = JAXBContext.newInstance(SaveFile.class);
		Marshaller saverHelper = saver.createMarshaller();
		saverHelper.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		printStringBoard(stringBoard);//debug
		
		if(mode == 0) {
			fileName = "saveFile.xml";
		}
		else if(mode == 1) {
			fileName = "customBoards.xml";
		}
		
		if(!fileName.contentEquals("temp")) {
			switch(saveSlot) {
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
	
//	public static void main(String args[]) throws Exception { //testing
//		Board board = new Board(5);
//		GameSaver gameSaver = new GameSaver(0);
//		gameSaver.saveToFile(board, 3);
////		gameSaver.saveToFile(gameLoader.loadFromFile());
//	}

}
