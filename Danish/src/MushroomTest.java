import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class MushroomTest {
	
	private Mushroom mushroom;

	@Before
	public void setUp() {
		
		 mushroom = new Mushroom (2,1);
	}

	@Test
	public void testGetX() {
		
		assertTrue (mushroom.getX() == 2);
	}
	
	@Test
	public void testGetY() {
		
		assertTrue (mushroom.getY() == 1);
	}

}
