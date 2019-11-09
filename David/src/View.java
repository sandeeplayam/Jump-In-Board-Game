import javax.swing.*;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.event.*;

/**
 * This class is what controls the View and controller portion of the MVC for
 * the game JumpIn'. This class gets the data of the pieces from the board class
 * and shows a gui. it also performs actions (the controller portion of the MVC)
 * when a button is clicked. The View class calls the methods in this class to
 * update the GUI.
 * 
 * @author David Ou
 * @author Sudarsana Sandeep
 * @author Tharsan Sivathasan
 * @author Omar Elberougy
 *
 */
public class View implements ActionListener {

	private JFrame frame;
	private int levelNumber;
	private Board board;
	private int xPos, yPos, xPos2, yPos2;
	private JMenuItem moveItem;

	/**
	 * Creates instance of the view class allowing the GUI to be shown
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try { // Catches exceptions that may be thrown
					View window = new View();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor for the class that initalizes the frame and creates a menubar
	 */
	public View() {
		// initialize the beginning and end coordinates to -1 (which is not found on the
		// board) so we know if the value changes
		xPos = -1;
		yPos = -1;
		xPos2 = -1;
		yPos2 = -1;
		levelNumber = 0;// Initialize the level number

		frame = new JFrame("Jump In'");// Set name of frame
		// Set icon of frame to a rabbit image
		ImageIcon logoImage = new ImageIcon(getClass().getResource("Grabbit.png"));
		frame.setIconImage(logoImage.getImage());
		frame.setSize(505, 552);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);

		// Create cart layout so in the future we can implement the unlimited undos and
		// redos
		CardLayout cards = new CardLayout();
		JPanel gamePanel = new JPanel(); // panel that stores the card layout
		gamePanel.setLayout(cards);
		frame.setContentPane(gamePanel);

		startMenu(); // Calls method that creates a card displays that card on the frame

		// Creates menu bar that houses options for rules, quit, and return to main menu
		JMenuBar menuBar = new JMenuBar();
		JMenu optionMenu = new JMenu("Options");
		JMenuItem rules = new JMenuItem("Rules");
		JMenuItem quit = new JMenuItem("Quit");
		JMenuItem returnMain = new JMenuItem("Return to Main Menu");
		frame.setJMenuBar(menuBar);
		menuBar.add(optionMenu);
		optionMenu.add(rules);
		optionMenu.add(quit);
		optionMenu.add(returnMain);
		// creates move button that is not visible until you
		moveItem = new JMenuItem("Move");
		frame.getJMenuBar().add(Box.createHorizontalGlue());
		frame.getJMenuBar().add(moveItem);
		moveItem.addActionListener(this);
		moveItem.setVisible(false);

