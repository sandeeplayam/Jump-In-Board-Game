//author Danish Butt 101000319

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SlotTest {

	private Slot slot;
	
	@Before
	public void setUp() {
		slot = new Slot (3,4);             //Create slot at [3][4]
	}

	@Test
	public void testSlot() {
		assertEquals(3, slot.getX());       //Checks x value of slot
		assertEquals(4, slot.getY());       //Checks y value of slot
	}
	
	@Test
	public void testSetPos() {
		
		slot.setPos(2, 3);                // set's new position of Slot
		assertEquals(2, slot.getX());     // get's x value
		assertEquals(3, slot.getY());     // get's y value
	}
	
	@Test
	public void testGetX() {
		assertEquals(3, slot.getX());     //get's x value
	}
	
	@Test
	public void testGetY() {
		assertEquals(4,slot.getY());      //get's y value
		
	}

}
