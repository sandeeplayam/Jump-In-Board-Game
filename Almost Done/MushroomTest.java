
//author Danish Butt 101000319

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MushroomTest {

	private Mushroom mushroom;

	@Before
	public void setUp() {

		mushroom = new Mushroom(2, 1);
	}

	@Test
	public void testGetX() {

		// Get's x value
		assertTrue(mushroom.getX() == 2);
	}

	@Test
	public void testGetY() {

		// Get's y value
		assertTrue(mushroom.getY() == 1);
	}

}
