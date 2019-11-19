import java.util.Stack;

/**
 * The ActionStorage class stores the x, y coordinates of game pieces and keeps track of them through the use of 
 * multiple stacks.
 * 
 * @author Omar Elberougy
 * @author David Ou
 * @author Sudarsana Sandeep
 */
public class ActionStorage {
	
	Stack<Integer> movex;
	Stack<Integer> redox;
	Stack<Integer> movey;
	Stack<Integer> redoy;
	int currentMove; 
	
	/**
	 * Constructor of the ActionStorage class which initializes all the stacks being used in the class and initializes 
	 * currentMove to -1 to indicate no moves have been made yet.
	 */
	public ActionStorage() {
		movex = new Stack<Integer>();
		redox = new Stack<Integer>();
		movey = new Stack<Integer>();
		redoy = new Stack<Integer>();
		currentMove = -1;
	}
	
	/**
	 * addMove method records the current location and new location of a game piece whenever a move is made
	 * @param x current x location of game piece
	 * @param y current y location of game piece 
	 * @param x2 new x location of game piece
	 * @param y2 new y location of game piece
	 * @param normalMove normalMove indicates what type of move it is, 0 for undo, 1 for a regular move, and 2 for a redo
	 */
	public void addMove(int x, int y, int x2, int y2,int normalMove) {
		
		movex.push(x);
		movey.push(y);
		movex.push(x2);
		movey.push(y2);
		currentMove++;
		currentMove++;
		
		if(normalMove==1) {
			this.clearUndos();
		}
		
	}
	
	/**
	 * addUndoMove method stores the current location and previous location of a game piece into the redo stacks
	 */
	public void addUndoMove() {
		
		int temp = movex.pop();
		redox.push(movex.pop());
		redox.push(temp);
		
		temp = movey.pop();
		redoy.push(movey.pop());
		redoy.push(temp);
		
		currentMove--;
		currentMove--;
		
	}
	
	/**
	 * removeUndo method removes one undo move
	 */
	public void removeUndo(){
		redoy.pop();
		redoy.pop();
		redox.pop();
		redox.pop();
	}
	
	/**
	 * getX method returns an element from the movex stack, that element being a x coordinate for a game piece
	 * @param i index of the stack that one would like to retrieve from
	 * @return the x coordinate of a game piece from stack movex
	 */
	public int getX(int i) {
		return movex.get(i);
	}
	
	/**
	 * getY method returns an element from the movey stack, that element being a y coordinate for a game piece
	 * @param i index of the stack that one would like to retrieve from
	 * @return the y coordinate of a game piece from stack movey
	 */
	public int getY(int i) {
		return movey.get(i);
	}
	
	/**
	 * getundoX method returns an element from the redox stack, that element being a x coordinate for a game piece
	 * @param i index of the stack that one would like to retrieve from
	 * @return the x coordinate of a game piece from stack redox
	 */
	public int getundoX(int i) {
		return redox.get(i);
	}
	
	/**
	 * getundoY method returns an element from the redoy stack, that element being a y coordinate for a game piece
	 * @param i index of the stack that one would like to retrieve from
	 * @return the y coordinate of a game piece from stack redoy
	 */
	public int getundoY(int i) {
		return redoy.get(i);
	}
	
	/**
	 * getNumMoves method gives you the number of moves that have occurred 
	 * @return the value of currentMove
	 */
	public int getNumMoves() {
		return currentMove;
	}
	
	/**
	 * getMovex method gives the contents of the stack movex
	 * @return stack movex
	 */
	public Stack<Integer> getMovex(){
		return movex;
	}
	
	/**
	 * getMovey method gives the contents of the stack movey
	 * @return stack movey
	 */
	public Stack<Integer> getMovey(){
		return movey;
	}
	
	/**
	 * getRedox method gives the contents of the stack redox
	 * @return stack redox
	 */
	public Stack<Integer> getRedox(){
		return redox;
	}
	
	/**
	 * getRedoy method gives the contents of the stack redoy
	 * @return stack redoy
	 */
	public Stack<Integer> getRedoy(){
		return redoy;
	}
	
	/**
	 * clearUndos method clears stacks redox and redoy
	 */
	public void clearUndos() {
		redoy.clear();
		redox.clear();
	}
	
}
