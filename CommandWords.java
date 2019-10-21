import java.util.ArrayList;

public class CommandWords {

	private static final String[] VALID_COMMANDS = { "quit", "rules", "commands", "move", "fox", "rabbit", "up", "down",
			"left", "right" };
	// Stores the list of rabbit and fox colours being used on the current challenge on the board
	// has so user can call toString and check
	private ArrayList<String> rabbitColours;
	private ArrayList<String> foxColours;

	/**
	 * Constructor of CommandWords class that initializes the rabbit colours class
	 */
	public CommandWords() {
		this.rabbitColours = new ArrayList<String>();
		this.foxColours = new ArrayList<String>();
	}

	/**
	 * Compares inputed command and the position of word (wordNum) to the command
	 * list to see if it is a valid command
	 * 
	 * @param command One word from the line of text the player inputs
	 * @param wordNum Position number for the word entered
	 * @return boolean whether the command entered was a valid command or not
	 */
	public boolean isCommand(String command, int wordNum) {

		switch (wordNum) {
		case 0: // If first wordis one of the first 4 strings in validCommands
			for (int i = 0; i < 4; i++) {
				if (command.equals(VALID_COMMANDS[i])) {
					return true;
				}
			}
			break;
		case 1:// If second word in user input and on the board
			//Outputs a valid response if there are no rabbits or foxes on the board
			if (this.rabbitColours.size() == 0 && command.equals("rabbit")) {
				System.out.println("There are no rabbits on the board");
				return false;
			} else if (this.foxColours.size() == 0 && command.equals("fox")) {
				System.out.println("There are no foxes on the board");
				return false;
			} else if (!command.equals("rabbit") && !command.equals("fox")) {
				System.out.println("There is no such game piece, try 'rabbit' or 'fox'");
			}

			//is either 5th or 6th strings in validCommands
			for (int i = 4; i < 6; i++) {
				if (command.equals(VALID_COMMANDS[i])) {
					return true;
				}
			}

			break;
		case 2: // If third word in user input
			//If inputted fox colour is valid return true
			for (int i = 0; i < this.foxColours.size(); i++) {
				if (command.equals(this.foxColours.get(i))) {
					return true;
				}
			}
			
			//If inputted rabbit colour is valid return true
			for (int i = 0; i < this.rabbitColours.size(); i++) {
				if (command.equals(this.rabbitColours.get(i))) {
					return true;
				}
			}
			//If a colour that isnt valid is entered, print this
			System.out.println("There is no such color, For fox ->'orange' or 'red'");
			System.out.println("                        For rabbit -> 'orange', 'white' or 'grey'");
			break;
		case 3: // If fourth word in user input
			//If a valid direction is entered (strings 7-10 in validCommands array
			for (int i = 6; i < VALID_COMMANDS.length; i++) {
				if (command.equals(VALID_COMMANDS[i])) {
					return true;
				}
			}
			System.out.println("That's not a proper direction, try 'up', 'down', 'right', or 'left'");
			break;
		}
		return false;
	}

	/**
	 * Adds the rabbit colour to the list of valid commands
	 * 
	 * @param rabbitColour the colour of a rabbit
	 */
	public void addRabbitColour(String rabbitColour) {
		this.rabbitColours.add(rabbitColour);
	}

	/**
	 * Adds the fox colour to the list of valid commands
	 * 
	 * @param foxColour the colour of a fox
	 */
	public void addFoxColour(String foxColour) {
		this.foxColours.add(foxColour);
	}

	/**
	 * Converts the list of valid commands to a string with spaces in between each
	 * command
	 * 
	 * @return s String of all valid commands
	 */
	public String toString() {
		String s = "";
		for (String command : VALID_COMMANDS) {
			s += command + " ";
		}
		for (int i = 0; i < this.rabbitColours.size(); i++) {
			s += this.rabbitColours.get(i) + " ";
		}
		for (int i = 0; i < this.foxColours.size(); i++) {
			s += this.foxColours.get(i) + " ";
		}
		return s;
	}
}
