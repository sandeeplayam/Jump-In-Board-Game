
/**
 * The Board class of a text based version of JumpIn'. This
 *  class contains the "board" of the game, which is the 5x5 Grid of slots
 *  for objects(hole, mushroom, rabbit, fox) to be placed in.
 * 
 * @author Omar Elberougy
 *
 */
import java.util.*;

public class Board {

	private Slot[][] board;
	private ArrayList<Hole> Holes;
	private int numRabbits;

	/**
	 * Constructor of the board, that initializes the 5x5 2d Array of slots (with
	 * corresponding coordinates) and List of holes.
	 */
	public Board() {

		board = new Slot[5][5];
		Holes = new ArrayList<Hole>();

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				board[i][j] = new Slot(i, j);
			}
		}
	}

	/**
	 * Getter for the 2D array of Slots
	 * 
	 * @return Slot[][] of all slots created in board
	 */
	public Slot[][] getBoard() {
		return board;
	}

	/**
	 * Gets the string representation/view of the board
	 * 
	 * @return A String representing the current state/view of the board
	 */
	public String toStringBoard() {
		String b = "";
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {

				b += board[i][j].toString();
				b += " ";
			}
			b += "\n";
		}
		return b;
	}

	/**
	 * Adds a game piece to the board
	 * 
	 * @param s A slot which can be any type of game piece (Hole, Mushroom, Fox, or
	 *          rabbit)
	 */
	public void addPiece(Slot s) {
		// If Hole, add to list of holes
		if (s instanceof Hole) {
			Holes.add((Hole) s);
			// If Rabbit, Increment number of rabbits
		} else if (s instanceof Rabbit) {
			numRabbits++;
			// If Fox, add Fox's tail to board
		} else if (s instanceof Fox) {
			board[((Fox) s).getTailX()][((Fox) s).getTailY()] = s;
		}
		// Add piece to board
		board[s.getX()][s.getY()] = s;
	}

	/**
	 * Checks if player won the game
	 * 
	 * @return boolean True if All rabbits are in a Hole, False otherwise
	 */
	public boolean checkWin() {

		int occupiedHoles = 0;
		
		if(Holes.size()==0) {
			return false;
		}
		// Check all holes
		for (Hole h : Holes) {
			// If hole has a rabbit, increment number of holes with a rabbit
			if (h.hasRabbit()) {
				occupiedHoles++;
			}
		}
		// If number of occupied holes is the same as number of rabbits on the board,
		// return true
		if (occupiedHoles == numRabbits) {
			return true;
		}
		// Else..
		return false;
	}

	/**
	 * Moves a game piece somewhere on the board
	 * 
	 * @param s         Slot (game piece) to move
	 * @param direction String that specifies the direction the game piece should be
	 *                  moved
	 * @return boolean True if a move was made, False if a move was not possible
	 */
	public boolean move(Slot s, String direction) {
		// if slot is a rabbit
		if (s instanceof Rabbit) {

			// initialize number of available moves in given direction
			int go = 0;

			// Get number of Slots rabbit can jump over then change rabbit's position
			switch (direction) {

			case "up":

				// get number of moves( go = number of obstacles + 1)
				// check the slot above the rabbit (subtract 1 from x)
				go = this.canHop((s.getX() - 1), s.getY(), direction) + 1;
				// If no obstacles do nothing
				if (go == 1) {
					System.out.println("No obstacle to hop over");
					return false;
				// If the checked slot is outside the board
				} else if (go == 0) {
					System.out.println("Can't hop out of board, there's no escape :)");
					return false;
				}

				// Declare rabbit's new position on the Board (current Xposition - number of possible moves)
				// If that position is a hole, add that rabbit to the hole
				if (board[s.getX() - go][s.getY()] instanceof Hole) {

					((Hole) board[s.getX() - go][s.getY()]).addGamePiece(s);

				// else just change that slot to that rabbit
				} else {
					board[s.getX() - go][s.getY()] = s;

				}

				// clear rabbit's old position
				// If it was a hole, remove the rabbit from the hole
				if (board[s.getX()][s.getY()] instanceof Hole) {

					((Hole) board[s.getX()][s.getY()]).removeGamePiece();
				//else change that position to an empty slot
				} else {
					board[s.getX()][s.getY()] = new Slot(s.getX(), s.getY());
				}
				//Set the rabbit's position
				s.setPos(s.getX() - go, s.getY());

				break;
			case "down":
				//get number of moves(number of obstacles +)
				//check slot below rabbit (add 1 to x)
				go = this.canHop((s.getX() + 1), s.getY(), direction) + 1;
				//If no obstacle present
				if (go == 1) {
					System.out.println("No obstacle to hop over");
					return false;
				//If that slot is outside the board
				} else if (go == 0) {
					System.out.println("Can't hop out of board, there's no escape :)");
					return false;
				}

				//Declare rabbit position in Board (current Xpos + number of possible moves)
				// If that position is a whole, add rabbit to hole 
				if (board[s.getX() + go][s.getY()] instanceof Hole) {
					((Hole) board[s.getX() + go][s.getY()]).addGamePiece(s);
				//else change slot to that rabbit	
				} else {
					board[s.getX() + go][s.getY()] = s;

				}

				// clear rabbits old position
				// If rabbit was in a hole, remove rabbit from hole
				if (board[s.getX()][s.getY()] instanceof Hole) {

					((Hole) board[s.getX()][s.getY()]).removeGamePiece();
				//else change slot to an empty slot
				} else {

					board[s.getX()][s.getY()] = new Slot(s.getX(), s.getY());
				}
				//Set rabbit's position
				s.setPos(s.getX() + go, s.getY());

				break;
			case "right":
				// get number of moves( go = number of obstacles + 1)
				// check the slot right to the rabbit (add 1 to y)
				go = this.canHop(s.getX(), (s.getY() + 1), direction) + 1;
				// If no obstacles do nothing
				if (go == 1) {
					System.out.println("No obstacle to hop over");
					return false;
				// If the checked slot is outside the board
				} else if (go == 0) {
					System.out.println("Can't hop out of board, there's no escape :)");
					return false;
				}

				// Declare rabbit's new position on the Board (current Yposition + number of possible moves)
				// If that position is a hole, add that rabbit to the hole
				if (board[s.getX()][s.getY() + go] instanceof Hole) {
					((Hole) board[s.getX()][s.getY() + go]).addGamePiece(s);
				// else just change that slot to that rabbit
				} else {
					board[s.getX()][s.getY() + go] = s;

				}

				// clear rabbit's old position
				// If it was a hole, remove the rabbit from the hole
				if (board[s.getX()][s.getY()] instanceof Hole) {

					((Hole) board[s.getX()][s.getY()]).removeGamePiece();
				//else change that position to an empty slot
				} else {

					board[s.getX()][s.getY()] = new Slot(s.getX(), s.getY());
				}
				//Set the rabbit's position
				s.setPos(s.getX(), s.getY() + go);
				break;
			case "left":
				// get number of moves( go = number of obstacles + 1)
				// check the slot left to the rabbit (subtract 1 from y)
				go = this.canHop(s.getX(), (s.getY() - 1), direction) + 1;
				// If no obstacles do nothing
				if (go == 1) {
					System.out.println("No obstacle to hop over");
					return false;
				// If the checked slot is outside the board
				} else if (go == 0) {
					System.out.println("Can't hop out of board, there's no escape :)");
					return false;
				}

				// Declare rabbit's new position on the Board (current Yposition - number of possible moves)
				// If that position is a hole, add that rabbit to the hole
				if (board[s.getX()][s.getY() - go] instanceof Hole) {
					((Hole) board[s.getX()][s.getY() - go]).addGamePiece(s);
				// else just change that slot to that rabbit
				} else {
					board[s.getX()][s.getY() - go] = s;

				}

				// clear rabbits old position
				// If it was a hole, remove the rabbit from the hole
				if (board[s.getX()][s.getY()] instanceof Hole) {

					((Hole) board[s.getX()][s.getY()]).removeGamePiece();
				//else change that position to an empty slot
				} else {

					board[s.getX()][s.getY()] = new Slot(s.getX(), s.getY());
				}
				//Set the rabbit's position
				s.setPos(s.getX(), s.getY() - go);
				break;
			default:
				System.out.println("That's not a proper direction, use (up, down, left, right)");
			}
		//If a fox is being moved
		} else if (s instanceof Fox) {
			//initialize variables
			boolean go;
			int tempX = -1;
			int tempY = -1;
			
			switch (direction) {

			case "up":
				//If the fox is horizontal
				if (!((Fox) s).getVertical()) {
					System.out.println("Can't go that direction");
					return false;
				// if fox is going backwards (check TailxPos-1)
				} else if (s.getX() > ((Fox) s).getTailX()) {
					//check if tail has space to move
					go = this.canSlide(((Fox) s).getTailX() - 1, ((Fox) s).getTailY(), direction);
					//record Fox head position
					tempX = s.getX();
				//if going forward
				} else {
					go = this.canSlide(s.getX() - 1, s.getY(), direction);
					//record Fox tail position
					tempX = ((Fox) s).getTailX();
				}
				//if fox can move
				if (go) {

					// Declare fox position in Board (Tail and head)
					board[((Fox) s).getTailX() - 1][s.getY()] = s;
					board[s.getX() - 1][s.getY()] = s;
					// clear fox old position
					board[tempX][s.getY()] = new Slot(tempX, s.getY());
					// update fox coordinates
					((Fox) s).setPos(s.getX() - 1, s.getY(), ((Fox) s).getTailX() - 1, ((Fox) s).getTailY());

				} else {

					System.out.println("No space to slide");
					return false;
				}

				break;

			case "down":
				//If the fox is horizontal
				if (!((Fox) s).getVertical()) {
					System.out.println("Can't go that direction");
					return false;
				// fox going backward (check TailxPos+1)
				} else if (s.getX() < ((Fox) s).getTailX()) {
					//record Fox head position
					tempX = s.getX();
					//check if tail has space to move
					go = this.canSlide(((Fox) s).getTailX() + 1, ((Fox) s).getTailY(), direction);
				//if going forward
				} else {
					//record Fox tail position
					tempX = ((Fox) s).getTailX();
					//check if head has space to move
					go = this.canSlide(s.getX() + 1, s.getY(), direction);
				}
				//if fox can move
				if (go) {
					// Declare fox position in Board
					board[((Fox) s).getTailX() + 1][s.getY()] = s;
					board[s.getX() + 1][s.getY()] = s;
					// clear fox old position
					board[tempX][s.getY()] = new Slot(tempX, s.getY());
					// update fox coordinates
					((Fox) s).setPos(s.getX() + 1, s.getY(), ((Fox) s).getTailX() + 1, ((Fox) s).getTailY());

				} else {

					System.out.println("No space to slide");
					return false;
				}

				break;

			case "right":
				//If the fox is vertical
				if (((Fox) s).getVertical()) {
					System.out.println("Can't go that direction");
					return false;
				// fox going backward
				} else if (s.getY() < ((Fox) s).getTailY()) {
					//record Fox head position
					tempY = s.getY();
					//check if tail has space to move
					go = this.canSlide(((Fox) s).getTailX(), ((Fox) s).getTailY() + 1, direction);
				//if going forward
				} else {
					//record Fox tail position
					tempY = ((Fox) s).getTailY();
					//check if head has space to move
					go = this.canSlide(s.getX(), s.getY() + 1, direction);
				}
				//if fox can move
				if (go) {
					// Declare new fox position in Board
					board[((Fox) s).getTailX()][((Fox) s).getTailY() + 1] = s;
					board[s.getX()][s.getY() + 1] = s;
					// clear old fox position
					board[s.getX()][tempY] = new Slot(s.getX(), tempY);
					// update fox coordinates
					((Fox) s).setPos(s.getX(), (s.getY() + 1), ((Fox) s).getTailX(), ((Fox) s).getTailY() + 1);

				} else {

					System.out.println("No space to slide");
					return false;
				}

				break;

			case "left":
				//If the fox is vertical
				if (((Fox) s).getVertical()) {
					System.out.println("Can't go that direction");
					return false;
				// fox going backward
				} else if (s.getY() > ((Fox) s).getTailY()) {
					//record Fox head position
					tempY = s.getY();
					//check if tail has space to move
					go = this.canSlide(((Fox) s).getTailX(), ((Fox) s).getTailY() - 1, direction);

				} else {
					//record Fox tail position
					tempY = ((Fox) s).getTailY();
					//check if head has space to move
					go = this.canSlide(s.getX(), s.getY() - 1, direction);
				}
				//if fox can move
				if (go) {
					// Declare fox position in Board
					board[((Fox) s).getTailX()][((Fox) s).getTailY() - 1] = s;
					board[s.getX()][s.getY() - 1] = s;
					// clear fox old position
					board[s.getX()][tempY] = new Slot(s.getX(), tempY);
					// update fox coordinates
					((Fox) s).setPos(s.getX(), (s.getY() - 1), ((Fox) s).getTailX(), ((Fox) s).getTailY() - 1);
				} else {
					System.out.println("No space to slide");
					return false;
				}
				break;
			default:
				System.out.println("That's not a proper direction, use (up, down, left, right)");
				return false;

			}

		}
		return true;
	}

	/**
	 * Checks if slot can be hopped over by a rabbit in a given direction using recursion
	 * 
	 * @param row Int of the row of Slot to be checked
	 * @param col Int of the coloumn of Slot to be checked
	 * @param direction String of the direction rabbit is trying to hop
	 *                  
	 * @return Int the number of obstacles the rabbit can hop over (0, 1, -1)
	 */
	public int canHop(int row, int col, String direction) {
		//if slot being checked is out of the board, returns special int so can be distinguished from "no obstacle"
		if (row < 0 || col < 0 || row >= board.length || col >= board.length) {
			return -1;
		}
		// if there's an obstacle, check if there are more obstacles ahead
		if (board[row][col] instanceof Mushroom || board[row][col] instanceof Rabbit || board[row][col] instanceof Fox
				|| (board[row][col] instanceof Hole && ((Hole) board[row][col]).hasGamePiece())) {

			switch (direction) {
			//check next slot as per direction
			case "up":
				return canHop(row - 1, col, "up") + 1;
			case "down":
				return canHop(row + 1, col, "down") + 1;
			case "right":
				return canHop(row, col + 1, "right") + 1;
			case "left":
				return canHop(row, col - 1, "left") + 1;
			}
			
		}
		// stop recursion when no more obstacles available
		return 0;
	}

	/**
	 * Checks if slot can be slid to by a fox in a given direction
	 * 
	 * @param row Int of the row of Slot to be checked
	 * @param col Int of the coloumn of Slot to be checked
	 * @param direction String of the direction, fox is trying to slide to
	 *                  
	 * @return boolean True if slot is empty, False if it's occupied
	 */
	public boolean canSlide(int row, int col, String direction) {
		//if slot being checked is out of the board
		if (row < 0 || col < 0 || row >= board.length || col >= board.length) {
			return false;
		//if slot is occupied by (fox or rabbit or mushroom or a hole with a mushroom)	
		} else if (board[row][col] instanceof Fox || board[row][col] instanceof Rabbit
				|| board[row][col] instanceof Mushroom
				|| (board[row][col] instanceof Hole && ((Hole) board[row][col]).hasGamePiece())) {
			return false;
		} else {
			return true;
		}
	}
}
