package cs509.hobbits.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import cs509.hobbits.search.Airplane;
import cs509.hobbits.search.Airport;
import cs509.hobbits.search.DataRetriever;
import cs509.hobbits.search.FlightPlan;
import cs509.hobbits.search.ListToXMLBuilder;
import cs509.hobbits.search.SearchResults;

/**
 * @author		Xu Han     xhan@wpi.edu		
 * @version		1.17	
 * @since		2015-04-08	 
 */

public class ListToXMLBuilderTest {

	@Test
	public void testBuildPlanXML() {
		
		SearchResults sr_direct = new SearchResults("HOU","EWR", "2015_05_09",0, 20l);
		String direct = ListToXMLBuilder.buildPlanXML(sr_direct.getPlans(), false);
		assertTrue(direct.contains("<LocalTime>9 May 2015 19:16:00 CDT</LocalTime>"));
		
		SearchResults sr_1 = new SearchResults("HOU","EWR", "2015_05_09",1, 20l);
		String stop1 = ListToXMLBuilder.buildPlanXML(sr_1.getPlans(), false);

		
		assertTrue(stop1.contains("<FlightPlan Coach=\"75.84\" FirstClass=\"524.96\" Stopover=\"1\">"));
		assertTrue(stop1.contains("<Flight Airplane=\"737\" FlightTime=\"119\" Number=\"7891\">"));
		assertTrue(stop1.contains("<FirstClass Price=\"$165.76\">10</FirstClass>"));
		
		SearchResults sr_2 = new SearchResults("HOU","EWR", "2015_05_09",2,40l);
		String stop2 = ListToXMLBuilder.buildPlanXML(sr_2.getPlans(), false);
		assertTrue(stop2.contains("<FlightPlan Coach=\"95.94\" FirstClass=\"494.48\" Stopover=\"2\">"));
		
		SearchResults sr_return = new SearchResults("EWR","HOU", "2015_05_11",2,40l);
		ArrayList<FlightPlan> return_plan = sr_return.getPlans();
		ArrayList<FlightPlan> go_plan = sr_2.getPlans();
	
		ArrayList<FlightPlan> plans = new ArrayList<>();
		for(int i=0; i<go_plan.size(); i++){
			for(int j=0; j<return_plan.size(); j++){
				FlightPlan temp = new FlightPlan(null);
				temp.buildReturnPlan(go_plan.get(i), return_plan.get(j));
				plans.add(temp);
			}
		}
		
		
		String return_plans = ListToXMLBuilder.buildPlanXML(plans, true);
		assertTrue(return_plans.contains("<FlightPlan Coach=\"198.25\" FirstClass=\"1479.77\" Stopover=\"4\">"));

	
	}

	@Test
	public void testBuildAirportsXML() {
		ArrayList<Airport> airports =  DataRetriever.getAirportList();
		String airports_str = ListToXMLBuilder.buildAirportsXML(airports);
		assertTrue(airports_str.contains("<Airport Code=\"IAH\" Name=\"George Bush Intercontinental\"><Latitude>29.990494</Latitude><Longtitude>-95.33686</Longtitude></Airport>"));
	}

	@Test
	public void testBuildAirplanesXML() {
		ArrayList<Airplane> airplanes =  DataRetriever.getAirplaneList();
		String airports_str = ListToXMLBuilder.buildAirplanesXML(airplanes);
		assertTrue(airports_str.contains("<Airplane Manufacturer=\"Airbus\" Model=\"A340\"><FirstClassSeats>32</FirstClassSeats><CoachSeats>268</CoachSeats></Airplane>"));
	}

}
