//author Danish Butt 101000319

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

public class RabbitTest {

	private Rabbit rabbit;
	private Slot[][] board;
	private Mushroom mushroom;
	
	@Before
	public void setUp() {
		
		
		rabbit = new Rabbit(1,2, Color.ORANGE);             //Initialize rabbit
		mushroom = new Mushroom(2,2);                       //Initialize mushroom
		
		//Create and initialize new board
		board = new Slot[5][5];                            
		
		for (int x=0;x<5;x++) {
			for (int y=0;y<5;y++) {
				board [x][y]= new Slot (x,y);
			}
		}
		
		board[1][2]= rabbit;             //Set Rabbit to [1][2]
		board[2][2]= mushroom;           //Set Mushroom to [2][2]
	}

	@Test
	public void testGetColour() {
		
		assertEquals(Color.ORANGE, rabbit.getColor());       //check that the color is orange
	}

	
	@Test 
	public void testMove() {
		
		//Checks if move can be made (YES)        
		rabbit.move(board,3,2,1);
		assertTrue(board[3][2].getClass() == Rabbit.class);  //true if rabbit at [3][2]
		
		//Checks if move can be made (NO-out of bounds)
		assertFalse(rabbit.move(board,5,5,1));
		
		//Checks if move can be made (NO-no mushroom to jump over)
		assertFalse(rabbit.move(board, 1,4,1));
	}
	
	
	@Test
	public void testUndo() {
		
		rabbit.move(board,3,2,1);    //move rabbit to [3][2];
		assertTrue(rabbit.undo(board)); // true if undo happened
		
	}
	
	@Test
	public void canUndo() {
		
		//no move made so can't do undo
		assertFalse(rabbit.canUndo());
		
		//move made so undo can happen
		rabbit.move(board, 3, 2, 1);  //moved rabbit to [3][2]
		assertTrue (rabbit.canUndo());
	}
	
	@Test 
	public void testRedo() {
		
		rabbit.move(board,3,2,1);  //moved rabbit to [3][2]
		rabbit.undo(board);        //moved rabbit back to [1][2]
		rabbit.redo(board);        //moved rabbit to [3][2]
		
		assertTrue(board[3][2].getClass() == Rabbit.class);  //true if rabbit at [3][2]
	}
	
	@Test 
	public void testCanHop() {
		
		//rabbit can jump 2 spaces down from [1][2] to [3][2]
		assertEquals (2,(rabbit.canHop(board, 1, 2, 2, 0)));
				
		//out of bounds (return -2) 
		assertEquals (-2,(rabbit.canHop(board, 5, 4, 2, 0)));
	}
	
}
