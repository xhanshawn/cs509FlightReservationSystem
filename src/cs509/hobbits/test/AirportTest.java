package cs509.hobbits.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import cs509.hobbits.search.Airport;
import cs509.hobbits.search.DataRetriever;

/**
 * @author		Xu Han     xhan@wpi.edu		
 * @version		1.17	
 * @since		2015-04-08	 
 */

public class AirportTest {

	@Test
	public void testSetCode() {
		
		Airport airport = new Airport();
		airport.setCodeAndName("BOS", "Boston logan airport");
		assertEquals("BOS", airport.getCode());
		assertEquals("Boston logan airport", airport.getAirportName());
	}

	@Test
	public void testSetLocation() {
		
		Airport airport = new Airport();
		airport.setLocation(40.32f, -75.30f);
		assertEquals(40.32f, airport.getLatitude(), 0.00001f);
		assertEquals(-75.30f, airport.getLongitude(), 0.00001f);
	}

	@Test
	public void testSetTimeZone() {
		
		//JFK
		Airport airport = new Airport();
		airport.setLocation(40.641518f, -73.77816f);
		airport.setTimeZone();

		
		assertEquals("EDT", airport.getTimeZone());
		assertEquals((-14400l), airport.getOffset());
		assertTrue(airport.dstIsUsed());
		
		//Memphis
		airport.setLocation(35.042343f, -89.97922f);
		airport.setTimeZone();
		
		assertEquals("CDT", airport.getTimeZone());
		assertEquals((-18000l), airport.getOffset());
		assertTrue(airport.dstIsUsed());

		//Denver
		airport.setLocation(39.866373f, -104.67377f);
		airport.setTimeZone();
		assertEquals("MDT", airport.getTimeZone());
		assertEquals((-21600l), airport.getOffset());
		assertTrue(airport.dstIsUsed());
		
		//LA
		airport.setLocation(33.94443f, -118.408356f);
		airport.setTimeZone();
		
		assertEquals("PDT", airport.getTimeZone());
		assertEquals((-25200l), airport.getOffset());
		assertTrue(airport.dstIsUsed());
		
		
		//Alaskan Time Zone
		airport.setLocation(61.176033f, -149.99008f);
		airport.setTimeZone();
		
		assertEquals("AKDT", airport.getTimeZone());
		assertEquals((-28800l), airport.getOffset());
		assertTrue(airport.dstIsUsed());
		
		//Hawaiian Time Zone
		airport.setLocation(21.324808f, -157.92519f);
		airport.setTimeZone();
		
		assertEquals("HST", airport.getTimeZone());
		assertEquals((-36000l), airport.getOffset());
		assertFalse(airport.dstIsUsed());
		
		//Phoenix 
		airport.setLocation(33.43755f, -112.0078f);
		airport.setTimeZone();
		
		assertEquals("MST", airport.getTimeZone());
		assertEquals((-25200l), airport.getOffset());
		assertFalse(airport.dstIsUsed());

	}



	



	@Test
	public void testGetDirection() {
		Airport airport1 = new Airport();
		Airport airport2 = new Airport();

		airport1.setLocation(50.00f, -70.00f);
		airport2.setLocation(30.00f, -120.00f);
		
		assertTrue(airport1.getDirection(airport2) );
		
		airport1.setLocation(50.00f, -70.00f);
		airport2.setLocation(20.00f, -80.00f);
		assertFalse(airport1.getDirection(airport2));
		
		airport1.setLocation(61.176033f, -149.99008f);
		airport2.setLocation(42.365856f, -71.00962f);
		assertTrue(airport1.getDirection(airport2));


	}

	

	@Test
	public void testIsLayover() {
		Airport airport1 = new Airport();
		Airport airport2 = new Airport();
		Airport airport3 = new Airport();

		airport1.setLocation(50.00f, -70.00f);
		airport2.setLocation(30.00f, -120.00f);
		airport3.setLocation(10.00f, -100.00f);
		assertTrue(airport3.isLayover(airport1, airport2) );
		
		airport1.setLocation(50.00f, -70.00f);
		airport2.setLocation(20.00f, -80.00f);
		airport3.setLocation(40.00f, -90.00f);
		assertTrue(airport3.isLayover(airport1, airport2) );
		
		airport1.setLocation(50.00f, -70.00f);
		airport2.setLocation(20.00f, -100.00f);
		airport3.setLocation(40.00f, -90.00f);
		assertTrue(airport3.isLayover(airport1, airport2) );
		

		
		
		airport1.setLocation(50.00f, -70.00f);
		airport2.setLocation(30.00f, -110.00f);
		airport3.setLocation(35.00f, -75.00f);
		assertFalse(airport3.isLayover(airport1, airport2) );
		
	}

}
