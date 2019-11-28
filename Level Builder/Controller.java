
/**
 *The controller class acts as a middle man between the view and 
 *the model. This means that whenever there is a change in one of 
 *them the controller updates the other one. 
 *
 *@author 

*/

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
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
	Slot[][] tempBoard;
	JButton button1, button2;

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
		tempBoard = new Slot[5][5];
		button1 = new JButton("temp1");
		button2 = new JButton("temp2");
	}

	/**
	 * Method that is implemented from the action listener interface. This method is
	 * called whenever an action from a Jmenu or Jbutton object that added
	 * themselves to the action listener is pressed. It does a different action
	 * based on where the event was from
	 * 
	 * @param e: Event type (Example: event came came from button object/jmenu
	 *        object)
	 */
	public void actionPerformed(ActionEvent e) {

		switch (screen) {
		case 0:// start screen
			startMenuPerformed(e);
			break;
		case 1:// level select screen
			levelSelectPerformed(e);
			break;
		case 2:// level builder screen
			levelBuilderPerformed(e);
			break;
		case 3:// start level screen
			startLevelPerformed(e);
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
			if (text.equals("New Game")) {// if the button is the new game button
				view.levelSelect();
				screen = 1;
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
			case "Level 5":// if the button is the level 4 button
				levelNumber = 5;
				break;
			case "Create Level":
				xPos = -1;
				yPos = -1;
				xPos2 = -1;
				yPos2 = -1;
				view.levelBuilder();
				screen = 2;
				break;
			case "Start":// if the button is the start button
				if (levelNumber > 0) { // if the level 1 or 2 buttons were pressed and they set a level number
					board = new Board(levelNumber); // initialize the board object with the level number picked
					xPos = -1;
					yPos = -1;
					xPos2 = -1;
					yPos2 = -1;
					view.startLevel(board); // initialize the panel that holds the board gui
					screen = 3;
				}
				break;
			}
		}
	}

	private void levelBuilderPerformed(ActionEvent e) {
//		int y = ((JButton) e.getSource()).getX() / 90, x = ((JButton) e.getSource()).getY() / 90;
//		System.out.println(x);
//		System.out.println(y);
//		JButton button1, button2;

		if (button1.getText().equals("temp1")) {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					tempBoard[i][j] = new Slot(i, j);
				}
			}
			tempBoard[0][0] = new Hole(0, 0);
			tempBoard[0][4] = new Hole(0, 4);
			tempBoard[2][2] = new Hole(2, 2);
			tempBoard[4][0] = new Hole(4, 0);
			tempBoard[4][4] = new Hole(4, 4);
		}