		rules.addActionListener(this);
		quit.addActionListener(this);
		returnMain.addActionListener(this);
	}

	/**
	 * Method that creates a panel of the start screen and adds it to the list of
	 * panels in card layout which can be cycled through
	 */
	private void startMenu() {
		JPanel startMenu = new JPanel();
		startMenu.setLayout(null);
		frame.getContentPane().add(startMenu);// Adds the start menu to the card layout

		// Shows the logo on the start screen
		ImageIcon logoImage = new ImageIcon(getClass().getResource("Jump In Logo.jpg"));
		JLabel logo = new JLabel(logoImage);
		int logoWidth = 500, logoHeight = 200;
		// set x location in frame to be centered in the middle and y location to be
		// near the top
		logo.setBounds(frame.getWidth() / 2 - logoWidth / 2, frame.getHeight() / 10, logoWidth, logoHeight);
		startMenu.add(logo);

		// Create a button and adds it to the startmenu screen
		JButton newGame = new JButton("New Game");
		newGame.setFont(new Font("Tahoma", Font.PLAIN, 20));
		int buttonWidth = 180, buttonHeight = 40;
		// set x location in frame to be centered in the middle and y location below the
		// logo
		newGame.setBounds(frame.getWidth() / 2 - buttonWidth / 2, frame.getHeight() / 10 + logoHeight + buttonHeight,
				buttonWidth, buttonHeight);
		startMenu.add(newGame);

		newGame.addActionListener(this);
	}

	/**
	 * Method that creates a panel of the level select screen and adds it to the
	 * list of panels in card layout which can be cycled through
	 */
	private void levelSelect() {
		JPanel levelSelect = new JPanel();
		levelSelect.setLayout(null);
		frame.getContentPane().add(levelSelect);

		int buttonWidth = 180, buttonHeight = 40; // button dimensions

		// creates 2 level buttons to be placed side by side
		JButton level1 = new JButton("Level 1");
		level1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		level1.setBounds(frame.getWidth() / 2 - 30 - buttonWidth, frame.getHeight() / 2, buttonWidth, buttonHeight);
		levelSelect.add(level1);
		JButton level2 = new JButton("Level 2");
		level2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		level2.setBounds(frame.getWidth() / 2 + 30, frame.getHeight() / 2, buttonWidth, buttonHeight);
		levelSelect.add(level2);

		// Creates a start button that is placed below the level buttons
		JButton start = new JButton("Start");
		start.setFont(new Font("Tahoma", Font.PLAIN, 20));
		start.setBounds(frame.getWidth() / 2 - buttonWidth / 2, frame.getHeight() / 2 + buttonHeight + buttonHeight,
				buttonWidth, buttonHeight);
		levelSelect.add(start);

		level1.addActionListener(this);
		level2.addActionListener(this);
		start.addActionListener(this);
	}

	/**
	 * This method creates a panel for when the board gui is showing. Uses a
	 * gridbaglayout to add pieces on the board's 2d array
	 */
	public void startLevel() {

		moveItem.setVisible(true); // move item is visible

		JPanel startLevel = new JPanel();
		GridBagConstraints gbc = new GridBagConstraints();
		Slot[][] tempBoard = board.getBoard();
		Dimension square = new Dimension(100, 100); // size of each button in the grid (except fox)

		startLevel.setLayout(new GridBagLayout());
		frame.getContentPane().add(startLevel);

		// Iterates over the board's 2d array
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {

				JButton tempButton;
				ImageIcon imageIcon;
				Image image;
				String fileName = "";
				// get class to determine if its a rabbit fox mushroom hole or slot
				Class<? extends Slot> tempClass = tempBoard[x][y].getClass();

				// set row, column and size of each square
				gbc.gridx = y; // set row number to the row number of the 2d array
				gbc.gridy = x; // set column number to the column number of the 2d array
				gbc.gridheight = 1; // set height of the column to 1
				gbc.gridwidth = 1; // set width of the column to 1

				if (tempClass == Rabbit.class) { // if the object is instance of rabbit
					// get color from the rabbit and pick the correct file based on color
					Color rabbitColour = ((Rabbit) tempBoard[x][y]).getColor();
					if (rabbitColour == Color.WHITE) {
						fileName = "Wrabbit.png"; // change to white rabbit image file
					} else if (rabbitColour == Color.ORANGE) {
						fileName = "Orabbit.png"; // change to orange rabbit image file
					} else if (rabbitColour == Color.GRAY) {
						fileName = "Grabbit.png"; // change to grey rabbit image file
					}
				} else if (tempClass == Fox.class) {// if the object is instance of fox
					Fox tempFox = (Fox) tempBoard[x][y]; // store the fox object temporarily

					if (tempFox.getX() == x && tempFox.getY() == y) { // if the head of the fox is the current position
						if (tempFox.getVertical()) { // if facing up or down
							if (tempFox.getTailX() == x - 1) {// if facing up
								fileName = "Fox Hdown.png"; // change to vertical head down fox file
								gbc.gridy = x - 1;
							} else { // if facing down
								fileName = "Fox Hup.png"; // change to vertical head up fox file
							}
							gbc.gridheight = 2; // overriding height dimension to fox length

						} else { // if facing right or left
							if (tempFox.getTailY() == y - 1) { // if facing left
								gbc.gridx = y - 1;
								fileName = "Fox Hright.png"; // change to horizontal head left fox file

							} else {// if facing right
								fileName = "Fox Hleft.png"; // change to horizontal head right fox file
							}
							// buttonName = "HFox";
							gbc.gridwidth = 2; // overriding width dimension
						}
					} else {// if the tail of the fox is the current position do nothing
						continue; // waits until the the fox head comes
					}
				} else if (tempClass == Mushroom.class) {// if the object is instance of Mushroom
					fileName = "mushroom image.png";

				} else if (tempClass == Hole.class) {// if the object is instance of Hole
					if (((Hole) tempBoard[x][y]).hasGamePiece()) { // if the hole has a gamepiece (rabbit or mushroom)
						if (((Hole) tempBoard[x][y]).hasRabbit()) {// if the hole has a rabbit inside
							if (((Rabbit) ((Hole) tempBoard[x][y]).getGamePiece()).getColor() == Color.WHITE) {
								fileName = "Whole.png";// set picture to a white rabbit in a hole

							} else if (((Rabbit) ((Hole) tempBoard[x][y]).getGamePiece()).getColor() == Color.ORANGE) {
								fileName = "Ohole.png";// set picture to a orange rabbit in a hole

							} else if (((Rabbit) ((Hole) tempBoard[x][y]).getGamePiece()).getColor() == Color.GRAY) {
								fileName = "Ghole.png";// set picture to a Gray rabbit in a hole

							}
						} else { // if the hole has a mushroom inside
							fileName = "mushroom image.png"; // set picture to a mushroom
						}
					} else { // if the hole doesnt have game piece
						fileName = "hole.png"; // set picture to a hole
					}
				} else { // if the object is an instance of a slot
					fileName = "empty slot.png";
				}

				// Initialize the button with the filename and location set when determining
				// which instance of a class it was
				tempButton = new JButton();
				imageIcon = new ImageIcon(getClass().getResource(fileName));
				image = imageIcon.getImage();
				// set size of the image to 100 by 100 (same size as the button)
				image = image.getScaledInstance(gbc.gridwidth * 100, gbc.gridheight * 100, Image.SCALE_SMOOTH);
				imageIcon = new ImageIcon(image);

				tempButton.setIcon(imageIcon); // set picture in icon to image
				tempButton.setSize(square); // set dimensions of button
				tempButton.setFocusPainted(false);
				tempButton.addActionListener(this);
				tempButton.setBorder(BorderFactory.createEmptyBorder());

				startLevel.add(tempButton, gbc);
			}
		}
		// set the grid bag constraints to the last column and row so the board's gui
		// alignment is northwest in the frame
		gbc.gridx = 5;
		gbc.gridy = 5;
		gbc.weightx = 1;
		gbc.weighty = 1;
		JLabel label = new JLabel();
		startLevel.add(label, gbc);

	}

	/**
	 * Method that is implemented from the action listener interface. This method is
	 * called whenever an action from a Jmenu or Jbutton object that added
	 * themselves to the action listener is pressed. It does a different action
	 * based on where the event was from
	 */
	public void actionPerformed(ActionEvent e) {
		String text;
		if (e.getSource().getClass() == JButton.class) { // if the event came from a button object
			JButton tempButton = (JButton) e.getSource();
			text = tempButton.getText();
			switch (text) {
			case "New Game":// if the button is the new game button
				levelSelect();
				((CardLayout) frame.getContentPane().getLayout()).next(frame.getContentPane());
				break;
			case "Level 1":// if the button is the level 1 button
				levelNumber = 1;
				break;
			case "Level 2":// if the button is the level 2 button
				levelNumber = 2;
				break;
			case "Start":// if the button is the start button
				if (levelNumber > 0) { // if the level 1 or 2 buttons were pressed and they set a level number
					board = new Board(levelNumber); // initialize the board object with the level number picked
					startLevel(); // initialize the panel that holds the board gui
					// switch to the startlevel card
					((CardLayout) frame.getContentPane().getLayout()).next(frame.getContentPane());
				}
				break;
			default: // if a button from the board gui is pressed
				int y = ((JButton) e.getSource()).getX() / 100, x = ((JButton) e.getSource()).getY() / 100;
				Slot[][] gameBoard = board.getBoard(); // get the current 2d array layout and save it to gameboard
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
				break;
			}
		} else if (e.getSource().getClass() == JMenuItem.class) { // if the event came from a jmenu object
			JMenuItem tempMenuItem = (JMenuItem) e.getSource();
			text = tempMenuItem.getText(); // get name of the object
			switch (text) {
			case "Rules":// if jmenuitem pressed was called rules
				// display the rules
				JOptionPane.showMessageDialog(frame,
						"-Rabbits can only jump over other game pieces and they are allowed to jump over multiple pieces\n"
								+ "-Only one rabbit per hole\n" + "-Foxes move either vertically or horizontally\n"
								+ "-Game is won once all rabbits are in the hole",
						"Rules", JOptionPane.OK_OPTION, new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
				break;
			case "Quit":// if jmenuitem pressed was called quit
				// ask player if they wasnt to quit
				int optionQuit = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
				if (optionQuit == 0) { // if they selected yes, end the program
					System.exit(0);
				}
				break;
			case "Return to Main Menu":// if jmenuitem pressed was called return to main menu
				// confirm with player if they actually want to go to the main menu
				int optionReturn = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to return to the main menu?", "Return to Main Menu",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
				if (optionReturn == 0) { // if they select yes
					// change the panel to the starting screen
					moveItem.setVisible(false); // hide move button on menu bar
					levelNumber = 0;
					frame.getContentPane().removeAll();
					startMenu();
					((CardLayout) frame.getContentPane().getLayout()).first(frame.getContentPane());
				}
				break;
			case "Move":// if jmenuitem pressed was called move
				if (xPos + yPos != -2 && xPos2 + yPos2 != -2) {// if player selected a beginning and ending position
					if (board.move(xPos, yPos, xPos2, yPos2)) { // try to move the pieces and if successful
						startLevel();
						((CardLayout) frame.getContentPane().getLayout()).next(frame.getContentPane());
					} else { // if move was not successful
						// display dialog box saying it was an invalid move
						JOptionPane.showMessageDialog(frame, "Invalid Move", "Invalid Move", JOptionPane.ERROR_MESSAGE);
					}
					// reset the pressed button coordinates
					xPos = -1;
					yPos = -1;
					xPos2 = -1;
					yPos2 = -1;
					if (board.checkWin()) { // if you win
						// display dialog box saying you win and asking if they wasnt to play again
						int optionWin = JOptionPane.showConfirmDialog(null,
								"Congratulations, you won! Would you like to play again?", "You Win",
								JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
								new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
						if (optionWin == 0) { // if player selects yes
							levelNumber = 0;//
							frame.getContentPane().removeAll();
							startMenu();// initialize the panel that holds the board gui
							// switch to the startlevel card
							((CardLayout) frame.getContentPane().getLayout()).first(frame.getContentPane());
						} else { // if player doesnt want to play again
							System.exit(0); // exit program
						}
					}
				} else {// if player didnt select a beginning and ending position
					// show a dialog box telling user that it was an invalid move
					JOptionPane.showMessageDialog(frame,
							"Invalid Move: Select an object to move and a valid place to move it", "Invalid Move",
							JOptionPane.ERROR_MESSAGE);
				}
				break;
			}

		}
	}
}