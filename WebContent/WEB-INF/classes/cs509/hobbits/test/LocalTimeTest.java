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
	public void testToOffsetTimeString() {
		LocalTime lc = new LocalTime();
		Airport airport = new Airport();
		
		Date da = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm z");
		
		
		String str = "2015 May 12 05:59 GMT";
		Date da2 = new Date();
		LocalTime lc2 = new LocalTime();
		try {
			da = sdf.parse("2015 May 09 09:35 GMT");
			da2 = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lc.setTime(da.getTime());
		airport.setLocation(40.00f, -75.00f);
		airport.setTimeZone();
		
		lc2.setTime(da2.getTime());
		Airport airport2 = new Airport();
		airport2.setLocation(29.531406f, -98.468414f);
		airport2.setTimeZone();
		
		assertEquals("9 May 2015 05:35:00 GMT",lc.toOffsetTimeString(airport) );
		
		assertEquals("14 May 2015 00:59:00 GMT",lc2.toOffsetTimeString(airport2) );

	}

	@Test
	public void testGetDSTOffset() {
		LocalTime lc = new LocalTime();
		
		Date dateDST = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd E HH:mm z");
		
		String may_date = "2015 05 12 Tue 00:00 GMT";
		
		try {
			dateDST = sdf.parse(may_date);
			
			assertEquals(0l,lc.getDSTOffset(dateDST));
			
			String dec_date = "2015 12 12 Sat 00:00 EDT";
			
			Date dateNotDST = sdf.parse(dec_date);
			
			assertEquals(-3600l,lc.getDSTOffset(dateNotDST));
			
			String edgecase1 = "2015 03 08 Sun 02:00 EDT";
			
			dateDST = sdf.parse(edgecase1);
			
			assertEquals(0l,lc.getDSTOffset(dateDST));
			
			String edgecase2 = "2014 11 02 Sun 02:00 PDT";
			
			dateNotDST = sdf.parse(edgecase2);
			
			assertEquals(-3600l,lc.getDSTOffset(dateNotDST));

			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		

	}
	
	
	@Test
	public void testParseToDateCode() {
		LocalTime lcmore10 = new LocalTime();
		
		Date datemore10 = new Date();
		Date dateless10 = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd E HH:mm z");
		
		String date_more10 = "2015 05 12 Tue 00:00 GMT";
		String date_less10 = "2015 05 08 Tue 00:00 GMT";

		
		try {
			datemore10 = sdf.parse(date_more10);
			dateless10 = sdf.parse(date_less10);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		lcmore10.setTime(datemore10.getTime());
		
		LocalTime lcless10 = new LocalTime();
		
		lcless10.setTime(dateless10.getTime());
		
		
		assertEquals("2015_05_12", LocalTime.parseToDateCode(lcmore10));
		assertEquals("2015_05_08", LocalTime.parseToDateCode(lcless10));

		

	}
	
	@Test
	public void testParseStringToLocalTime(){
		Airport airport2 = new Airport();
		airport2.setLocation(29.531406f, -98.468414f);
		airport2.setTimeZone();
		LocalTime lc = LocalTime.parseStringToLocalTime("2015 May 12 05:59 GMT");
		assertEquals("12 May 2015 10:59:00 GMT", lc.toOffsetTimeString(airport2));
	}

}
