package cs509.hobbits.test;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import cs509.hobbits.search.Flight;
import cs509.hobbits.search.LocalTime;
import cs509.hobbits.search.Airport;
import cs509.hobbits.search.Airplane;

/**
 * @author		Yuzhou Xu	
 * @version		1.17	
 * @since		2015-04-08	 
 */


public class FlightTest {
	Flight flight;
	Airport BOS;
	Airport JFK;
	LocalTime dep_time;
	LocalTime arr_time;
	Airplane plane;
	
	
	@Before
	public void setUp() {
		flight = new Flight();
		BOS = new Airport();
		JFK = new Airport();
		plane = new Airplane();
		BOS.setCodeAndName("BOS", "Logan International");
		BOS.setLocation(42.365855f, -71.009624f);
		//BOS.setTimeZone("2015_05_10");
		JFK.setCodeAndName("JFK","John F. Kennedy International");
		JFK.setLocation(40.641519f, -73.77816f);
		//JFK.setTimeZone("2015_05_10");	
		dep_time = new LocalTime();
		arr_time = new LocalTime();
        flight.setDATime("2015 May 10 05:04 GMT", "2015 May 10 20:44 GMT");
        dep_time = flight.getDATime(true);
        arr_time = flight.getDATime(false);
		flight.setAirports(BOS, JFK);
		String plane = "A380";
		flight.setPlane(plane);
	}
	

   @Test
	public void testgetCode(){
	   
		assertEquals("BOS",flight.getCode(true));
		assertEquals("JFK",flight.getCode(false));
	}
	@Test
	public void testgetFlightTime(){
		flight.setFlightTime(8);
		assertEquals(8,flight.getFlightTime());
	}
	@Test
	public void testgetPrice(){
		flight.setPrice("%450.30", "$230.20");
		assertEquals(450.30f,flight.getPrice(true),10e-10);
		assertEquals(230.20f,flight.getPrice(false),10e-10);
	}
	@Test
	public void testgetLocalTime(){
		
			
		
      //  System.out.print(flight.getLocalTime(true));
		assertEquals(dep_time,flight.getDATime(true)); 
	}
	@Test
	public void testGetDAOffsetString(){
		 flight.setDATime("2015 May 10 05:04 GMT", "2015 May 10 20:44 GMT");
		 assertEquals("10 May 01:04 EDT",flight.getDAOffsetString(true)) ;
		 assertEquals("10 May 16:44 EDT",flight.getDAOffsetString(false)) ;

	}
	@Test
	public void testgetDateCode(){
         //System.out.println(dep_time.getDateCode()+"timestamp");
		 assertEquals("2015_05_10",flight.getGMTDateCode(true));
	}
	@Test
	public void testgetAirport(){
	     assertEquals(BOS.getCode(),flight.getAirport(true).getCode());
	}
	@Test
	public void testgetSeat(){
		flight.setSeats(1, 0);
		assertEquals(127,flight.getSeat(true));
	}
}