//Author: Sudarsana Sandeep (100963087)

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ActionStorageTest {
	
	private ActionStorage storage;
	
	@Before
	public void setUp () {
		storage = new ActionStorage();
	}
	
	@Test
	public void testAddMove () {
		/* addMove method adds the current (x, y) and new (x, y) coordinates of a game piece to two stacks
		 * one for the x and one for the y coordinate */
		storage.addMove(1, 2, 3, 4, 1);
		
		int x = storage.movex.pop(); // if addMove executed properly then one can simply pop the movex stack and check if the right
									 // x coordinate was initially pushed into the movex stack
		assertEquals(3, x); // here is the check to see if the correct x coordinate was pushed into the movex stack 
		
		int x2 = storage.movex.pop(); 
		assertEquals(1, x2);
		
		
		int y = storage.movey.pop(); // popping movey stack for y coordinate
		assertEquals(4, y); // checking if the correct y coordinate wasd pushed into the movey stack
		
		int y2 = storage.movey.pop();
		assertEquals(2, y2);
		
		assertEquals(1, storage.currentMove); /* addMove method increments currentMove by 2, 
											   * so checking if it was incremented properly */
	}

	@Test
	public void testAddUndoMove() {
		storage.movex.push(1);
		storage.movex.push(3);
		storage.movey.push(2);
		storage.movey.push(4);
		storage.currentMove = 1;
		storage.addUndoMove();
		
		int x = storage.redox.pop();
		assertEquals(3, x); //checking if addUndoMove properly pushed into appropriate 
							//stacks by popping those stacks and making sure the values match
		
		int x2 = storage.redox.pop();
		assertEquals(1, x2);
		
		int y = storage.redoy.pop();
		assertEquals(4, y); //checking if addUndoMove properly pushed into appropriate 
							//stacks by popping those stacks and making sure the values match
		
		int y2 = storage.redoy.pop();
		assertEquals(2, y2);
		
		assertEquals(-1, storage.currentMove); //checking if currentMove got decremented properly by addUndoMove
	}

	@Test
	public void testRemoveUndo() {
		storage.redox.push(1);
		storage.redox.push(3);
		storage.redoy.push(2);
		storage.redoy.push(4);
		storage.removeUndo(); // removeUndo should pop redox and redoy each twice
		
		assertTrue(storage.redox.empty()); // if removeUndo works, then redox should be empty
		assertTrue(storage.redoy.empty()); // if removeUndo works, then redoy should be empty
	}

	@Test
	public void testGetX() {
		//getX method should get the value at the specific index of the movex stack
		storage.movex.push(1); // pushed value of 1 into the the 0th index of the movex stack
		storage.movex.push(3); // pushed value of 3 into the 1st index of the movex stack
		assertEquals(1, storage.getX(0)); // getX method should return the value at the 0th index of the movex stack
		assertEquals(3, storage.getX(1));
	}

	@Test
	public void testGetY() {
		//getY method should get the value at the specific index of the movey stack
		storage.movey.push(2); // pushed value of 2 into the the 0th index of the movey stack
		storage.movey.push(4); // pushed value of 4 into the the 1st index of the movey stack
		assertEquals(2, storage.getY(0)); // getY method should return the value at the 0th index of the movey stack
		assertEquals(4, storage.getY(1));
	}

	@Test
	public void testGetundoX() {
		// getundoY method should get the value at the specific index of the redox stack
		storage.redox.push(1); // pushed value of 1 into the 0th index of the redox stack
		storage.redox.push(3); // pushed value of 3 into the 1st index of the redox stack
		assertEquals(1, storage.getundoX(0)); // getundoX method should return the value at the 0th indec of the redox stack
		assertEquals(3, storage.getundoX(1));
	}

	@Test
	public void testGetundoY() {
		// getundoY method should get the value at the specific index of the redoy stack
		storage.redoy.push(2);
		storage.redoy.push(4);
		assertEquals(2, storage.getundoY(0));
		assertEquals(4, storage.getundoY(1));
	}

	@Test
	public void testGetNumMoves() {
		// getNumMoves method returns the number of moves that were made
		assertEquals(-1, storage.getNumMoves()); // when no moves are made, the value is -1
	}

	@Test
	public void testGetMovex() {
		// getMovex method returns the contents of the movex stack
		storage.movex.push(1);
		storage.movex.push(3);
		assertEquals(2, storage.getMovex().size()); // checking the size of the stack that was returned
													// if the size matches the expected size of the returned stack
	}

	@Test
	public void testGetMovey() {
		// getMovey method returns the contents of the movey stack
		storage.movey.push(2);
		storage.movey.push(4);
		assertEquals(2, storage.getMovey().size());
	}

	@Test
	public void testGetRedox() {
		// getRedox method returns the contents of the redox stack
		storage.redox.push(1);
		storage.redox.push(3);
		assertEquals(2, storage.getRedox().size());
	}

	@Test
	public void testGetRedoy() {
		// getRedoy method returns the contents of the redoy stack
		storage.redoy.push(2);
		storage.redoy.push(4);
		assertEquals(2, storage.getRedoy().size());
	}

	@Test
	public void testClearUndos() {
		// clearUndos method clears the redox and redoy stack, which means its clearing the stack with all the undo moves in them
		storage.redox.push(1);
		storage.redox.push(3);
		storage.redoy.push(2);
		storage.redoy.push(4);
		storage.clearUndos(); // pushed values into redox and redoy stack then called clearUndos method 
		assertTrue(storage.redox.empty()); // redox and redoy stack should be empty if clearUndos executed properly
		assertTrue(storage.redoy.empty());
	}
}
