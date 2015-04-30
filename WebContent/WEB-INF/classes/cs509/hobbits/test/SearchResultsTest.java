package cs509.hobbits.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import cs509.hobbits.search.DataRetriever;
import cs509.hobbits.search.Flight;
import cs509.hobbits.search.FlightPlan;
import cs509.hobbits.search.SearchResults;

public class SearchResultsTest {

	@Test
	public void testSearchResults() {
		
		DataRetriever.updateLists();
		
		SearchResults sr0 = new SearchResults("LAS","JFK", "2015_05_08",0, 20l);
		ArrayList<FlightPlan> plan0 = sr0.getPlans();
		
		for(int i=0; i<plan0.size(); i++){
			assertEquals("JFK",plan0.get(i).getLastFlight().getCode(false));
		}
		for(int i=0; i<plan0.size(); i++){
			assertEquals("LAS",plan0.get(i).getLastFlight().getCode(true));
		}
		for(int i=0; i<plan0.size(); i++){
			String offset_str = plan0.get(i).getPlan().get(0).getDAOffsetString(true);
			String[] strs = offset_str.split(" ");
			
			assertEquals("8",strs[0]);
			assertEquals("May",strs[1]);

		}
		
		for(int i=0; i<plan0.size(); i++){
			assertEquals(0,plan0.get(i).getStopOver());
		}
		
		
		
		SearchResults sr1 = new SearchResults("TPA","CMH", "2015_05_10",1, 50l);
		ArrayList<FlightPlan> plan1 = sr1.getPlans();
		
		
		for(int i=0; i<plan1.size(); i++){
			assertEquals("CMH",plan1.get(i).getLastFlight().getCode(false));
		}
		for(int i=0; i<plan1.size(); i++){
			assertEquals("TPA",plan1.get(i).getPlan().get(0).getCode(true));
		}
		for(int i=0; i<plan1.size(); i++){
			String offset_str = plan1.get(i).getPlan().get(0).getDAOffsetString(true);
			String[] strs = offset_str.split(" ");
			
			assertEquals("10",strs[0]);
			assertEquals("May",strs[1]);	
		}
		
		for(int i=0; i<plan1.size(); i++){
			assertEquals(1,plan1.get(i).getStopOver());
		}
		
		boolean window_larger = true;
		for(int i=0; i<plan1.size(); i++){
			ArrayList<Flight> flights = plan1.get(i).getPlan();

			if(flights.get(1).getDATime(true).getTime()
					-flights.get(0).getDATime(false).getTime() < 50l) window_larger = false;
			
		}
		assertTrue(window_larger);
		
		
		
		SearchResults sr2 = new SearchResults("BOS","HNL", "2015_05_13",2, 140l);
		ArrayList<FlightPlan> plan2 = sr2.getPlans();
		
		
		for(int i=0; i<plan2.size(); i++){
			assertEquals(2,plan2.get(i).getStopOver());
		}

		
		SearchResults sr_down = new SearchResults("BOS","HNL", "2015_05_06",1, 0l);
		ArrayList<FlightPlan> plan_down = sr_down.getPlans();
		
		assertTrue(plan_down.size()==0);

		SearchResults sr_up = new SearchResults("BOS","HNL", "2015_05_18",1, 0l);
		ArrayList<FlightPlan> plan_up = sr_up.getPlans();
		
		assertTrue(plan_up.size()==0);

		SearchResults sr_window = new SearchResults("BOS","HNL", "2015_05_07",1, 232l);
		ArrayList<FlightPlan> plan_window = sr_window.getPlans();
		
		
		boolean window_larger2 = true;
		for(int i=0; i<plan_window.size(); i++){
			ArrayList<Flight> flights = plan_window.get(i).getPlan();

			if(flights.get(1).getDATime(true).getTime()
					-flights.get(0).getDATime(false).getTime() < 50l) window_larger2 = false;
			
		}
		assertTrue(window_larger2);

	}

	

}
