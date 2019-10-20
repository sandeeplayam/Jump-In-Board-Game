
import java.util.HashMap;
import java.util.Scanner;

/**
 * The Game class of a text based version of the "JumpIn" children's game. This
 * class contains the main method which creates the game instance allowing you
 * to play the game
 * 
 * @author tharsan
 *
 */

public class Game {

	private Board gameBoard;
	private Rabbit rabbitWhite, rabbitOrange, rabbitGrey;
	private Fox foxRed, foxOrange;
	private Mushroom mushroom1, mushroom2, mushroom3;
	private Hole hole1, hole2, hole3, hole4, hole5;
	private HashMap<String, Slot> gamePieces;
	private boolean quitGame;
	private CommandWords commandWords;

	public static void main(String[] args) {
		Game game = new Game();
		game.play();
	}

	/**
	 * Constructor of Game class that initializes the variables
	 */
	public Game() {
		this.quitGame = false;
		this.commandWords = new CommandWords();
		this.gamePieces = new HashMap<>();
	}

	/**
	 * This method contains the loop the game repeats until the player quits the
	 * game or wins
	 */
	public void play() {

		Scanner sc = new Scanner(System.in);
		int chal; // stores the challenge number
		String commandInput; // Stores the command the user enters into the game

		do {
			this.quitGame = false;

			// Accept a value from player to specify which difficulty to play on
			System.out.print("Enter challenge number (1 for easy, 2 for hard): ");
			chal = sc.nextInt();

			while (chal < 1 || chal > 2) { // repeat until challenge number is either 1 or 2
				System.out.print("Not a valid challenge number, Enter '1' for easy or '2' for hard: ");
				chal = sc.nextInt();
			}
			createBoard(chal);

			// clear(consume) rest of line for scanner
			sc.nextLine();

			System.out.println("Welcome to a text based version of the game JUMP IN'");
			printRules();
			printCommands();

			do {
				printBoard();
				// input command repeatedly until a valid command is entered
				do {
					System.out.print(">");
					commandInput = sc.nextLine();
				} while (parseCommand(commandInput) == false);
			} while (this.gameBoard.checkWin() == false && this.quitGame == false); // repeat as long as player didnt
																					// win or quit

			if (this.gameBoard.checkWin()) {// if won game
				printBoard();
				System.out.println("You won!");
				
				//Ask user if they would like to play again and store result in boolean replay 
				System.out.print("Would you like to play again (yes/no)?\n>");
				String input = sc.nextLine();
				while (!input.equals("yes") && !input.equals("no")) { //repeat until input is 'yes' or 'no'
					System.out.print("Not a valid input. Enter 'yes' or 'no'\n>");
					input = sc.nextLine();
				}
				if (input.equals("no")) { //if player doesnt want to play again, set replay quitgame to true
					this.quitGame = true;
				}
			} 
			
			if (this.quitGame) {//if quit true print a message
				System.out.println("Thanks for playing!");
			}
		} while (!this.quitGame); // repeat as long as player wishes to replay or until player quits game

		sc.close();
	}

	/**
	 * This method changes the words into recognizable commands and based on the
	 * command performs an action
	 * 
	 * @param commandInput the line of text the user inputed in play method
	 * @return boolean if the game must receive another input from user
	 */
	public boolean parseCommand(String commandInput) {
		String[] words = commandInput.split(" ");

		// convert command to lowercase
		for (int i = 0; i < words.length; i++) {
			words[i] = words[i].toLowerCase();
		}

		if (words.length > 0) {
			switch (words[0]) {
			case "move":
				if (words.length == 4) { // if there are 4 words with first being 'move'
					// if rest of words are a valid command word
					if (this.commandWords.isCommand(words[1], 1) && this.commandWords.isCommand(words[2], 2)
							&& this.commandWords.isCommand(words[3], 3)) {

						// if there's a piece with proper colors
						if (gamePieces.containsKey(words[1] + " " + words[2])) {
							// Call the Board class move function to move the gamepiece (words 1 and 2) in
							// the direction (word 3)
							boolean moveHappened = this.gameBoard.move(this.gamePieces.get(words[1] + " " + words[2]),
									words[3]);

							if (moveHappened) {
								return true;
							} else {
								return false;
							}
						}

						System.out.println("There's no such " + words[1]);
						return false;
					} else {
						return false;
					}
				}
				break;
			case "rules": // if command is rules call print rules method
				printRules();
				return false;
			case "commands": // if command is commands call print commands method
				printCommands();
				return false;
			case "quit": // if command is quit set quit game variable to true
				this.quitGame = true;
				return true;
			}
		}

		// If nothing is typed in or if correct format isn't used
		System.out.println("Command not recognized. Try again or type help to learn how to play.");
		return false;
	}

