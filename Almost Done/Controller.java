
/**
 *The controller class acts as a middle man between the view and 
 *the model. This means that whenever there is a change in one of 
 *them the controller updates the other one. 
 *
 *@author 

*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Controller implements ActionListener {
	private Board board;
	private View view;
	private int levelNumber;
	private int xPos;
	private int yPos;
	private int xPos2;
	private int yPos2;
	private int screen;
	private LevelBuilder levelBuilder;

	/**
	 * Constructor of the controller that initializes the instance variables and
	 * takes an instance of the view
	 * 
	 * @param v: passes in an instance of the view
	 */
	public Controller(View v) {

		this.view = v;
		levelNumber = 0;

		xPos = -1;
		yPos = -1;
		xPos2 = -1;
		yPos2 = -1;
		screen = 0;
	}

	/**
	 * Method that is implemented from the action listener interface. This method is
	 * called whenever an action from a Jmenu or Jbutton object that added
	 * themselves to the action listener is pressed. It does a different action
	 * based on where the event was from
	 * 
	 * @param e: Event type (Example: event came came from button object/jmenu
	 *           object)
	 */
	public void actionPerformed(ActionEvent e) {

		switch (screen) {
		case 0:// start screen
			startMenuPerformed(e);
			break;
		case 1:// level select screen
			levelSelectPerformed(e);
			break;
		case 2:// start level screen
			startLevelPerformed(e);
			break;
		case 3:// load game screen
			loadGamePerformed(e);
			break;
		}
		if (e.getSource().getClass() == JMenuItem.class) {
			menuBarPerformed(e);
		}
	}

	private void startMenuPerformed(ActionEvent e) {
		String text;
		if (e.getSource().getClass() == JButton.class) { // if the event came from a button object
			JButton tempButton = (JButton) e.getSource();
			text = tempButton.getText();
			switch (text) {
			case "New Game":
				view.levelSelect();
				screen = 1;
				break;
			case "Load Game":
				view.loadGameScreen();
				screen = 3;
				break;
			case "Build Level":
				levelBuilder = new LevelBuilder(view);
				view.levelBuilder(levelBuilder);
				break;
			}
		}
	}

	private void levelSelectPerformed(ActionEvent e) {
		String text;
		if (e.getSource().getClass() == JButton.class) { // if the event came from a button object
			JButton tempButton = (JButton) e.getSource();
			text = tempButton.getText();
			switch (text) {
			case "Level 1":// if the button is the level 1 button
				levelNumber = 1;
				break;
			case "Level 2":// if the button is the level 2 button
				levelNumber = 2;
				break;
			case "Level 3":// if the button is the level 3 button
				levelNumber = 3;
				break;
			case "Level 4":// if the button is the level 4 button
				levelNumber = 4;
				break;
			case "Custom 1":
				levelNumber = -1;
				break;
			case "Custom 2":
				levelNumber = -2;
				break;
			case "Custom 3":
				levelNumber = -3;
				break;
			case "Start":// if the button is the start button
				if(levelNumber != 0) {
					if (levelNumber > 0) { // if the level 1 or 2 buttons were pressed and they set a level number
						board = new Board(levelNumber); // initialize the board object with the level number picked
					} else {
						Loader gameLoader = new Loader(1);
						board = gameLoader.parseToBoard(0 - levelNumber);
					}
					xPos = -1;
					yPos = -1;
					xPos2 = -1;
					yPos2 = -1;
					view.startLevel(board);
					screen = 2;
				}
				break;
			}
		}
	}

	/**
	 * Actions for startLevel screen
	 * 
	 * @param e The ActionEvent
	 */
	private void startLevelPerformed(ActionEvent e) {
		if (e.getSource().getClass() == JButton.class) { // if the event came from a button object
			int y = ((JButton) e.getSource()).getX() / 100, x = ((JButton) e.getSource()).getY() / 100;

			Slot[][] gameBoard = board.getBoard(); // get the current 2d array layout and save it to gameboard

			if (gameBoard[x][y].getClass() == Fox.class || gameBoard[x][y].getClass() == Rabbit.class
					|| (gameBoard[x][y].getClass() == Hole.class) && ((Hole) gameBoard[x][y]).hasRabbit()) {
				// set beginning coordinates
				xPos = x;
				yPos = y;
			} // if it is an object that can be moved to (slot or empty hole
			else if (gameBoard[x][y].getClass() == Slot.class
					|| (gameBoard[x][y].getClass() == Hole.class) && !((Hole) gameBoard[x][y]).hasGamePiece()) {
				// set end coordinates
				xPos2 = x;
				yPos2 = y;
			}
		}
	}

	private void loadGamePerformed(ActionEvent e) {
		String text;
		if (e.getSource().getClass() == JButton.class) {
			text = ((JButton) e.getSource()).getText();
			switch (text) {
			case "Save 1":
				levelNumber = 1;
				break;
			case "Save 2":
				levelNumber = 2;
				break;
			case "Save 3":
				levelNumber = 3;
				break;
			case "Start":
				if (levelNumber > 0) {
					Loader gameLoader = new Loader(0);
					board = gameLoader.parseToBoard(levelNumber);
					xPos = -1;
					yPos = -1;
					xPos2 = -1;
					yPos2 = -1;
					view.startLevel(board);
					screen = 2;
				}
				break;
			}
		}
	}

	private void menuBarPerformed(ActionEvent e) {
		JMenuItem tempMenuItem = (JMenuItem) e.getSource();
		String text = tempMenuItem.getText(); // get name of the object
		switch (text) {
		case "Rules":// if jmenuitem pressed was called rules
			// display the rules
			JOptionPane.showMessageDialog(view.getFrame(),
					"-Rabbits can only jump over other game pieces and they are allowed to jump over multiple pieces\n"
							+ "-Only one rabbit per hole\n" + "-Foxes move either vertically or horizontally\n"
							+ "-Game is won once all rabbits are in the hole",
					"Rules", JOptionPane.OK_OPTION, new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
			break;
		case "Quit":// if jmenuitem pressed was called quit
			// ask player if they wasnt to quit
			int optionQuit = JOptionPane.showConfirmDialog(view.getFrame(), "Are you sure you want to quit?", "Quit",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
					new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
			if (optionQuit == 0) { // if they selected yes, end the program
				System.exit(0);
			}
			break;
		case "Return to Main Menu":// if jmenuitem pressed was called return to main menu
			// confirm with player if they actually want to go to the main menu
			int optionReturn = JOptionPane.showConfirmDialog(view.getFrame(),
					"Are you sure you want to return to the main menu?", "Return to Main Menu",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
					new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
			if (optionReturn == 0) { // if they select yes
				// change the panel to the starting screen
				levelNumber = 0;

				xPos = -1;
				yPos = -1;
				xPos2 = -1;
				yPos2 = -1;

				view.startMenu();
				screen = 0;
			}
			break;
		case "Move":// if jmenuitem pressed was called move

			if (xPos + yPos != -2 && xPos2 + yPos2 != -2) {// if player selected a beginning and ending position
				if (board.move(xPos, yPos, xPos2, yPos2, 1)) { // try to move the pieces and if successful

					view.startLevel(board);

				} else { // if move was not successful
					// display dialog box saying it was an invalid move
					JOptionPane.showMessageDialog(view.getFrame(), "Invalid Move", "Invalid Move",
							JOptionPane.ERROR_MESSAGE);
				}

				if (board.checkWin()) { // if you win
					// display dialog box saying you win and asking if they wasnt to play again
					int optionWin = JOptionPane.showConfirmDialog(view.getFrame(),
							"Congratulations, you won! Would you like to play again?", "You Win",
							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
							new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
					if (optionWin == 0) { // if player selects yes

						levelNumber = 0;//
						view.getFrame().getContentPane().removeAll();
						view.startMenu();// initialize the panel that holds the board gui
						screen = 0;

					} else { // if player doesnt want to play again
						System.exit(0); // exit program
					}
				}
			} else {// if player didnt select a beginning and ending position
				// show a dialog box telling user that it was an invalid move
				JOptionPane.showMessageDialog(view.getFrame(),
						"Invalid Move: Select an object to move and a valid place to move it", "Invalid Move",
						JOptionPane.ERROR_MESSAGE);
			}

			// change pos to new coordinates so highlighting code checks new position not
			// old
			xPos = -1;
			yPos = -1;
			xPos2 = -1;
			yPos2 = -1;

			break;
		case "Undo":

			if (!board.canUndo()) {
				JOptionPane.showMessageDialog(view.getFrame(), "Cannot Undo: No moves made to undo", "Can't Undo",
						JOptionPane.ERROR_MESSAGE);
				break;
			}

			board.undo();
			view.startLevel(board);

			break;

		case "Redo":
			if (!board.canRedo()) {
				JOptionPane.showMessageDialog(view.getFrame(), "Cannot Redo: No 'Undos' made to redo", "Can't Redo",
						JOptionPane.ERROR_MESSAGE);
				break;
			}

			board.redo();
			view.startLevel(board);
			break;
		case "Hint":
			int maxSteps;
			try {
				maxSteps = Integer.parseInt(JOptionPane.showInputDialog(view.getFrame(), "Please insert the maximum amount of moves", "Max Moves", JOptionPane.INFORMATION_MESSAGE));
				Board tempBoard = new Board(board);
				Solver ts = new Solver(tempBoard, maxSteps);
				ArrayList<Integer> moves = ts.findSolution();
				if (!moves.isEmpty()) {
					JOptionPane.showMessageDialog(view.getFrame(),
							"To solve the level move the piece from coordinate " + moves.get(0) + ", " + moves.get(1)
									+ " to coordinate " + moves.get(2) + ", " + moves.get(3),
							"Hints", JOptionPane.INFORMATION_MESSAGE);

				} else {
					JOptionPane.showMessageDialog(view.getFrame(),
							"This level is unsolvable. Try Undoing or returning to main menu and picking another level.",
							"Hints", JOptionPane.INFORMATION_MESSAGE);
					
				}
			} catch(Exception exception){
				JOptionPane.showMessageDialog(view.getFrame(), "Invalid input. Input numbers only.", "Invalid input", JOptionPane.WARNING_MESSAGE);
			}
			break;
		case "Save 1":
		case "Save 2":
		case "Save 3":
			try {
				int optionSave = JOptionPane.showConfirmDialog(view.getFrame(),
						"Are you sure you want save your progress?\nAny previously saved progress in this slot will be overwritten.\nAny undo and redo information will not be saved.",
						"Save Game", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
				if (optionSave == 0) {
					int saveSlot = Integer.parseInt(text.substring(text.length() - 1));
					Saver gameSaver = new Saver(0);
					gameSaver.saveToFile(board, saveSlot);
					JOptionPane.showMessageDialog(view.getFrame(), "Game saved!", "Game Saved",
							JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
				}
			} catch (Exception exception) {
				JOptionPane.showMessageDialog(view.getFrame(), "Game was unable to be saved due to: " + exception,
						"Game Save Error", JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
			}

			break;
		}
	}

	public void setScreen(int screen) {
		this.screen = screen;
		if (screen == 2) {
			xPos = -1;
			yPos = -1;
			xPos2 = -1;
			yPos2 = -1;
		}
	}

	public void setBoard(Board board) {
		this.board = board;
	}
}