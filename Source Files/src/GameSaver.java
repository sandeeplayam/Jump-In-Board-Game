import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class GameSaver {
	
	
	private String[][] parseBoardToString(Board board) {
		String[][] stringBoard = new String[5][5];
		Slot[][] currBoard = board.getBoard();
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if(currBoard[i][j].getClass() == Fox.class) {
					stringBoard[i][j] = parseFoxPiece((Fox) currBoard[i][j], i, j);
				}
				else {
					stringBoard[i][j] = parseNonFoxPiece(currBoard[i][j]);
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
	

	public void saveToFile(Board board) throws Exception{
		//File file = new File("saveFile.xml");
		String[][] stringBoard = parseBoardToString(board);
		JAXBContext saver = JAXBContext.newInstance(SaveFile.class);
		Marshaller saverHelper = saver.createMarshaller();
		saverHelper.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		SaveFile saveFile = new SaveFile(stringBoard);
		printStringBoard(stringBoard);//debug
		//saverHelper.marshal(saveFile, System.out);
		saverHelper.marshal(saveFile, new FileOutputStream("src/saveFile.xml"));
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
//		Board board = new Board(4);
//		GameSaver gameSaver = new GameSaver();
//		GameLoader gameLoader = new GameLoader();
//		gameSaver.saveToFile(board);
//		gameSaver.saveToFile(gameLoader.loadFromFile());
//	}

}