	/**
	 * Set up the Board initial layout based on a random integer
	 * 
	 * @param challengeNum
	 */
	public void createBoard(int challengeNum) {
		this.gameBoard = new Board();

		switch (challengeNum) {
		case 1:
			this.rabbitWhite = new Rabbit(0, 1, "RW");
			this.gamePieces.put("rabbit white", this.rabbitWhite);
			this.rabbitOrange = new Rabbit(4, 2, "RO");
			this.gamePieces.put("rabbit orange", this.rabbitOrange);
			this.hole1 = new Hole(0, 0);
			this.hole2 = new Hole(0, 4);
			this.hole3 = new Hole(2, 2);
			this.hole4 = new Hole(4, 0);
			this.hole5 = new Hole(4, 4);
			this.mushroom1 = new Mushroom(0, 3);
			this.mushroom2 = new Mushroom(1, 2);
			this.mushroom3 = new Mushroom(3, 2);
			this.gameBoard.addPiece(this.rabbitWhite);
			this.gameBoard.addPiece(this.rabbitOrange);
			this.gameBoard.addPiece(this.hole1);
			this.gameBoard.addPiece(this.hole2);
			this.gameBoard.addPiece(this.hole3);
			this.gameBoard.addPiece(this.hole4);
			this.gameBoard.addPiece(this.hole5);
			this.gameBoard.addPiece(this.mushroom1);
			this.gameBoard.addPiece(this.mushroom2);
			this.gameBoard.addPiece(this.mushroom3);

			this.commandWords.addRabbitColour("white");
			this.commandWords.addRabbitColour("orange");
			break;

		case 2:
			this.rabbitWhite = new Rabbit(1, 3, "RW");
			this.gamePieces.put("rabbit white", this.rabbitWhite);
			this.rabbitOrange = new Rabbit(2, 4, "RO");
			this.gamePieces.put("rabbit orange", this.rabbitOrange);
			this.rabbitGrey = new Rabbit(4, 3, "RG");
			this.gamePieces.put("rabbit grey", this.rabbitGrey);
			this.foxRed = new Fox(1, 1, 1, 0, "FR");
			this.gamePieces.put("fox red", this.foxRed);
			this.foxOrange = new Fox(3, 1, 2, 1, "FO");
			this.gamePieces.put("fox orange", this.foxOrange);
			this.hole1 = new Hole(0, 0);
			this.hole2 = new Hole(4, 0);
			this.mushroom2 = new Mushroom(4, 0);
			this.hole2.addGamePiece(this.mushroom2);
			this.hole4 = new Hole(0, 4);
			this.mushroom1 = new Mushroom(0, 4);
			this.hole4.addGamePiece(mushroom1);

			this.hole3 = new Hole(2, 2);
			this.hole5 = new Hole(4, 4);

			this.mushroom3 = new Mushroom(3, 2);

			this.gameBoard.addPiece(this.rabbitWhite);
			this.gameBoard.addPiece(this.rabbitOrange);
			this.gameBoard.addPiece(this.rabbitGrey);
			this.gameBoard.addPiece(this.foxRed);
			this.gameBoard.addPiece(this.foxOrange);
			this.gameBoard.addPiece(this.hole1);
			this.gameBoard.addPiece(this.hole2);
			this.gameBoard.addPiece(this.hole3);
			this.gameBoard.addPiece(this.hole4);
			this.gameBoard.addPiece(this.hole5);
			this.gameBoard.addPiece(this.mushroom1);
			this.gameBoard.addPiece(this.mushroom2);
			this.gameBoard.addPiece(this.mushroom3);

			this.commandWords.addRabbitColour("white");
			this.commandWords.addRabbitColour("orange");
			this.commandWords.addRabbitColour("grey");

			this.commandWords.addFoxColour("red");
			this.commandWords.addFoxColour("orange");

			break;
		}

	}

	/**
	 * Prints the text based version of the board
	 * 
	 * @see http://www.smartgamesandpuzzles.com/inventor/JumpIn.html for the expaned
	 *      game rules
	 */
	public void printBoard() {
		System.out.println(this.gameBoard.toStringBoard()); // gets the string of board from Board class
	}

	/**
	 * Prints the opening line of the game
	 */
	public void printRules() {
		System.out.println("The goal is to get all of the rabbits into a hole. Rabbits must jump over "
				+ "atleast one object, landing on the first empty space after the obstacle or they "
				+ "cannot move. Foxes can only slide backwards and fowards but cannot jump over objects.");
	}

	/**
	 * Prints the help dialog that appears whenever the player enters the help
	 * command and at the beginning of the game with the Welcome dialogue in
	 * printWelcome
	 */
	public void printCommands() {
		System.out.println(
				"To move a rabbit or fox type \"move\", the animal type, the colour of the animal, and direction.\n"
						+ "Type 'rules' for how to play, 'commands' if you would like the list of commands, or 'quit' if you would like stop playing.\n"
						+ "The command list is: " + this.commandWords.toString() + ".\n"
						+ "Examples of working commands are: \"move rabbit white up\" \"move fox red right\" \"rules\" \"commands\" \"quit\"");
	}

}
