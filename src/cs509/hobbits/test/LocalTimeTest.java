package cs509.hobbits.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import cs509.hobbits.search.Airport;
import cs509.hobbits.search.LocalTime;

public class LocalTimeTest {


	@Test
	public void testSetTime() {
		LocalTime lc = new LocalTime();
		Airport airport = new Airport();
		
		airport.setLocation(40.00f, -75.00f);
		airport.setTimeZone("2015_05_10");
		lc.setAirport(airport);
		
		lc.setTime("2015 May 10 05:04 GMT");
		
		assertEquals("2015_05_10",lc.getDateCode());
		
	}

	@Test
	public void testGetTime() {
		LocalTime lc = new LocalTime();
		Airport airport = new Airport();
		
		airport.setLocation(40.00f, -75.00f);
		airport.setTimeZone("2015_05_10");
		lc.setAirport(airport);
		
		lc.setTime("2015 May 10 05:04 GMT");
		
		
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy MMM dd HH:mm z",Locale.ENGLISH);
		
		try {
			Date da1 = date_format.parse("2015 May 10 05:04 GMT");
			
			assertEquals(da1, lc.getTime());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		

	}
	
	
	@Test
	public void testGetTimeZone() {
		LocalTime lc = new LocalTime();
		Airport airport = new Airport();
		
		airport.setLocation(40.00f, -75.00f);
		airport.setTimeZone("2015_05_10");
		lc.setAirport(airport);
		
		lc.setTime("2015 May 10 05:04 GMT");
		
		assertEquals("EDT", lc.getTimeZone());
		

	}

}
