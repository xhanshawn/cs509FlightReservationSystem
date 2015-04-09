package cs509.hobbits.web;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import cs509.hobbits.search.Airplane;
import cs509.hobbits.search.Airport;
import cs509.hobbits.search.DataRetriever;
import cs509.hobbits.search.FlightPlan;
import cs509.hobbits.search.SearchResults;

public class ResponseFactory {
	
	public static String actionSearch(HttpServletRequest request){
		
		boolean round_trip = false;
		int req_stop;
		long window;
		
		
		String depart = request.getParameter("depart");
		String arrival = request.getParameter("arrival");
		String day = request.getParameter("day");
		String _window = request.getParameter("window");
		String stopover = request.getParameter("stop");
		String return_day = "";
		
		if(depart==null||arrival==null||day==null||stopover==null) return "Error";
		
		req_stop = Integer.parseInt(stopover);
		    
		if(_window!=null&&_window!=""){
	    	window = Long.parseLong(_window);
	    }else{
	    	window = 120l;
	    }
	    
	    if(return_day!=""&&return_day!=null) {
	    	round_trip = true;
	    } 
	    
		if((return_day = request.getParameter("return_day"))!=null) round_trip = true;
		
		ArrayList<FlightPlan> results = new ArrayList<FlightPlan>();
		
		if(!round_trip||return_day==null){
  			
  			SearchResults search = new SearchResults(depart,arrival,day, req_stop, window);
  			
  			results = search.getPlans();
  		
  		}else{
  			
  			ArrayList <FlightPlan> departs = new ArrayList<FlightPlan> ();
  			int count1 = 0;
  			
  			while(count1<=req_stop&&count1<=2){
  	  			
  				if(req_stop-count1<=2){
  					SearchResults search1 = new SearchResults(depart,arrival,day, count1, window);
  					ArrayList <FlightPlan> result = search1.getPlans();
  					if(result!=null) departs.addAll(result);
  				}
  				count1++;
  			}
  			
  	        
  			ArrayList <FlightPlan> [] result = new ArrayList[3];
  			for(int i=0; i< 3; i++){
  				SearchResults search = new SearchResults(arrival,depart,return_day, i, window );
  				result[i] = new ArrayList<FlightPlan>();
  				result[i] = search.getPlans();
  			}

  			
  			for(int i=0; i<departs.size(); i++){
  				int re_stop = req_stop - departs.get(i).getStopOver();
  				

  				
  				int count2=0;
  				
  				while(count2<result[re_stop].size()){
  					FlightPlan temp = new FlightPlan(null);

  					temp.buildReturnPlan(departs.get(i), result[re_stop].get(count2));;
  					
  					results.add(temp);
  					count2++;
  				}
  				
  				
  				
  			}
  			
  		}
		if(results.isEmpty()) return null;	


  		return XMLTxtBuilder.buildPlanXML(results, round_trip);
  		 
		
	}
	
	public static String actionList(HttpServletRequest request){
		
		String list_type = request.getParameter("list_type");
		
		if(list_type.equals("airports")) {
			ArrayList <Airport> airports = DataRetriever.getAirportList();
			return XMLTxtBuilder.buildAirportsXML(airports);
		}
		
		if(list_type.equals("airplanes")) {
			DataRetriever dr = new DataRetriever();
			ArrayList <Airplane> airplanes = DataRetriever.getAirplaneList();
			return XMLTxtBuilder.buildAirplanesXML(airplanes);
		}
		
		
		return null;
	}
	
}
