package cs509.hobbits.test;

import static org.junit.Assert.*;

import org.junit.Test;

import cs509.hobbits.search.Airplane;

/**
 * @author		Xu Han     xhan@wpi.edu		
 * @version		1.17	
 * @since		2015-04-08	 
 */

public class AirplaneTest {

	
	@Test
	public void testSetModel() {
		Airplane airplane = new Airplane();
		assertEquals("", airplane.getManufacturer());
		assertEquals("", airplane.getModel());

		airplane.setModel("Boing", "747");
		
		assertEquals("Boing", airplane.getManufacturer());
		assertEquals("747", airplane.getModel());
	}

	@Test
	public void testSetSeats() {
		Airplane airplane = new Airplane();
		airplane.setSeats(20, 50);
		
		assertEquals(20, airplane.getSeatNumber(true));
		assertEquals(50, airplane.getSeatNumber(false));
	}
}
