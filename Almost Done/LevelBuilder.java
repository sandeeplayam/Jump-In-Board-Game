import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LevelBuilder extends JPanel implements ActionListener {

	private View view;
	private int xPos, yPos;
	private JButton rabbitButton, foxButton, mushroomButton, removeButton, grayRabbit, orangeRabbit, whiteRabbit,
			northFox, eastFox, southFox, westFox;
//	private Slot[][] board.getBoard();
	private Board board;
	private JMenuItem resetItem, testSolveItem;
	private JMenu saveCustomBoard;
	private JPanel boardPanel;
	private boolean greyExists, orangeExists, whiteExists;

	public LevelBuilder(View v) {
		this.setLayout(new BorderLayout());
		this.view = v;
		greyExists = false;
		orangeExists = false;
		whiteExists = false;

		rabbitButton = new JButton("Add Rabbit");
		rabbitButton.addActionListener(this);
		foxButton = new JButton("Add Fox");
		foxButton.addActionListener(this);
		mushroomButton = new JButton("Add Mushroom");
		mushroomButton.addActionListener(this);
		removeButton = new JButton("Remove Piece");
		removeButton.addActionListener(this);

		northFox = new JButton("North");
		northFox.addActionListener(this);
		eastFox = new JButton("East");
		eastFox.addActionListener(this);
		southFox = new JButton("South");
		southFox.addActionListener(this);
		westFox = new JButton("West");
		westFox.addActionListener(this);
		grayRabbit = new JButton("Gray");
		grayRabbit.addActionListener(this);
		orangeRabbit = new JButton("Orange");
		orangeRabbit.addActionListener(this);
		whiteRabbit = new JButton("White");
		whiteRabbit.addActionListener(this);

		resetItem = new JMenuItem("Reset board");
		view.getFrame().getJMenuBar().add(resetItem);
		resetItem.addActionListener(this);

		testSolveItem = new JMenuItem("Test Solvable");
		view.getFrame().getJMenuBar().add(testSolveItem);
		testSolveItem.addActionListener(this);

		saveCustomBoard = new JMenu("Save Board");
		view.getFrame().getJMenuBar().add(saveCustomBoard);

		JMenuItem saveBoard1 = new JMenuItem("Board 1");
		saveCustomBoard.add(saveBoard1);
		saveBoard1.addActionListener(this);

		JMenuItem saveBoard2 = new JMenuItem("Board 2");
		saveCustomBoard.add(saveBoard2);
		saveBoard2.addActionListener(this);

		JMenuItem saveBoard3 = new JMenuItem("Board 3");
		saveCustomBoard.add(saveBoard3);
		saveBoard3.addActionListener(this);

		resetBoard();
	}

	/**
	 * Updates the content shown on the screen
	 */
	public void updateBoard() {
		JFrame frame = view.getFrame();
		boardPanel = view.boardToPanel(board.getBoard(), 85, 85, this);
		JPanel slotButtons = new JPanel(new GridLayout(1, 4));
		JPanel movingPieceOptions = new JPanel(new GridLayout(2, 4));

		this.removeAll();

		slotButtons.add(rabbitButton);
		slotButtons.add(foxButton);
		slotButtons.add(mushroomButton);
		slotButtons.add(removeButton);
		movingPieceOptions.add(northFox);
		movingPieceOptions.add(eastFox);
		movingPieceOptions.add(southFox);
		movingPieceOptions.add(westFox);
		movingPieceOptions.add(grayRabbit);
		movingPieceOptions.add(orangeRabbit);
		movingPieceOptions.add(whiteRabbit);

		this.add(boardPanel, BorderLayout.NORTH);
		this.add(slotButtons, BorderLayout.CENTER);
		this.add(movingPieceOptions, BorderLayout.SOUTH);

		frame.getContentPane().removeAll();
		frame.getContentPane().add(this);
		frame.validate();
		frame.repaint();

	}

	/**
	 * Determines which direction buttons are enabled for fox.
	 */
	public void setFoxDirectionButtons() {
		setPieceOptionsEnabled(0, false);
		// only allow direction if tail is acceptable
		if (xPos % 2 == 1) {// east and west only
			if (yPos == 0 || board.getBoard()[xPos][yPos - 1].getClass() == Slot.class) {
				setFoxOptionsEnabled(4, true);// east
			}
			if (yPos == 4 || board.getBoard()[xPos][yPos + 1].getClass() == Slot.class) {
				setFoxOptionsEnabled(2, true);// west
			}
		}
		if (yPos % 2 == 1) { // north and south only
			if (xPos == 0 || board.getBoard()[xPos - 1][yPos].getClass() == Slot.class) {
				setFoxOptionsEnabled(1, true);// north
			}
			if (xPos == 4 || board.getBoard()[xPos + 1][yPos].getClass() == Slot.class) {
				setFoxOptionsEnabled(3, true);// south
			}
		}
	}

	/**
	 * Removes a piece located at xPos and yPos
	 */
	public void removePiece() {
		if (board.getBoard()[xPos][yPos].getClass() == Fox.class) {
			Fox tempFox = (Fox) board.getBoard()[xPos][yPos];
			board.getBoard()[tempFox.getTailX()][tempFox.getTailY()] = new Slot(xPos, yPos);
			board.getBoard()[tempFox.getX()][tempFox.getY()] = new Slot(xPos, yPos);
			board.getFoxes().remove(tempFox);
		} else if (board.getBoard()[xPos][yPos].getClass() == Hole.class) {
			Slot piece = ((Hole) board.getBoard()[xPos][yPos]).getGamePiece();
			if (piece.getClass() == Rabbit.class) {
				Color rabbitColour = ((Rabbit) piece).getColor();
				if (rabbitColour == Color.GRAY) {
					greyExists = false;
				} else if (rabbitColour == Color.ORANGE) {
					orangeExists = false;
				} else {
					whiteExists = false;
				}
				board.getRabbits().remove(piece);
			}
			((Hole) board.getBoard()[xPos][yPos]).removeGamePiece();
		} else if(board.getBoard()[xPos][yPos].getClass() == Rabbit.class){
			Slot piece = board.getBoard()[xPos][yPos];
			if (piece.getClass() == Rabbit.class) {
				Color rabbitColour = ((Rabbit) piece).getColor();
				if (rabbitColour == Color.GRAY) {
					greyExists = false;
				} else if (rabbitColour == Color.ORANGE) {
					orangeExists = false;
				} else {
					whiteExists = false;
				}
				board.getRabbits().remove(piece);
			}
			board.getBoard()[xPos][yPos] = new Slot(xPos, yPos);
		} else if(board.getBoard()[xPos][yPos].getClass() == Mushroom.class){
			
			Slot piece = board.getBoard()[xPos][yPos];

			board.getBoard()[xPos][yPos] = new Slot(xPos, yPos);
			board.getMushrooms().remove(piece);
		}
		setPieceOptionsEnabled(0, false);
		
		
	}

	/**
	 * All the actions that happen when the user clicks on the board
	 * 
	 * @param e
	 */
	public void onBoardClick(ActionEvent e) {
		int y = ((JButton) e.getSource()).getX() / 80, x = ((JButton) e.getSource()).getY() / 80;
		// set button coordinates
		xPos = x;
		yPos = y;

		disableAllButtons();

		if (board.getBoard()[x][y].getClass() == Rabbit.class
				|| (board.getBoard()[x][y].getClass() == Hole.class && ((Hole) board.getBoard()[x][y]).hasGamePiece())
				|| board.getBoard()[x][y].getClass() == Mushroom.class || board.getBoard()[x][y].getClass() == Fox.class) {
			setPieceOptionsEnabled(4, true);
		} else {
			if (xPos >= 0 && yPos >= 0) {
				if (xPos % 2 == 1 || yPos % 2 == 1) {
					setPieceOptionsEnabled(1, true);
				} else {
					setPieceOptionsEnabled(2, true);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Slot tempSlot = new Slot(0, 0);
		if (e.getSource().getClass() == JButton.class) { // if the event came from a button object
			String text = ((JButton) e.getSource()).getText();
			switch (text) {
			case "Add Rabbit":
				setPieceOptionsEnabled(0, false);
				setRabbitOptionsEnabled(true);
				break;
			case "Add Fox": // this button will only show up if xPos is 1 or 3, or yPos is 1 or 3
				setFoxDirectionButtons();
				break;
			case "Add Mushroom":
				tempSlot = new Mushroom(xPos, yPos);
				board.getMushrooms().add(tempSlot);
				break;
			case "Remove Piece":
				removePiece();
				break;
			case "Gray":
				tempSlot = new Rabbit(xPos, yPos, Color.GRAY);
				board.getRabbits().add(tempSlot);
				greyExists = true;
				break;
			case "Orange":
				tempSlot = new Rabbit(xPos, yPos, Color.ORANGE);
				board.getRabbits().add(tempSlot);
				orangeExists = true;
				break;
			case "White":
				tempSlot = new Rabbit(xPos, yPos, Color.WHITE);
				board.getRabbits().add(tempSlot);
				whiteExists = true;
				break;
			case "North":
				if (xPos == 0) {
					xPos += 1;
				}
				tempSlot = new Fox(xPos - 1, yPos, xPos, yPos, Color.BLACK);
				board.getFoxes().add(tempSlot);
//				board.getBoard()[xPos - 1][yPos] = tempSlot;
				break;
			case "East":
				if (yPos == 4) {
					yPos -= 1;
				}
				tempSlot = new Fox(xPos, yPos + 1, xPos, yPos, Color.BLACK);
				board.getFoxes().add(tempSlot);
//				board.getBoard()[xPos][yPos + 1] = tempSlot;
				break;
			case "South":
				if (xPos == 4) {
					xPos -= 1;
				}
				tempSlot = new Fox(xPos + 1, yPos, xPos, yPos, Color.BLACK);
				board.getFoxes().add(tempSlot);
//				board.getBoard()[xPos + 1][yPos] = tempSlot;
				break;
			case "West":
				if (yPos == 0) {
					yPos += 1;
				}
				tempSlot = new Fox(xPos, yPos - 1, xPos, yPos, Color.BLACK);
				board.getFoxes().add(tempSlot);
//				board.getBoard()[xPos][yPos - 1] = tempSlot;
				break;
			default:
				onBoardClick(e);
			}
			board.addPiecesToBoard();

			if (text.equals("Gray") || text.equals("Orange") || text.equals("White") || text.equals("Add Mushroom")) {
				if (text.equals("Add Mushroom")) {
					setPieceOptionsEnabled(0, false);
				} else {
					setRabbitOptionsEnabled(false);
				}
				if (board.getBoard()[xPos][yPos].getClass() == Hole.class && !((Hole) board.getBoard()[xPos][yPos]).hasGamePiece()) {
					((Hole) board.getBoard()[xPos][yPos]).addGamePiece(tempSlot);
				} else {
					board.getBoard()[xPos][yPos] = tempSlot;
				}
			} else if (text.equals("North") || text.equals("East") || text.equals("South") || text.equals("West")) {
				setFoxOptionsEnabled(0, false);
				board.getBoard()[xPos][yPos] = tempSlot;
			}

			view.levelBuilder(this);
		} else if (e.getSource().getClass() == JMenuItem.class) {
			String text = ((JMenuItem) e.getSource()).getText();
			switch (text) {
			case "Board 1":
			case "Board 2":
			case "Board 3":
				saveCustomBoard(text);
				break;
			case "Test Solvable":
				testSolvable();
				break;
			case "Reset board":
				resetBoard();
				break;
			case "Start":
//				Board board = new Board(getPieces());
				((Controller) view.getController()).setBoard(board);
				((Controller) view.getController()).setScreen(2);
				view.startLevel(board); // initialize the panel that holds the board gui
			case "Return to Main Menu":
				removeInteractables();
			}
		}
	}

	/**
	 * Saves the custom board
	 * 
	 * @param text Used to determine which save slot the custom board will go to.
	 */
	public void saveCustomBoard(String text) {
		try {
			board.reset();
			int optionSave = JOptionPane.showConfirmDialog(null,
					"Are you sure you want save the board?\nAny previously saved board setups in this save slot will be overwritten.\n",
					"Save Board", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
					new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
			if (optionSave == 0) {
				int saveSlot = Integer.parseInt(text.substring(text.length() - 1));
				Saver boardSaver = new Saver(1);
				boardSaver.saveToFile(board, saveSlot);
				JOptionPane.showMessageDialog(view.getFrame(), "Board saved!\nPress Start to begin playing.",
						"Board Saved", JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
			}
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(view.getFrame(), "Board setup was unable to be saved due to: " + exception,
					"Board Save Error", JOptionPane.INFORMATION_MESSAGE,
					new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
		}
	}

	/**
	 * Checks if the board is solvable or not. If it is, then the option to start
	 * the game and save the custom board will be available.
	 */
	public void testSolvable() {
		int maxSteps;
		try {
			maxSteps = Integer.parseInt(JOptionPane.showInputDialog(view.getFrame(), "Please insert the maximum amount of moves", "Max Moves", JOptionPane.INFORMATION_MESSAGE));
			Solver solver = new Solver(board, maxSteps);
			disableAllButtons();
			if (!solver.findSolution().isEmpty() && (greyExists || orangeExists || whiteExists)) {
				testSolveItem.setText("Start");
				saveCustomBoard.setVisible(true);
				for (Component boardButton : boardPanel.getComponents()) { // disable buttons to prevent further
																			// edits
					((JButton) boardButton).removeActionListener(this);
				}
				JOptionPane.showMessageDialog(view.getFrame(),
						"The game is solvable!\nSave the board for future games by pressing Save Board and selecting a save slot",
						"Solvable Game", JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
			} else {
				JOptionPane.showMessageDialog(view.getFrame(),
						"This game is not solvable, refer to the rules and add pieces so that it is and press hint again.",
						"Unsolvable Game Error", JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
			}
			board.reset();
		} catch(Exception e){
			JOptionPane.showMessageDialog(view.getFrame(), "Invalid input. Input numbers only.", "Invalid input", JOptionPane.WARNING_MESSAGE);
			
		}
	}

	/**
	 * Removes all of the interactable JMenuItems for the level builder and this
	 * object as an actionListener for the Return to Main Menu item
	 */
	public void removeInteractables() {
		JMenuBar menuBar = view.getFrame().getJMenuBar();
		for (Component piece : menuBar.getComponents()) { // find Return to Main Menu item
			if (piece.getClass() == JMenu.class) {
				for (Component menuItem : ((JMenu) piece).getComponents()) {
					JMenuItem tempMenuItem = (JMenuItem) menuItem;
					if (tempMenuItem.getText().equals("Return to Main Menu")) {
						tempMenuItem.removeActionListener(this);
					}
				}
			}
		}
		menuBar.remove(resetItem);
		menuBar.remove(testSolveItem);
		menuBar.remove(saveCustomBoard);
	}

	/**
	 * Returns the list of pieces currently on the board
	 * 
	 * @return The list of pieces currently on the board
	 */
	public ArrayList<Slot> getPieces() {
		ArrayList<Slot> pieces = new ArrayList<Slot>();
		for (int i = 0; i < board.getBoard().length; i++) {
			for (int j = 0; j < board.getBoard().length; j++) {
				if (board.getBoard()[i][j].getClass() == Rabbit.class || board.getBoard()[i][j].getClass() == Fox.class
						|| board.getBoard()[i][j].getClass() == Mushroom.class || board.getBoard()[i][j].getClass() == Hole.class) {
					pieces.add(board.getBoard()[i][j]);
					if(board.getBoard()[i][j].getClass() == Hole.class) {
						Hole hole = (Hole) board.getBoard()[i][j];
						if(hole.hasGamePiece()) {
							pieces.add(hole.getGamePiece());
						}
					}
				}
			}
		}
		return pieces;
	}
	
//	public Slot[][] getPieces(){
//		return this.board.getBoard();
//	}

	/**
	 * Resets the interactable board
	 */
	public void resetBoard() {
		xPos = -1;
		yPos = -1;
//		board.getBoard() = new Slot[5][5];
		board =  new Board();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				board.getBoard()[i][j] = new Slot(i, j);
			}
		}
//		board.getBoard()[0][0] = new Hole(0, 0);
//		board.getBoard()[0][4] = new Hole(0, 4);
//		board.getBoard()[2][2] = new Hole(2, 2);
//		board.getBoard()[4][0] = new Hole(4, 0);
//		board.getBoard()[4][4] = new Hole(4, 4);
		ArrayList<Slot> holeList = board.getHoles();
		holeList.add(new Hole(0, 0));
		holeList.add(new Hole(0, 4));
		holeList.add(new Hole(2, 2));
		holeList.add(new Hole(4, 0));
		holeList.add(new Hole(4, 4));
		board.addPiecesToBoard();
		
		greyExists = false;
		orangeExists = false;
		whiteExists = false;

		testSolveItem.setText("Test Solvable");
		saveCustomBoard.setVisible(false);
		disableAllButtons();
		updateBoard();
	}

	/**
	 * 0 is enable all, 1 is all except remove, 2 is rabbit and mushroom, 3 is fox,
	 * 4 is remove
	 * 
	 * @param i
	 * @param b
	 */
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

	/**
	 * Enables or disables the rabbit buttons
	 * 
	 * @param b
	 */
	public void setRabbitOptionsEnabled(boolean b) {
		if (b) {
			if (!greyExists) {
				grayRabbit.setEnabled(b);
			}
			if (!orangeExists) {
				orangeRabbit.setEnabled(b);
			}
			if (!whiteExists) {
				whiteRabbit.setEnabled(b);
			}
		} else {
			grayRabbit.setEnabled(b);
			orangeRabbit.setEnabled(b);
			whiteRabbit.setEnabled(b);
		}
	}

	/**
	 * 0 is enable all, 1-4 is north, east, south, and west in that order
	 * 
	 * @param i
	 * @param b
	 */
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

	/**
	 * Disables all buttons
	 */
	public void disableAllButtons() {
		setPieceOptionsEnabled(0, false);
		setRabbitOptionsEnabled(false);
		setFoxOptionsEnabled(0, false);
	}
}
