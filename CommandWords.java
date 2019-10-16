import java.util.ArrayList;

public class CommandWords {

	private static final String[] validCommands = { "quit", "rules", "commands", "move", "fox", "rabbit", "up", "down",
			"left", "right" };
	// Stores the list of rabbit and fox colours the current challenge on the board
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
		case 0: // If first word in user input
			for (int i = 0; i < 4; i++) {
				if (command.equals(validCommands[i])) {
					return true;
				}
			}
			break;
		case 1:// If second word in user input
			for (int i = 4; i < 6; i++) {
				if (command.equals(validCommands[i])) {
					return true;
				}
			}
			break;
		case 2: // If third word in user input
			for (int i = 0; i < this.rabbitColours.size(); i++) {
				if (command.equals(this.rabbitColours.get(i))) {
					return true;
				}
			}
			for (int i = 0; i < this.foxColours.size(); i++) {
				if (command.equals(this.foxColours.get(i))) {
					return true;
				}
			}
			break;
		case 3: // If fourth word in user input
			for (int i = 6; i < validCommands.length; i++) {
				if (command.equals(validCommands[i])) {
					return true;
				}
			}
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
		for (String command : validCommands) {
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
