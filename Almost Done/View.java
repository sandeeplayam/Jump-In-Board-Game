import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.GridBagConstraints;

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
public class View {

	private JFrame frame;
	private JMenuItem moveItem, undoItem, redoItem, hintItem, returnMain;
	private Controller controller;
	private JMenu saveProgress;

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
	 * Constructor for the class that initializes the frame and creates a menubar
	 */
	public View() {
		controller = new Controller(this);
		// initialize the beginning and end coordinates to -1 (which is not found on the
		// board) so we know if the value changes

		frame = new JFrame("Jump In'");// Set name of frame
		// Set icon of frame to a rabbit image
		ImageIcon logoImage = new ImageIcon(getClass().getResource("Wrabbit.png"));
		frame.setIconImage(logoImage.getImage());
		frame.setSize(515, 560);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);

		JPanel gamePanel = new JPanel(); // panel that stores the card layout
		frame.setContentPane(gamePanel);

		// Creates menu bar that houses options for rules, quit, and return to main menu
		JMenuBar menuBar = new JMenuBar();
		JMenu optionMenu = new JMenu("Options");
		JMenuItem rules = new JMenuItem("Rules");
		JMenuItem quit = new JMenuItem("Quit");
		returnMain = new JMenuItem("Return to Main Menu");
		frame.setJMenuBar(menuBar);
		menuBar.add(optionMenu);
		optionMenu.add(rules);
		optionMenu.add(quit);
		optionMenu.add(returnMain);

		menuBar.add(Box.createHorizontalGlue());
		// creates move button that is not visible until you see the board
		hintItem = new JMenuItem("Hint");
		frame.getJMenuBar().add(hintItem);
		hintItem.addActionListener(controller);
		// creates move button that is not visible until you see the board
		undoItem = new JMenuItem("Undo");
		frame.getJMenuBar().add(undoItem);
		undoItem.addActionListener(controller);
		// creates move button that is not visible until you see the board
		redoItem = new JMenuItem("Redo");
		frame.getJMenuBar().add(redoItem);
		redoItem.addActionListener(controller);
		// creates move button that is not visible until you see the board
		moveItem = new JMenuItem("Move");
		frame.getJMenuBar().add(moveItem);
		moveItem.addActionListener(controller);

		// setting up save game menu
		saveProgress = new JMenu("Save Game");
		menuBar.add(saveProgress);

		JMenuItem save1Item = new JMenuItem("Save 1");
		saveProgress.add(save1Item);
		save1Item.addActionListener(controller);

		JMenuItem save2Item = new JMenuItem("Save 2");
		saveProgress.add(save2Item);
		save2Item.addActionListener(controller);

		JMenuItem save3Item = new JMenuItem("Save 3");
		saveProgress.add(save3Item);
		save3Item.addActionListener(controller);

		rules.addActionListener(controller);
		quit.addActionListener(controller);
		returnMain.addActionListener(controller);

