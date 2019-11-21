import java.util.Stack;

/**
 * The ActionStorage class stores the x, y coordinates of game pieces and keeps track of them through the use of 
 * multiple stacks.
 * 
 * @author Omar Elberougy
 * @author David Ou
 * @author Sudarsana Sandeep
 * @author Tharsan Sivathasan
 */
public class ActionStorage {
	
	private Stack<Integer> moveX;
	private Stack<Integer> redoX;
	private Stack<Integer> moveY;
	private Stack<Integer> redoY;
	private int currentMove; 
	
	/**
	 * Constructor of the ActionStorage class which initializes all the stacks being used in the class and initializes 
	 * currentMove to -1 to indicate no moves have been made yet.
	 */
	public ActionStorage() {
		moveX = new Stack<Integer>();
		redoX = new Stack<Integer>();
		moveY = new Stack<Integer>();
		redoY = new Stack<Integer>();
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
		
		moveX.push(x);
		moveY.push(y);
		moveX.push(x2);
		moveY.push(y2);
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
		
		int temp = moveX.pop();
		redoX.push(moveX.pop());
		redoX.push(temp);
		
		temp = moveY.pop();
		redoY.push(moveY.pop());
		redoY.push(temp);
		
		currentMove--;
		currentMove--;
		
	}
	
	/**
	 * removeUndo method removes one undo move
	 */
	public void removeUndo(){
		redoY.pop();
		redoY.pop();
		redoX.pop();
		redoX.pop();
	}
	
	/**
	 * getX method returns an element from the movex stack, that element being a x coordinate for a game piece
	 * @param i index of the stack that one would like to retrieve from
	 * @return the x coordinate of a game piece from stack movex
	 */
	public int getX(int i) {
		return moveX.get(i);
	}
	
	/**
	 * getY method returns an element from the movey stack, that element being a y coordinate for a game piece
	 * @param i index of the stack that one would like to retrieve from
	 * @return the y coordinate of a game piece from stack movey
	 */
	public int getY(int i) {
		return moveY.get(i);
	}
	
	/**
	 * getundoX method returns an element from the redox stack, that element being a x coordinate for a game piece
	 * @param i index of the stack that one would like to retrieve from
	 * @return the x coordinate of a game piece from stack redox
	 */
	public int getundoX(int i) {
		return redoX.get(i);
	}
	
	/**
	 * getundoY method returns an element from the redoy stack, that element being a y coordinate for a game piece
	 * @param i index of the stack that one would like to retrieve from
	 * @return the y coordinate of a game piece from stack redoy
	 */
	public int getundoY(int i) {
		return redoY.get(i);
	}
	
	/**
	 * getNumMoves method gives you the number of moves that have occurred 
	 * @return the value of currentMove
	 */
	public int getNumMoves() {
		
		return currentMove;
	}
	
	/**
	 * setNumMoves method allows to set the number of moves that have occurred
	 */
	public void setNumMoves(int currentMove) {
		this.currentMove = currentMove;
	}
	
	/**
	 * getMovex method gives the contents of the stack movex
	 * @return stack movex
	 */
	public Stack<Integer> getMovex(){
		return moveX;
	}
	
	/**
	 * Allows you to set the value of the moveX stack
	 * @param moveX the list of moves
	 */
	public void setMoveX(int num) {
		this.moveX.push(num);
	}
	
	/**
	 * getMovey method gives the contents of the stack movey
	 * @return stack movey
	 */
	public Stack<Integer> getMovey(){
		return moveY;
	}
	
	/**
	 * Allows you to set the value of the moveY stack
	 * @param moveY the list of moves
	 */
	public void setMoveY(int num) {
		this.moveY.push(num);
	}
	
	/**
	 * getRedox method gives the contents of the stack redox
	 * @return stack redox
	 */
	public Stack<Integer> getRedox(){
		return redoX;
	}
	
	/**
	 * Allows you to set the value of the redoX stack
	 * @param redoX the list of moves
	 */
	public void setRedoX(int num) {
		this.redoX.push(num);
	}
	
	/**
	 * getRedoy method gives the contents of the stack redoy
	 * @return stack redoy
	 */
	public Stack<Integer> getRedoy(){
		return redoY;
	}
	
	/**
	 * Allows you to set the value of the redoY stack
	 * @param redoY the list of moves
	 */
	public void setRedoY(int num) {
		this.redoY.push(num);
	}
	
	/**
	 * clearUndos method clears stacks redox and redoy
	 */
	public void clearUndos() {
		redoY.clear();
		redoX.clear();
	}
	
	public void clearMoves() {
		moveY.clear();
		moveX.clear();
		this.currentMove = -1;
	}
	
}
