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
		storage.addMove(1, 2, 3, 4, 1);
		
		int x = storage.movex.pop();
		assertEquals(3, x); //checking if stack movex pushed properly
		
		int x2 = storage.movex.pop();
		assertEquals(1, x2);
		
		
		int y = storage.movey.pop();
		assertEquals(4, y); //checking if stack movey pushed properly
		
		int y2 = storage.movey.pop();
		assertEquals(2, y2);
		
		assertEquals(1, storage.currentMove); //checking if currentMove got incremented properly
	}

	@Test
	public void testAddUndoMove() {
		storage.addMove(1, 2, 3, 4, 1);
		storage.addUndoMove();
		
		int x = storage.redox.pop();
		assertEquals(3, x); //checking if stack redox pushed properly
		
		int x2 = storage.redox.pop();
		assertEquals(1, x2);
		
		int y = storage.redoy.pop();
		assertEquals(4, y); //checking if stack redoy pushed properly
		
		int y2 = storage.redoy.pop();
		assertEquals(2, y2);
		
		assertEquals(-1, storage.currentMove); //checking if currentMove got decremented properly
	}

	@Test
	public void testRemoveUndo() {
		storage.addMove(1, 2, 3, 4, 1);
		storage.addUndoMove();
		storage.removeUndo();
		
		assertTrue(storage.redox.empty()); //if removeUndo works, then redox should be empty
		assertTrue(storage.redoy.empty()); //if removeUndo works, then redoy should be empty
	}

	@Test
	public void testGetX() {
		storage.addMove(1, 2, 3, 4, 1);
		assertEquals(1, storage.getX(0));
		assertEquals(3, storage.getX(1));
	}

	@Test
	public void testGetY() {
		storage.addMove(1, 2, 3, 4, 1);
		assertEquals(2, storage.getY(0));
		assertEquals(4, storage.getY(1));
	}

	@Test
	public void testGetundoX() {
		storage.addMove(1, 2, 3, 4, 1);
		storage.addUndoMove();
		assertEquals(1, storage.getundoX(0));
		assertEquals(3, storage.getundoX(1));
	}

	@Test
	public void testGetundoY() {
		storage.addMove(1, 2, 3, 4, 1);
		storage.addUndoMove();
		assertEquals(2, storage.getundoY(0));
		assertEquals(4, storage.getundoY(1));
	}

	@Test
	public void testGetNumMoves() {
		assertEquals(-1, storage.getNumMoves());
	}

	@Test
	public void testGetMovex() {
		storage.addMove(1, 2, 3, 4, 1);
		assertEquals(2, storage.getMovex().size());
	}

	@Test
	public void testGetMovey() {
		storage.addMove(1, 2, 3, 4, 1);
		assertEquals(2, storage.getMovex().size());
	}

	@Test
	public void testGetRedox() {
		storage.addMove(1, 2, 3, 4, 1);
		storage.addUndoMove();
		assertEquals(2, storage.getRedox().size());
	}

	@Test
	public void testGetRedoy() {
		storage.addMove(1, 2, 3, 4, 1);
		storage.addUndoMove();
		assertEquals(2, storage.getRedoy().size());
	}

	@Test
	public void testClearUndos() {
		storage.addMove(1, 2, 3, 4, 1);
		storage.addUndoMove();
		storage.clearUndos();
		assertTrue(storage.redox.empty());
		assertTrue(storage.redoy.empty());
	}
}
