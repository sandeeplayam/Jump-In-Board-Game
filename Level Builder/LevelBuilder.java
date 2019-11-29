import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class LevelBuilder extends JPanel implements ActionListener {
	
	private View view;
	private int xPos, yPos;
	private JButton rabbitButton, foxButton, mushroomButton, removeButton, grayRabbit, orangeRabbit, whiteRabbit,
	northFox, eastFox, southFox, westFox;
	private Slot[][] gameBoard;
	JButton button1;
	
	public LevelBuilder(View v) {
		this.setLayout(new BorderLayout());
		this.view = v;
		button1 = new JButton("temp1");
		
		xPos = -1;
		yPos = -1;
		
		gameBoard = new Slot[5][5];
		if (button1.getText().equals("temp1")) {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					gameBoard[i][j] = new Slot(i, j);
				}
			}
			gameBoard[0][0] = new Hole(0, 0);
			gameBoard[0][4] = new Hole(0, 4);
			gameBoard[2][2] = new Hole(2, 2);
			gameBoard[4][0] = new Hole(4, 0);
			gameBoard[4][4] = new Hole(4, 4);
		}
		
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
		
		setPieceOptionsEnabled(0, false);
		setRabbitOptionsEnabled(false);
		setFoxOptionsEnabled(0, false);
	}

	public void updateBoard() {
		JPanel board = view.boardToPanel(gameBoard, 85, 85, this);

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

		
		this.add(board, BorderLayout.NORTH);
		this.add(slotButtons, BorderLayout.CENTER);
		this.add(movingPieceOptions, BorderLayout.SOUTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Slot tempSlot = new Slot(0, 0);
		System.out.println("hi");
		if (e.getSource().getClass() == JButton.class) { // if the event came from a button object
			String text = ((JButton) e.getSource()).getText();
			System.out.println(text);
			switch (text) {
			case "Add Rabbit":
				setPieceOptionsEnabled(0, false);
				setRabbitOptionsEnabled(true);
				break;
			case "Add Fox":
				setPieceOptionsEnabled(0, false);
				// only allow direction if tail is acceptable
				if (xPos > 0 && gameBoard[xPos + 1][yPos].getClass() == Slot.class) {
					setFoxOptionsEnabled(3, true);
				}
				if (xPos < 4 && gameBoard[xPos - 1][yPos].getClass() == Slot.class) {
					setFoxOptionsEnabled(1, true);
				}
				if (yPos > 0 && gameBoard[xPos][yPos + 1].getClass() == Slot.class) {
					setFoxOptionsEnabled(2, true);
				}
				if (yPos < 4 && gameBoard[xPos][yPos - 1].getClass() == Slot.class) {
					setFoxOptionsEnabled(4, true);
				}
				break;
			case "Add Mushroom":
				tempSlot = new Mushroom(xPos, yPos);
				break;
			case "Remove Piece":
				if (gameBoard[xPos][yPos].getClass() == Fox.class) {
					gameBoard[xPos][yPos] = new Slot(xPos, yPos);
					Fox tempFox = (Fox) gameBoard[xPos][yPos];
					gameBoard[tempFox.getTailX()][tempFox.getTailY()] = new Slot(xPos, yPos);
				} else if (gameBoard[xPos][yPos].getClass() == Hole.class) {
					((Hole) gameBoard[xPos][yPos]).removeGamePiece();
				} else {
					gameBoard[xPos][yPos] = new Slot(xPos, yPos);
				}
				setPieceOptionsEnabled(0, false);
				break;
			case "Gray":
				tempSlot = new Rabbit(xPos, yPos, Color.GRAY);
				break;
			case "Orange":
				tempSlot = new Rabbit(xPos, yPos, Color.ORANGE);
				break;
			case "White":
				tempSlot = new Rabbit(xPos, yPos, Color.WHITE);
				break;
			case "North":
				tempSlot = new Fox(xPos - 1, yPos, xPos, yPos, Color.BLACK);
				gameBoard[xPos - 1][yPos] = tempSlot;
				break;
			case "East":
				tempSlot = new Fox(xPos, yPos + 1, xPos, yPos, Color.BLACK);
				gameBoard[xPos][yPos + 1] = tempSlot;
				break;
			case "South":
				tempSlot = new Fox(xPos + 1, yPos, xPos, yPos, Color.BLACK);
				gameBoard[xPos + 1][yPos] = tempSlot;
				break;
			case "West":
				tempSlot = new Fox(xPos, yPos - 1, xPos, yPos, Color.BLACK);
				gameBoard[xPos][yPos - 1] = tempSlot;
				break;
			default:
				int y = ((JButton) e.getSource()).getX() / 80, x = ((JButton) e.getSource()).getY() / 80;
				// set button coordinates
				xPos = x;
				yPos = y;
				
				System.out.println(x + ", " + y + " " + gameBoard[x][y].getClass());

				if (gameBoard[x][y].getClass() == Rabbit.class
						|| (gameBoard[x][y].getClass() == Hole.class && ((Hole) gameBoard[x][y]).hasGamePiece())
						|| gameBoard[x][y].getClass() == Mushroom.class || gameBoard[x][y].getClass() == Fox.class) {
					setPieceOptionsEnabled(4, true);
				} else {
					if (gameBoard[x][y].getClass() == Hole.class && !((Hole) gameBoard[x][y]).hasGamePiece()) {
						setPieceOptionsEnabled(2, true);
					} else if (gameBoard[x][y].getClass() == Slot.class) {
						setPieceOptionsEnabled(2, true);
						// if fox can be added there in atleast 1 direction
						if (x > 0 && gameBoard[x - 1][y].getClass() == Slot.class
								|| x < 4 && gameBoard[x + 1][y].getClass() == Slot.class
								|| y > 0 && gameBoard[x][y - 1].getClass() == Slot.class
								|| y < 4 && gameBoard[x][y + 1].getClass() == Slot.class) {
							setPieceOptionsEnabled(3, true);
						}
					}
				}
				break;
			}

			if (text.equals("Gray") || text.equals("Orange") || text.equals("White") || text.equals("Add Mushroom")) {
				if (text.equals("Add Mushroom")) {
					setPieceOptionsEnabled(0, false);
				} else {
					setRabbitOptionsEnabled(false);
				}
				if (gameBoard[xPos][yPos].getClass() == Hole.class && !((Hole) gameBoard[xPos][yPos]).hasGamePiece()) {
					((Hole) gameBoard[xPos][yPos]).addGamePiece(tempSlot);
				} else {
					gameBoard[xPos][yPos] = tempSlot;
				}
			} else if (text.equals("North") || text.equals("East") || text.equals("South") || text.equals("West")) {
				setFoxOptionsEnabled(0, false);
				gameBoard[xPos][yPos] = tempSlot;
			}

			view.levelBuilder(this);
		}
		
	}
	
	// 0 is enable all, 1 is all except remove, 2 is rabbit and mushroom, 3 is fox,
		// 4 is remove
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
