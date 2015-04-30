package cs509.hobbits.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import cs509.hobbits.search.Airport;
import cs509.hobbits.search.DataRetriever;
import cs509.hobbits.search.SearchResults;

public class SearchResultsCombinationalTest {

	@Test
	public void test() {
		
		DataRetriever.updateLists();
		int count=0;
		ArrayList<Airport> airports = DataRetriever.getAirportList();
		for(int i=0; i<airports.size();i++){
			for(int j=0; j<airports.size();j++){
				if(airports.get(i).getCode()!=airports.get(j).getCode()){
					
					SearchResults sr = new SearchResults(airports.get(i).getCode(),airports.get(j).getCode(),"2015_05_11",1,0l);
					if(sr.getPlans().size()==0) {
						System.out.println(airports.get(i).getCode()+airports.get(j).getCode());
						count++;
						System.out.println(count);
					}
					
					
				}
			}
		}
		
	}

}
