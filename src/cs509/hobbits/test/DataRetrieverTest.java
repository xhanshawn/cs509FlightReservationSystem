package cs509.hobbits.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import cs509.hobbits.search.Airplane;
import cs509.hobbits.search.Airport;
import cs509.hobbits.search.DataRetriever;
import cs509.hobbits.search.Flight;

public class DataRetrieverTest {

	

	@Test
	public void testGetAirportList() {
		ArrayList<Airport> airports = DataRetriever.getAirportList();
		assertEquals(52 ,airports.size());
		
		int code=0;
		int common = 0;
		int edge_name=0;
		boolean location = false;
		
		for(int i=0; i<airports.size(); i++){
			Airport current = airports.get(i);
			
			if(current.getCode().equals("FLL")) code += 1;
			if(current.getAirportName().equals("Minneapolis/St. Paul International")) common+=1;
			if(current.getAirportName().equals("Mineta San JosŽ International")) edge_name+=1;
			if(current.getLatitude()==21.324808f&&current.getLongitude()==-157.92519f) location= true;

		}
		
		assertEquals(1,code);
		assertEquals(1,common);
		assertTrue(location);
		assertEquals(1,edge_name);

		
		
	}

	@Test
	public void testGetAirplaneList() {
		ArrayList<Airplane> airplanes = DataRetriever.getAirplaneList();
		assertEquals(11 ,airplanes.size());
		
		int airbus = 0;
		int boeing = 0;
		int model = 0;
		boolean seat = false;
		
		for(int i=0; i<airplanes.size(); i++){
			Airplane current = airplanes.get(i);
			
			if(current.getManufacturer().equals("Airbus")) airbus += 1;
			if(current.getManufacturer().equals("Boeing")) boeing +=1;
			if(current.getModel().equals("A380")) model+=1;
			if(current.getSeatNumber(true)==24&&current.getSeatNumber(false)==82) seat = true;

		}
		
		assertEquals(5,airbus);
		assertEquals(6,boeing);
		assertEquals(1,model);
		assertTrue(seat);
		
	}

	@Test
	public void testGetFlightList() {
		DataRetriever dr = new DataRetriever();
		
		ArrayList<Flight> flights = dr.getFlightList("HOU", "2015_05_13", true);
		
		assertEquals(48,flights.size());
		
		
		final int A380_first = 128;
		final int A380_coach = 427;
		boolean hou = true;
		boolean dep_date_code = true;
		
		int flight_eg = 0;
		int flight_eg_first = 0;
		int flight_eg_coach = 0;
		int flight_eg_price = 0;
		String dep_date = "";
		String arr_date = "";

		for(int i=0; i<flights.size();i++){
			Flight current = flights.get(i);
			
			if(!current.getCode(true).equals("HOU")) hou = false;
			if(!current.getGMTDateCode(true).equals("2015_05_13")) dep_date_code = false;
			
			if(current.getNumber()=="8045"&&current.getFlightTime()==137
					&&current.getAirplane().getModel().equals("A320")) flight_eg++;
			
			if(current.getNumber()=="8036"&&current.getSeat(true)==A380_first - 45) flight_eg_first++;
			if(current.getNumber()=="8036"&&current.getSeat(false)==A380_coach - 131) flight_eg_coach++;
			if(current.getPrice(true)==3059.44f&&current.getPrice(false)==856.64f) flight_eg_price++;
			if(current.getNumber()=="8032") {
				dep_date = current.getDAOffsetString(true);
				arr_date = current.getDAOffsetString(false);
			}
			
		}
		
		assertTrue(hou);
		assertTrue(dep_date_code);
		assertEquals(1,flight_eg);
		assertEquals(1,flight_eg_first);
		assertEquals(1,flight_eg_coach);
		assertEquals(1,flight_eg_price);
		assertEquals("12 May 2015 19:01:00 CDT", dep_date);
		assertEquals("12 May 2015 22:53:00 EDT", arr_date);

	
	}

}
