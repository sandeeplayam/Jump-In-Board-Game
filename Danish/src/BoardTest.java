// author Danish Butt 101000319

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {

	public Board board;
	
	@Before
	public void setUp() {
		board= new Board (1);   //Create board 
		
	}

	@Test
	public void testCheckWin() {
		
		//All rabbits are not in hole
		assertFalse(board.checkWin());
		
		//Rabbits are in hole
		board.move(4,2,2,2);
		board.move(2,2,0,2);
		board.move(0,1,0,4);
		board.move(0,2,2,2);
		assertTrue(board.checkWin());

	}
	
	@Test
	public void testMove() {
		
		//Move all the rabbits into the hole to check if the move method works properly
		board.move(4,2,0,2);
		board.move(4,2,2,2);
		board.move(2,2,0,2);
		board.move(0,1,0,4);
		board.move(0,2,2,2);
		assertTrue(board.checkWin()); //Since this evaluates to true this means that the move method works properly
		
		//Check when move is not valid
		assertFalse(board.move(4, 2, 0, 5));
		
	}

}
