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
		
		rabbit = new Rabbit(1,2, Color.ORANGE);
		mushroom = new Mushroom(2,2);
		
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
		
		assertEquals(Color.ORANGE, rabbit.getColor());
	}
	
	@Test
	public void testCanHop() {
		
		//Checks how many spaces it can hop
		assertEquals(2,rabbit.canHop(board,1,2,2,0));
	}
	
	@Test 
	public void testMove() {
		
		//Checks if move can be made
		assertTrue(rabbit.move(board,3,2));
	}
	

}