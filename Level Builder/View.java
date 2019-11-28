import javax.swing.*;

import java.awt.BorderLayout;
//import java.awt.BorderLayout;
//import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
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
	private JMenuItem moveItem, undoItem, redoItem, hintItem;
	private Controller controller;
	JButton rabbitButton, foxButton, mushroomButton, removeButton, grayRabbit, orangeRabbit, whiteRabbit, northFox,
			eastFox, southFox, westFox;

	public JFrame getFrame() {
		return this.frame;
	}

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
		JMenuItem returnMain = new JMenuItem("Return to Main Menu");
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
		hintItem.setVisible(false);
		// creates move button that is not visible until you see the board
		undoItem = new JMenuItem("Undo");
		frame.getJMenuBar().add(undoItem);
		undoItem.addActionListener(controller);
		undoItem.setVisible(false);
		// creates move button that is not visible until you see the board
		redoItem = new JMenuItem("Redo");
		frame.getJMenuBar().add(redoItem);
		redoItem.addActionListener(controller);
		redoItem.setVisible(false);
		// creates move button that is not visible until you see the board
		moveItem = new JMenuItem("Move");
		frame.getJMenuBar().add(moveItem);
		moveItem.addActionListener(controller);
		moveItem.setVisible(false);

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

		frame.getContentPane().removeAll();
		JPanel startMenu = new JPanel(new BorderLayout());

		// Shows the logo on the start screen
		ImageIcon logoImage = new ImageIcon(getClass().getResource("Jump In Logo.jpg"));
		JLabel logo = new JLabel(logoImage);
		logo.setPreferredSize(new Dimension(300, 300));
		startMenu.add(logo, BorderLayout.NORTH);

		// Create a button and adds it to the startmenu screen
		JButton newGame = new JButton("New Game");
		newGame.setFont(new Font("Tahoma", Font.PLAIN, 20));
		startMenu.add(newGame, BorderLayout.SOUTH);

		newGame.addActionListener(controller);

		frame.add(startMenu);// Adds the start menu to the JFrame
		frame.validate();
		frame.repaint();
	}

	/**
	 * Method that creates a panel of the level select screen and adds it to the
	 * list of panels in card layout which can be cycled through
	 */
	public void levelSelect() {

		frame.getContentPane().removeAll();
		JPanel levelSelect = new JPanel(new BorderLayout());
		frame.getContentPane().add(levelSelect);

		// Shows the logo on the level select screen
		ImageIcon logoImage = new ImageIcon(getClass().getResource("Jump In Logo.jpg"));
		JLabel logo = new JLabel(logoImage);
		logo.setPreferredSize(new Dimension(300, 300));
		levelSelect.add(logo, BorderLayout.NORTH);

		JPanel levelButtons = new JPanel(new GridLayout(2, 3));

		// Creates 5 level buttons to be placed side by side
		JButton level1 = new JButton("Level 1");
		level1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		levelButtons.add(level1);
		JButton level2 = new JButton("Level 2");
		level2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		levelButtons.add(level2);
		JButton level3 = new JButton("Level 3");
		level3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		levelButtons.add(level3);
		JButton level4 = new JButton("Level 4");
		level4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		levelButtons.add(level4);
		JButton level5 = new JButton("Level 5");
		level5.setFont(new Font("Tahoma", Font.PLAIN, 20));
		levelButtons.add(level5);
		JButton levelBuilder = new JButton("Create Level");
		levelBuilder.setFont(new Font("Tahoma", Font.PLAIN, 20));
		levelButtons.add(levelBuilder);

		levelSelect.add(levelButtons, BorderLayout.CENTER);

		// Creates a start button that is placed below the level buttons
		JButton start = new JButton("Start");
		start.setFont(new Font("Tahoma", Font.PLAIN, 20));
		levelSelect.add(start, BorderLayout.SOUTH);

		level1.addActionListener(controller);
		level2.addActionListener(controller);
		level3.addActionListener(controller);
		level4.addActionListener(controller);
		level5.addActionListener(controller);
		levelBuilder.addActionListener(controller);
		start.addActionListener(controller);

		frame.validate();
		frame.repaint();

	}

	public void levelBuilder() {

		frame.getContentPane().removeAll();
		JPanel levelBuilder = new JPanel(new BorderLayout());
		JPanel board = new JPanel(new GridBagLayout());
		frame.getContentPane().add(levelBuilder);

		GridBagConstraints gbc = new GridBagConstraints();
//		Slot[][] tempBoard = ;
		Dimension square = new Dimension(80, 80); // size of each button in the grid (except fox)

		// Iterates over the board's 2d array
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {

				JButton tempButton;
				ImageIcon imageIcon;
				Image image;

				String fileName = "";

				// set row, column and size of each square
				gbc.gridx = y; // set row number to the row number of the 2d array
				gbc.gridy = x; // set column number to the column number of the 2d array
				gbc.gridheight = 1; // set height of the column to 1
				gbc.gridwidth = 1; // set width of the column to 1
				if (x == 0 && y == 0 || x == 0 && y == 4 || x == 2 && y == 2 || x == 4 && y == 0 || x == 4 && y == 4) {
					fileName = "hole.png";
				} else {
					fileName = "empty slot.png";
				}

				// Initialize the button with the filename and location set when determining
				// which instance of a class it was
				tempButton = new JButton();
				imageIcon = new ImageIcon(getClass().getResource(fileName));
				image = imageIcon.getImage();
				// set size of the image to 100 by 100 (same size as the button)
				image = image.getScaledInstance(gbc.gridwidth * 80, gbc.gridheight * 80, Image.SCALE_SMOOTH);
				imageIcon = new ImageIcon(image);

				tempButton.setIcon(imageIcon); // set picture in icon to image
				tempButton.setSize(square); // set dimensions of button
				tempButton.setFocusPainted(false);
				tempButton.addActionListener(controller);
				tempButton.setBorder(BorderFactory.createEmptyBorder());

				board.add(tempButton, gbc);
			}
		}

		JPanel slotButtons = new JPanel(new GridLayout(1, 4));
		rabbitButton = new JButton("Add Rabbit");
		rabbitButton.addActionListener(controller);
		slotButtons.add(rabbitButton);
		foxButton = new JButton("Add Fox");
		foxButton.addActionListener(controller);
		slotButtons.add(foxButton);
		mushroomButton = new JButton("Add Mushroom");
		mushroomButton.addActionListener(controller);
		slotButtons.add(mushroomButton);
		removeButton = new JButton("Remove Piece");
		removeButton.addActionListener(controller);
		slotButtons.add(removeButton);

		JPanel movingPieceOptions = new JPanel(new GridLayout(2, 4));
		northFox = new JButton("North");
		northFox.addActionListener(controller);
		movingPieceOptions.add(northFox);
		eastFox = new JButton("East");
		eastFox.addActionListener(controller);
		movingPieceOptions.add(eastFox);
		southFox = new JButton("South");
		southFox.addActionListener(controller);
		movingPieceOptions.add(southFox);
		westFox = new JButton("West");
		westFox.addActionListener(controller);
		movingPieceOptions.add(westFox);
		grayRabbit = new JButton("Gray");
		grayRabbit.addActionListener(controller);
		movingPieceOptions.add(grayRabbit);
		orangeRabbit = new JButton("Orange");
		orangeRabbit.addActionListener(controller);
		movingPieceOptions.add(orangeRabbit);
		whiteRabbit = new JButton("White");
		whiteRabbit.addActionListener(controller);
		movingPieceOptions.add(whiteRabbit);
		setPieceOptionsEnabled(0, false);
		setRabbitOptionsEnabled(false);
		setFoxOptionsEnabled(0, false);

		levelBuilder.add(board, BorderLayout.NORTH);
		levelBuilder.add(slotButtons, BorderLayout.CENTER);
		levelBuilder.add(movingPieceOptions, BorderLayout.SOUTH);

		frame.validate();
		frame.repaint();

		System.out.println(northFox.getHeight());
	}

	/**
	 * This method creates a panel for when the board gui is showing. Uses a
	 * gridbaglayout to add pieces on the board's 2d array
	 */
	public void startLevel(Board b) {

		moveItem.setVisible(true);
		redoItem.setVisible(true);
		undoItem.setVisible(true);
		hintItem.setVisible(true);

		JPanel startLevel = new JPanel();
		GridBagConstraints gbc = new GridBagConstraints();
		Slot[][] tempBoard = b.getBoard();
		Dimension square = new Dimension(100, 100); // size of each button in the grid (except fox)

		frame.getContentPane().removeAll();
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
				tempButton.addActionListener(controller);
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

		frame.validate();
		frame.repaint();

	}

	// 0 is enable all, 1 is all exceppremove, 2 is rabbit and mushroom, 3 is fox, 4 is remove
	public void setPieceOptionsEnabled(int i, boolean b) {
		switch (i) {
		case 0:
			rabbitButton.setEnabled(b);
			mushroomButton.setEnabled(b);
			foxButton.setEnabled(b);
			removeButton.setEnabled(b);
			break;
		case 1:
			rabbitButton.setEnabled(b);
			mushroomButton.setEnabled(b);
			foxButton.setEnabled(b);
			break;
		case 2:
			rabbitButton.setEnabled(b);
			mushroomButton.setEnabled(b);
			break;
		case 3:
			foxButton.setEnabled(b);
			break;
		case 4:
			removeButton.setEnabled(b);
			break;
		}
	}

	public void setRabbitOptionsEnabled(boolean b) {
		grayRabbit.setEnabled(b);
		orangeRabbit.setEnabled(b);
		whiteRabbit.setEnabled(b);
	}

	// 0 is enable all, 1-4 is north, east, south, and west in that order
	public void setFoxOptionsEnabled(int i, boolean b) {
		switch (i) {
		case 0:
			northFox.setEnabled(b);
			eastFox.setEnabled(b);
			southFox.setEnabled(b);
			westFox.setEnabled(b);
			break;
		case 1:
			northFox.setEnabled(b);
			break;
		case 2:
			eastFox.setEnabled(b);
			break;
		case 3:
			southFox.setEnabled(b);
			break;
		case 4:
			westFox.setEnabled(b);
			break;
		}
	}
}