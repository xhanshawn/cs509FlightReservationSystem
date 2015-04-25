package cs509.hobbits.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import cs509.hobbits.search.Airport;
import cs509.hobbits.search.DataRetriever;

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
		
		//EDT
		Airport airport = new Airport();
		airport.setLocation(40.00f, -78.00f);
		airport.setTimeZone();
		
//		ArrayList<Airport> list = new ArrayList<> ();
//		list = DataRetriever.getAirportList();
//		
//		DataRetriever.setTime();
//		
//		for(int i=0; i<list.size(); i++){
//			if(list.get(i).);
//		}
		assertEquals("EDT", airport.getTimeZone());
		assertEquals((-14400l), airport.getOffset());
		assertTrue(airport.dstIsUsed());
		
		
		airport.setLocation(40.00f, -89.00f);
		airport.setTimeZone();
		
		assertEquals("CDT", airport.getTimeZone());
		assertEquals((-18000l), airport.getOffset());
		assertTrue(airport.dstIsUsed());

		
		airport.setLocation(40.00f, -117.00f);
		airport.setTimeZone();
		
		assertEquals("PDT", airport.getTimeZone());
		assertEquals((-25200l), airport.getOffset());
		assertTrue(airport.dstIsUsed());

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
		airport2.setLocation(20.00f, -100.00f);
		airport3.setLocation(40.00f, -90.00f);
		assertTrue(airport3.isLayover(airport1, airport2) );
		
		
		airport1.setLocation(50.00f, -70.00f);
		airport2.setLocation(30.00f, -110.00f);
		airport3.setLocation(35.00f, -75.00f);
		assertFalse(airport3.isLayover(airport1, airport2) );
		
	}

}
