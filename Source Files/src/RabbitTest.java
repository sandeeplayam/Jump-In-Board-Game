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
		
		//Create and initialize board
		board = new Slot[5][5];                            
		
		for (int x=0;x<5;x++) {
			for (int y=0;y<5;y++) {
				board [x][y]= new Slot (x,y);
			}
		}
		
		board[1][2]= rabbit;
		board[2][2]= mushroom;
	}

	@Test
	public void testGetColour() {
		
		assertEquals(Color.ORANGE, rabbit.getColor());       //check that the color is orange
	}

	
	@Test 
	public void testMove() {
		
		//Checks if move can be made (YES)
		assertTrue(rabbit.move(board,3,2));
		
		//Checks if move can be made (NO-out of bounds)
		assertFalse(rabbit.move(board,5,5));
		
		//Checks if move can be made (NO-no mushroom to jump over)
		assertFalse(rabbit.move(board, 1, 4));
	}
	

}