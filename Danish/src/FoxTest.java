import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FoxTest {

		private Fox fox;
		private Slot[][] board;
		
		
	@Before
	public void setUp() {
		
		
		fox = new Fox(2,2,2,1);
		
		//Set up new board
		board = new Slot[5][5];
		
		for (int x=0;x<5;x++) {
			for (int y=0;y<5;y++) {
				board [x][y]= new Slot (x,y);
			}
		}
		
		board[2][2]= fox;
		
	}

	@Test
	public void testGetVertical() {
		
		//fox is not vertical
		assertFalse(fox.getVertical());
		
		//fox is vertical
		Fox fox2= new Fox (1,1,2,1);       
		assertTrue(fox2.getVertical());
	}
	
	@Test
	public void testGetTailX() {
		assertEquals (2, fox.getTailX());
	}
	
	@Test
	public void testGetTailY() {
		assertEquals (1, fox.getTailY());
	}

	
	//@Test
	//public void testCanSlide() {
		
		//assertEquals (2, fox.canSlide(board,2,2,3,0));
		
	//}
	
	@Test 
	public void testMove() {
		
		assertTrue (fox.move(board, 2, 4));
		
	}
	
}
