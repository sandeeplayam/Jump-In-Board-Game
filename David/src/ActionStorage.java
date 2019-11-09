import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JPanel;

public class ActionStorage {
	private Stack<Board> toUndoBoard;
	private Stack<Board> toRedoBoard;
	private Stack<JPanel> toUndoView;
	private Stack<JPanel> toRedoView;
	private Board currBoard;
	private JPanel currView;
	
	public ActionStorage() {
		this.toUndoBoard = new Stack<Board>();
		this.toRedoBoard = new Stack<Board>();
		this.toUndoView = new Stack<JPanel>();
		this.toRedoView = new Stack<JPanel>();
		//default values that indicate the time before a move has been taken
		this.currBoard = new Board(0);
		this.currView = new JPanel();
		currView.setEnabled(false);
	}
	
	/**
	 * Records the current state of the game after the player makes a move. Clears the redo pile if there is something to clear.
	 * Pushes previous state of the game into the undo pile if possible
	 * @param boardState most current state of the board
	 * @param viewState most current state of the view
	 */
	public void recordMove(Board boardState, JPanel viewState) {
		//only works after first move has been played
		if(!currBoard.isEmpty() && currView.isEnabled()) {
			toUndoBoard.push(currBoard);
			toUndoView.push(currView);
		}
		
		currBoard = boardState;
		currView = viewState;
		
		if (canRedo()) {
			toRedoBoard.clear();
			toRedoView.clear();
		}
	}
	
	/**
	 * Returns the first undo state Board and JPanel and puts the currently stored state into the redo stacks
	 * @param boardState most current state of the board
	 * @param viewState most current state of the view
	 * @return ArrayList of the first undo state Board and JPanel
	 */
	public ArrayList<Object> recordUndo(Board boardState, JPanel viewState) {
		ArrayList<Object> state = new ArrayList<Object>();
		//pushing current state into redo stack
		toRedoBoard.push(currBoard);
		toRedoView.push(currView);
		//popping undo state into current state
		currBoard = toUndoBoard.pop();
		currView = toUndoView.pop();
		
		state.add(currBoard);
		state.add(currView);
		
		return state;
	}
	
	/**
	 * Returns the first redo state Board and JPanel and puts the currently stored state into the undo stacks
	 * @param boardState most current state of the board
	 * @param viewState most current state of the view
	 * @return ArrayList of the first redo state Board and JPanel
	 */
	public ArrayList<Object> recordRedo(Board boardState, JPanel viewState) {
		ArrayList<Object> state = new ArrayList<Object>();
		//pushing current state into undo stack
		toUndoBoard.push(currBoard);
		toUndoView.push(currView);
		//popping redo state into current state
		currBoard = toRedoBoard.pop();
		currView = toRedoView.pop();
		
		state.add(currBoard);
		state.add(currView);

		return state;
	}

	public boolean canUndo() {
		if(toUndoBoard.isEmpty() && toUndoView.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public boolean canRedo() {
		if(toRedoBoard.isEmpty() && toRedoView.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public Stack<Board> getToUndoBoard() {
		return toUndoBoard;
	}

	public Stack<Board> getToRedoBoard() {
		return toRedoBoard;
	}

	public Stack<JPanel> getToUndoView() {
		return toUndoView;
	}

	public Stack<JPanel> getToRedoView() {
		return toRedoView;
	}
	
	
	
}
