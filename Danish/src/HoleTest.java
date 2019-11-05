//author Danish Butt 101000319

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class HoleTest {

	private Hole hole;
	private Rabbit rabbit;

	
	@Before
	public void setUp() {
		 hole = new Hole (3,2);    
	}

	
	@Test
	public void testGetX() {
		assertTrue (hole.getX() == 3);
	}
	
	@Test
	public void testGetY() {
		assertTrue (hole.getY() == 2);
	}

	@Test
	public void testHasGamePiece() {
		
		//List is empty (no game piece)
		assertFalse (hole.hasGamePiece());
		
		//List has game piece (add rabbit)
		hole.addGamePiece(rabbit);
		assertTrue (hole.hasGamePiece());
	}
	
	@Test
	public void testHasRabbit() {
		
		//No Rabbit in hole (list is empty)
		assertFalse (hole.hasRabbit());
		
		
		// THIS ONE NOT WORKING
		// Rabbit in hole
		hole.addGamePiece(rabbit);
		assertTrue (hole.hasRabbit());
		
	}
	
	@Test
	public void testAddGamePiece() {
		
		//adds game piece
		hole.addGamePiece(rabbit);
		assertTrue(hole.hasGamePiece());
	}
	
	@Test
	public void removeGamePiece() {
		
		//adds a game piece and then removes it
		hole.addGamePiece(rabbit);
		hole.removeGamePiece();
		assertFalse (hole.hasGamePiece());
	}
	
	@Test
	public void getGamePiece() {
		
		hole.addGamePiece(rabbit); //adds a game piece
		assertEquals(rabbit, hole.getGamePiece()); //get's game piece (and make's sure it's a rabbit)
		
	}


	
}