//		JButton tempButton = (JButton) e.getSource();
		ImageIcon imageIcon;
		Image image;
		String fileName = "";
		GridBagConstraints gbc = new GridBagConstraints();

		int gridHeight = 1; // set height of the column to 1
		int gridWidth = 1; // set width of the column to 1

		if (e.getSource().getClass() == JButton.class) { // if the event came from a button object
			switch (((JButton) e.getSource()).getText()) {
			case "Add Rabbit":
				view.setPieceOptionsEnabled(0, false);
				view.setRabbitOptionsEnabled(true);
				break;
			case "Add Fox":
				view.setPieceOptionsEnabled(0, false);
				//only allow direction if tail is acceptable///////////////////////////////////
				//////////////////////////////////////////////////////////////////////////////
				if (xPos > 0) {
					view.setFoxOptionsEnabled(3, true);
				}
				if (xPos < 4) {
					view.setFoxOptionsEnabled(1, true);
				}
				if (yPos > 0) {
					view.setFoxOptionsEnabled(2, true);
				}
				if (yPos < 4) {
					view.setFoxOptionsEnabled(4, true);
				}
				break;
			case "Add Mushroom":
				fileName = "mushroom image.png";
				tempBoard[xPos][yPos] = new Mushroom(xPos, yPos);
				view.setPieceOptionsEnabled(0, false);
				break;
			case "Remove Piece":
				fileName = "empty slot.png";
				if (tempBoard[xPos][yPos].getClass() == Fox.class){
					tempBoard[xPos][yPos] = new Slot(xPos, yPos);
					Fox tempFox = (Fox) tempBoard[xPos][yPos];
					tempBoard[tempFox.getTailX()][tempFox.getTailY()] = new Slot(xPos, yPos);
				} else {
					tempBoard[xPos][yPos] = new Slot(xPos, yPos);
				}
				view.setPieceOptionsEnabled(0, false);
				break;
			case "Gray":
				fileName = "Grabbit.png"; // change to grey rabbit image file
				tempBoard[xPos][yPos] = new Rabbit(xPos, yPos, Color.GRAY);
				view.setRabbitOptionsEnabled(false);
				break;
			case "Orange":
				fileName = "Orabbit.png"; // change to grey rabbit image file
				tempBoard[xPos][yPos] = new Rabbit(xPos, yPos, Color.ORANGE);
				view.setRabbitOptionsEnabled(false);
				break;
			case "White":
				fileName = "Wrabbit.png"; // change to grey rabbit image file
				tempBoard[xPos][yPos] = new Rabbit(xPos, yPos, Color.WHITE);
				view.setRabbitOptionsEnabled(false);
				break;
			case "North":
				fileName = "Fox Hup.png"; // change to vertical head up fox file
				
//				button1.getParent().add(comp, gbc);;
				view.setFoxOptionsEnabled(0, false);
				break;
			case "East":
				fileName = "Fox Hright.png"; // change to horizontal head left fox file
				view.setFoxOptionsEnabled(0, false);
				break;
			case "South":
				fileName = "Fox Hdown.png"; // change to vertical head down fox file
				view.setFoxOptionsEnabled(0, false);
				break;
			case "West":
				fileName = "Fox Hleft.png"; // change to horizontal head right fox file
				view.setFoxOptionsEnabled(0, false);
				break;
			default:
				int y = ((JButton) e.getSource()).getX() / 80, x = ((JButton) e.getSource()).getY() / 80;
//				// set end coordinates
//				xPos2 = xPos;
//				yPos2 = yPos;
//				// set beginning coordinates
				xPos = x;
				yPos = y;
//				System.out.println(xPos + ", " + yPos + ": " + xPos2 + ", " + yPos2);
				
				gbc.gridx = x; // set row number to the row number of the 2d array
				gbc.gridy = y; // set column number to the column number of the 2d array
				gbc.gridheight = 1; // set height of the column to 1
				gbc.gridwidth = 1; // set width of the column to 1

				System.out.println(x + ", " + y + " " + tempBoard[x][y].getClass());

//				button2 = button1;
				button1 = (JButton) e.getSource();

				if (tempBoard[x][y].getClass() == Rabbit.class
						|| (tempBoard[x][y].getClass() == Hole.class && ((Hole) tempBoard[x][y]).hasGamePiece())
						|| tempBoard[x][y].getClass() == Mushroom.class || tempBoard[x][y].getClass() == Fox.class) {
					view.setPieceOptionsEnabled(4, true);
				} else {
					view.setPieceOptionsEnabled(2, true);
					if (tempBoard[x][y].getClass() == Fox.class){
						//only allow direction if tail is acceptable///////////////////////////
						//////////////////////////////////////////////////////////////////////
						view.setPieceOptionsEnabled(3, true);
					}
				}
				break;
			}

			if (!fileName.equals("")) {
				imageIcon = new ImageIcon(getClass().getResource(fileName));
				image = imageIcon.getImage();
				// set size of the image to 100 by 100 (same size as the button)
				image = image.getScaledInstance(gridWidth * 80, gridHeight * 80, Image.SCALE_SMOOTH);
				imageIcon = new ImageIcon(image);
				button1.setIcon(imageIcon); // set picture in icon to image

				fileName = "";
			}
			System.out.println(((JButton) e.getSource()).getWidth() + " " + ((JButton) e.getSource()).getHeight());
		}

	}

	private void startLevelPerformed(ActionEvent e) {
		if (e.getSource().getClass() == JButton.class) { // if the event came from a button object
			int y = ((JButton) e.getSource()).getX() / 100, x = ((JButton) e.getSource()).getY() / 100;

			Slot[][] gameBoard = board.getBoard(); // get the current 2d array layout and save it to gameboard

			 System.out.println(gameBoard[x][y].getClass());
			// if its a object that can be moved
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
					int optionWin = JOptionPane.showConfirmDialog(null,
							"Congratulations, you won! Would you like to play again?", "You Win",
							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
							new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
					if (optionWin == 0) { // if player selects yes

						levelNumber = 0;//
						view.getFrame().getContentPane().removeAll();
						view.startMenu();// initialize the panel that holds the board gui

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
			Board tempBoard = new Board(board, this.levelNumber);

			// Solver s = new Solver(tempBoard);
			// ArrayList<Integer> moves = s.findSolution();

			tempSolver ts = new tempSolver(tempBoard);
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
			break;
		}
	}

	public void resetCoordinates() {
		xPos = -1;
		yPos = -1;
		xPos2 = -1;
		yPos2 = -1;
	}
}