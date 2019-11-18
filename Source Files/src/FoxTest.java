// author: Danish Butt 101000319


import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

public class FoxTest {

		private Fox fox;
		private Slot[][] board;
		
		
	@Before
	public void setUp() {
		
		//Create fox with coordinates
		fox = new Fox(2,2,2,1, Color.ORANGE);
		
		//Set up new board
		board = new Slot[5][5];
		
		for (int x=0;x<5;x++) {
			for (int y=0;y<5;y++) {
				board [x][y]= new Slot (x,y);
			}
		}
		
		board[2][2]= fox;   //Set head of fox to [2][2]
		
	}

	@Test
	public void testGetVertical() {
		
		//fox is not vertical
		assertFalse(fox.getVertical());       
		
		//fox is vertical
		Fox fox2= new Fox (1,1,2,1, Color.GREEN);       
		assertTrue(fox2.getVertical());
	}
	
	@Test
	public void testGetTailX() {
		assertEquals (2, fox.getTailX());       //get's value of TailX           
	}
	
	@Test
	public void testGetTailY() {
		assertEquals (1, fox.getTailY());
	}

	
	@Test 
	public void testMove() {
		
		assertTrue (fox.move(board, 2, 4, 1));  //moved fox to new coordinates

		assertFalse(fox.move(board, 2, 5, 1));  //move not valid (out of bounds)
		
		assertFalse(fox.move(board, 0, 2, 1));  //move not valid (this particular fox can't move up/down only right/left)
	}
	
	
	@Test 
	public void testCanUndo() {
		
		//no move made so can't undo
		assertFalse(fox.canUndo());
				
		//move made so undo can happen
		fox.move(board,2,4,1);       //move fox to [2][4]
		assertTrue (fox.canUndo());
	}
	
	
	@Test
	public void testUndo() {
		
		fox.move(board,2,4,1);    //move fox to [2][4];
		assertTrue(fox.undo(board)); // true since undo happened
		
	}
	
	@Test
	public void testRedo() {
		
		fox.move(board,2,4,1);  //moved fox to [2][4]
		fox.undo(board);        //moved fox back to [2][2]
		fox.redo(board);        //moved fox to [2][4]
		
		assertTrue(board[2][4].getClass() == Fox.class);  //true if fox at [2][4]
	}
	
	@Test
	public void testCanSlide() {
		
		//fox can slide 2 spaces right from [2][2] to [2][4]
		assertEquals (2,(fox.canSlide(board, 2, 2, 3, 0)));
		
		// out of bounds (return -1) 
		assertEquals (-1,(fox.canSlide(board, 5, 5, 3, 0)));
		
	}
}