		startMenu(); // Calls method that creates a card displays that card on the frame

	}

	/**
	 * Method that creates a panel of the start screen and adds it to the list of
	 * panels in card layout which can be cycled through
	 */
	public void startMenu() {

		moveItem.setVisible(false);
		redoItem.setVisible(false);
		undoItem.setVisible(false);
		hintItem.setVisible(false);
		saveProgress.setVisible(false);
		returnMain.setVisible(false);

		frame.getContentPane().removeAll();
		JPanel startMenu = new JPanel(new BorderLayout());

		JPanel startSubMenu = new JPanel(new BorderLayout());
		startMenu.add(startSubMenu, BorderLayout.SOUTH);

		// Shows the logo on the start screen
		ImageIcon logoImage = new ImageIcon(getClass().getResource("Jump In Logo.jpg"));
		JLabel logo = new JLabel(logoImage);
		logo.setPreferredSize(new Dimension(300, 300));
		startMenu.add(logo, BorderLayout.NORTH);

		// Create a button to start a new game and adds it to the startmenu screen
		JButton newGame = new JButton("New Game");
		newGame.setFont(new Font("Tahoma", Font.PLAIN, 20));
		startSubMenu.add(newGame, BorderLayout.NORTH);
		newGame.addActionListener(controller);

		// Create a button to continue game progress and adds it to the startmenu screen
		JButton loadGame = new JButton("Load Game");
		loadGame.setFont(new Font("Tahoma", Font.PLAIN, 20));
		startSubMenu.add(loadGame, BorderLayout.CENTER);
		loadGame.addActionListener(controller);

		// determines if the load button is disabled or not
		// disabled if the save file does not exists
		File file = new File("saveFile.xml");
		if (file.exists()) {
			loadGame.setEnabled(true);
		} else {
			loadGame.setEnabled(false);
		}

		// Create a a button to build a level and adds it ot the startmenu screen
		JButton buildLevel = new JButton("Build Level");
		buildLevel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		startSubMenu.add(buildLevel, BorderLayout.SOUTH);
		buildLevel.addActionListener(controller);

		frame.add(startMenu);// Adds the start menu to the JFrame
		frame.validate();
		frame.repaint();
	}

	/**
	 * Method that creates a panel of the level select screen and adds it to the
	 * list of panels in card layout which can be cycled through
	 */
	public void levelSelect() {
		returnMain.setVisible(true);

		frame.getContentPane().removeAll();
		JPanel levelSelect = new JPanel(new BorderLayout());
		frame.getContentPane().add(levelSelect);

		// Shows the logo on the level select screen
		ImageIcon logoImage = new ImageIcon(getClass().getResource("Jump In Logo.jpg"));
		JLabel logo = new JLabel(logoImage);
		logo.setPreferredSize(new Dimension(300, 300));
		levelSelect.add(logo, BorderLayout.NORTH);

		JPanel levelSelectSub = new JPanel(new BorderLayout());
		JPanel premadeLevels = new JPanel(new GridLayout(1, 5));
		JPanel customLevels = new JPanel(new GridLayout(1, 3));

		// Creates 5 level buttons to be placed side by side
		JButton level1 = new JButton("Level 1");
		level1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		premadeLevels.add(level1);
		JButton level2 = new JButton("Level 2");
		level2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		premadeLevels.add(level2);
		JButton level3 = new JButton("Level 3");
		level3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		premadeLevels.add(level3);
		JButton level4 = new JButton("Level 4");
		level4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		premadeLevels.add(level4);
		// Creates 3 buttons for the three custom levels the user has made
		JButton customLevel1 = new JButton("Custom 1");
		customLevel1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		customLevels.add(customLevel1);
		JButton customLevel2 = new JButton("Custom 2");
		customLevel2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		customLevels.add(customLevel2);
		JButton customLevel3 = new JButton("Custom 3");
		customLevel3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		customLevels.add(customLevel3);

		// Checking if custom levels exists;
		File file = new File("customBoards.xml");
		if (!file.exists()) {
			customLevel1.setEnabled(false);
			customLevel2.setEnabled(false);
			customLevel3.setEnabled(false);
		} else {
			Loader gameLoader = new Loader(1);
			if (!gameLoader.saveExists(1)) {
				customLevel1.setEnabled(false);
			}
			if (!gameLoader.saveExists(2)) {
				customLevel2.setEnabled(false);
			}
			if (!gameLoader.saveExists(3)) {
				customLevel3.setEnabled(false);
			}
		}

		levelSelectSub.add(premadeLevels, BorderLayout.NORTH);
		levelSelectSub.add(customLevels, BorderLayout.SOUTH);
		levelSelect.add(levelSelectSub, BorderLayout.CENTER);

		// Creates a start button that is placed below the level buttons
		JButton start = new JButton("Start");
		start.setFont(new Font("Tahoma", Font.PLAIN, 20));
		levelSelect.add(start, BorderLayout.SOUTH);

		level1.addActionListener(controller);
		level2.addActionListener(controller);
		level3.addActionListener(controller);
		level4.addActionListener(controller);
		customLevel1.addActionListener(controller);
		customLevel2.addActionListener(controller);
		customLevel3.addActionListener(controller);
		start.addActionListener(controller);

		frame.validate();
		frame.repaint();

	}

	public void levelBuilder(LevelBuilder levelBuilder) {
		returnMain.setVisible(true);

		frame.getContentPane().removeAll();
		levelBuilder.updateBoard();
		frame.getContentPane().add(levelBuilder);
		returnMain.addActionListener(levelBuilder);

		frame.validate();
		frame.repaint();
	}

	/**
	 * This method creates a panel for when the board gui is showing. Uses a
	 * gridbaglayout to add pieces on the board's 2d array
	 */
	public void startLevel(Board b) {
		returnMain.setVisible(true);
		moveItem.setVisible(true);
		redoItem.setVisible(true);
		undoItem.setVisible(true);
		hintItem.setVisible(true);
		saveProgress.setVisible(true);

		frame.getContentPane().removeAll();
		JPanel startLevel = boardToPanel(b.getBoard(), 100, 100, controller);
		frame.getContentPane().add(startLevel);

		frame.validate();
		frame.repaint();
	}

	public void loadGameScreen() {
		returnMain.setVisible(true);

		frame.getContentPane().removeAll();
		JPanel loadGameScreen = new JPanel(new BorderLayout());
		frame.getContentPane().add(loadGameScreen);

		ImageIcon logoImage = new ImageIcon(getClass().getResource("Jump In Logo.jpg"));
		JLabel logo = new JLabel(logoImage);
		logo.setPreferredSize(new Dimension(300, 300));
		loadGameScreen.add(logo, BorderLayout.NORTH);

		JButton save1 = new JButton("Save 1");
		save1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		loadGameScreen.add(save1, BorderLayout.WEST);
		save1.addActionListener(controller);

		JButton save2 = new JButton("Save 2");
		save2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		loadGameScreen.add(save2, BorderLayout.CENTER);
		save2.addActionListener(controller);

		JButton save3 = new JButton("Save 3");
		save3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		loadGameScreen.add(save3, BorderLayout.EAST);
		save3.addActionListener(controller);

		JButton start = new JButton("Start");
		start.setFont(new Font("Tahoma", Font.PLAIN, 20));
		loadGameScreen.add(start, BorderLayout.SOUTH);
		start.addActionListener(controller);

		Loader gameLoader = new Loader(0);
		if (!gameLoader.saveExists(1)) {
			save1.setEnabled(false);
		}
		if (!gameLoader.saveExists(2)) {
			save2.setEnabled(false);
		}
		if (!gameLoader.saveExists(3)) {
			save3.setEnabled(false);
		}

		frame.validate();
		frame.repaint();
	}

	public JPanel boardToPanel(Slot[][] gameBoard, int squareHeight, int squareWidth, ActionListener c) {
		JPanel board = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		Dimension square = new Dimension(squareHeight, squareHeight); // size of each button in the grid (except fox)

		// Iterates over the board's 2d array
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {

				JButton tempButton;
				ImageIcon imageIcon;
				Image image;

				String fileName = "";
				// get class to determine if its a rabbit fox mushroom hole or slot
				Class<? extends Slot> tempClass = gameBoard[x][y].getClass();

				// set row, column and size of each square
				gbc.gridx = y; // set row number to the row number of the 2d array
				gbc.gridy = x; // set column number to the column number of the 2d array
				gbc.gridheight = 1; // set height of the column to 1
				gbc.gridwidth = 1; // set width of the column to 1

				if (tempClass == Rabbit.class) { // if the object is instance of rabbit
					// get color from the rabbit and pick the correct file based on color
					Color rabbitColour = ((Rabbit) gameBoard[x][y]).getColor();
					if (rabbitColour == Color.WHITE) {
						fileName = "Wrabbit.png"; // change to white rabbit image file
					} else if (rabbitColour == Color.ORANGE) {
						fileName = "Orabbit.png"; // change to orange rabbit image file
					} else if (rabbitColour == Color.GRAY) {
						fileName = "Grabbit.png"; // change to grey rabbit image file
					}
				} else if (tempClass == Fox.class) {// if the object is instance of fox
					Fox tempFox = (Fox) gameBoard[x][y]; // store the fox object temporarily

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
					if (((Hole) gameBoard[x][y]).hasGamePiece()) { // if the hole has a gamepiece (rabbit or mushroom)
						if (((Hole) gameBoard[x][y]).hasRabbit()) {// if the hole has a rabbit inside
							if (((Rabbit) ((Hole) gameBoard[x][y]).getGamePiece()).getColor() == Color.WHITE) {
								fileName = "Whole.png";// set picture to a white rabbit in a hole

							} else if (((Rabbit) ((Hole) gameBoard[x][y]).getGamePiece()).getColor() == Color.ORANGE) {
								fileName = "Ohole.png";// set picture to a orange rabbit in a hole

							} else if (((Rabbit) ((Hole) gameBoard[x][y]).getGamePiece()).getColor() == Color.GRAY) {
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
				image = image.getScaledInstance(gbc.gridwidth * squareHeight, gbc.gridheight * squareHeight,
						Image.SCALE_SMOOTH);
				imageIcon = new ImageIcon(image);

				tempButton.setIcon(imageIcon); // set picture in icon to image
				tempButton.setSize(square); // set dimensions of button
				tempButton.setFocusPainted(false);
				tempButton.addActionListener(c);
				tempButton.setBorder(BorderFactory.createEmptyBorder());

				board.add(tempButton, gbc);
			}
		}
		return board;
	}

	public JFrame getFrame() {
		return this.frame;
	}

	public ActionListener getController() {
		return controller;
	}

}