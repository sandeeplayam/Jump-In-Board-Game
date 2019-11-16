import javax.swing.*;

import java.awt.BorderLayout;
//import java.awt.BorderLayout;
//import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.GridBagLayout;
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
	private JMenuItem moveItem;
	private JMenuItem undo;
	private JMenuItem redo;
	private Controller controller;

	public JFrame getFrame() {
		return this.frame;
	}

	public void displayMove(boolean display) {
		moveItem.setVisible(display);
	}

	public void displayUndo(boolean display) {
		undo.setVisible(display);
	}

	public void displayRedo(boolean display) {
		redo.setVisible(display);
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

//		BorderLayout border = new BorderLayout();
		JPanel gamePanel = new JPanel(); // panel that stores the card layout
		// gamePanel.setLayout(border);
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
		moveItem.addActionListener(controller);
		moveItem.setVisible(false);
		// redo
		redo = new JMenuItem("Redo");
		frame.getJMenuBar().add(Box.createHorizontalGlue());
		frame.getJMenuBar().add(redo);
		redo.addActionListener(controller);
		redo.setVisible(false);
		// undo
		undo = new JMenuItem("Undo");
		frame.getJMenuBar().add(Box.createHorizontalGlue());
		frame.getJMenuBar().add(undo);
		undo.addActionListener(controller);
		undo.setVisible(false);

		rules.addActionListener(controller);
		quit.addActionListener(controller);
		returnMain.addActionListener(controller);

	}

	/**
	 * Method that creates a panel of the start screen and adds it to the list of
	 * panels in card layout which can be cycled through
	 */
	public void startMenu() {
		frame.getContentPane().removeAll();
		JPanel startMenu = new JPanel( new BorderLayout() );
		
		// Shows the logo on the start screen
		ImageIcon logoImage = new ImageIcon(getClass().getResource("Jump In Logo.jpg"));
		JLabel logo = new JLabel(logoImage);
		logo.setPreferredSize( new Dimension(300, 350) );
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
		JPanel levelSelect = new JPanel( new BorderLayout() );
		frame.getContentPane().add(levelSelect);
		
		// Shows the logo on the level select screen
		ImageIcon logoImage = new ImageIcon(getClass().getResource("Jump In Logo.jpg"));
		JLabel logo = new JLabel(logoImage);
		logo.setPreferredSize( new Dimension(300, 350) );
		levelSelect.add(logo, BorderLayout.NORTH);

		// Creates 2 level buttons to be placed side by side
		JButton level1 = new JButton("Level 1");
		level1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		levelSelect.add(level1, BorderLayout.WEST);
		
		JButton level2 = new JButton("Level 2");
		level2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		levelSelect.add(level2, BorderLayout.EAST);

		// Creates a start button that is placed below the level buttons
		JButton start = new JButton("Start");
		start.setFont(new Font("Tahoma", Font.PLAIN, 20));
		levelSelect.add(start, BorderLayout.SOUTH);

		level1.addActionListener(controller);
		level2.addActionListener(controller);
		start.addActionListener(controller);

		frame.validate();
		frame.repaint();

	}

	/**
	 * This method creates a panel for when the board gui is showing. Uses a
	 * gridbaglayout to add pieces on the board's 2d array
	 */
	public void startLevel(Board b) {

		moveItem.setVisible(true); // move item is visible
		redo.setVisible(true);
		undo.setVisible(true);

		JPanel startLevel = new JPanel();
		GridBagConstraints gbc = new GridBagConstraints();
		Slot[][] tempBoard = b.getBoard();
		Dimension square = new Dimension(100, 100); // size of each button in the grid (except fox)

		startLevel.setLayout(new GridBagLayout());
		frame.getContentPane().removeAll();
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
}