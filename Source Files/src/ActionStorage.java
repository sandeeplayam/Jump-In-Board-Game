//Authors: Omar Elberougy, David Ou, Sudarsana Sandeep

import java.awt.Point;
import java.util.Stack;

public class ActionStorage {
	
	Stack<Integer> movex;
	Stack<Integer> redox;
	Stack<Integer> movey;
	Stack<Integer> redoy;
	Stack<Point> undo;
	int currentMove; 
	
	public ActionStorage() {
		
		undo = new Stack<Point>();
		
		movex = new Stack<Integer>();
		redox = new Stack<Integer>();
		movey = new Stack<Integer>();
		redoy = new Stack<Integer>();
		currentMove = -1;
	}

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
	
	public void removeUndo(){
		redoy.pop();
		redoy.pop();
		redox.pop();
		redox.pop();
	}
	
	public int getX(int i) {
		return movex.get(i);
	}
	
	public int getY(int i) {
		return movey.get(i);
	}
	
	public int getundoX(int i) {
		return redox.get(i);
	}
	
	public int getundoY(int i) {
		return redoy.get(i);
	}
	
	public int getNumMoves() {
		return currentMove;
	}
	
	public Stack<Integer> getMovex(){
		return movex;
	}
	
	public Stack<Integer> getMovey(){
		return movey;
	}
	
	public Stack<Integer> getRedox(){
		return redox;
	}
	
	public Stack<Integer> getRedoy(){
		return redoy;
	}
	
	public void clearUndos() {
		redoy.clear();
		redox.clear();
	}
	
}
