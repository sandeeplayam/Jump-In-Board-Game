// author Danish Butt 101000319

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;


public class BoardTest {

	private Board board;
	
	
	
	@Before
	public void setUp() {
		board= new Board (1);   //Create board object and set to 1st case

	}

	@Test
	public void testCheckWin() {
		
		//All rabbits are not in hole
		assertFalse(board.checkWin());
		
		//Rabbits are in hole
		board.move(4,2,2,2,1);
		board.move(2,2,0,2,1);
		board.move(0,1,0,4,1);
		board.move(0,2,2,2,1);
		assertTrue(board.checkWin());

	}
	
	@Test
	public void testMove() {
		
		//Check when move is valid
		assertTrue(board.move(4,2,2,2,1));
		
		//Check when move is not valid
		assertFalse(board.move(4, 2, 0, 5,1));
		
	}

	@Test
	public void canUndo() {
		
		//Can't undo (current move is set to -1 by default)
		assertFalse(board.canUndo());
		
		//Can Undo 
		board.move(4, 2, 2, 2, 1);     //Move made so value of current move was incremented so no longer -1
		assertTrue(board.canUndo());

	}
	
	@Test
	public void testCanRedo() {
		
		//Stack is empty (no redo possible since no moves added)
		assertFalse(board.canRedo());
		
		
		//Moves made so redo is now possible
		board.move(4, 2, 2, 2, 1);
		board.undo();
		assertTrue(board.canRedo());	
	}
	
	@Test
	public void testUndo() {
		
		board.move(4,2,2,2,1);  //moved rabbit to [2][2]
		board.undo();           //undo: moved rabbit back to [4][2]
		
		//Create new board
		Slot[][] board1=new Slot[5][5];
		board1=board.getBoard();   // get board from board class
		
		//Check that there is a rabbit at [4][2]
		assertTrue(board1[4][2].getClass() == Rabbit.class);   
		
	}
	
	@Test 
	public void testRedo() {
		
		board.move(4, 2, 2, 2, 1);  //moved rabbit to [2][2]
		board.move(2, 2, 0, 2, 1);  //moved rabbit to [0][2]
		board.undo();               //undo: moved rabbit to [2][2]
		board.redo();	            //redo: moved rabbit to [0][2]
		
		//Create new board
		Slot[][] board1=new Slot[5][5];
		board1=board.getBoard();   // get board from board class
				
		//Check that there is a rabbit at [0][2]
		assertTrue(board1[0][2].getClass() == Rabbit.class);  
		
	}
}
