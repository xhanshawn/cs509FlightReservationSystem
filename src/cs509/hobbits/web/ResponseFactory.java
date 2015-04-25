package cs509.hobbits.web;

/**
 * This class is used to response to the request
 * 
 * @author Xu Han 
 * 
 */

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import cs509.hobbits.search.Airplane;
import cs509.hobbits.search.Airport;
import cs509.hobbits.search.DataRetriever;
import cs509.hobbits.search.FlightPlan;
import cs509.hobbits.search.ListToXMLBuilder;
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
  			
  			if(req_stop>2) return null;
  			
  			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd HH:mm");
  			
  			Date da1 = new Date();
  			Date da2 = new Date();
			try {
				
				da1 = sdf.parse(day+" 00:00");
	  			da2= sdf.parse(return_day + " 00:00");

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  			
  			if(da2.getTime()<da1.getTime()) return null;
  			
  			ArrayList <FlightPlan> departs = new ArrayList<FlightPlan> ();
  			ArrayList <FlightPlan> returns = new ArrayList<FlightPlan> ();

  			int count1 = 0;
  			
  			while(count1<=req_stop){
  	  			
  				SearchResults search1 = new SearchResults(depart,arrival,day, count1, window);
  				ArrayList <FlightPlan> result1 = search1.getPlans();
  				if(result1!=null) departs.addAll(result1);
  				
  				count1++;
  			}
  			
  			for(int count2=0; count2< req_stop+1; count2++){
  				
  				SearchResults search2 = new SearchResults(arrival,depart,return_day, count2, window );
  				ArrayList <FlightPlan> result2 = search2.getPlans();
  				if(result2!=null) returns.addAll(result2);
  			}
  			
  			for(int i=0; i<departs.size(); i++){
  				
  				int j=0;
  				
  				while(j<returns.size()){
  					
  					FlightPlan temp = new FlightPlan(null);
  					
  					if(departs.get(i).getStopOver() == req_stop
  							||returns.get(j).getStopOver() == req_stop)
  					{
  						
  						temp.buildReturnPlan(departs.get(i), returns.get(j));;
  						if(temp.checkRoundTrip()) results.add(temp);
  						
  					}
  					j++;
  				}
  			}
  		}
		
		if(results.isEmpty()) return null;	

  		return ListToXMLBuilder.buildPlanXML(results, round_trip);
		
	}
	
	public static String actionList(HttpServletRequest request){
	
		String list_type = request.getParameter("list_type");
		
		if(list_type.equals("airports")) {
			
			ArrayList <Airport> airports = DataRetriever.getAirportList();
			return ListToXMLBuilder.buildAirportsXML(airports);
		}
		
		if(list_type.equals("airplanes")) {
			
			ArrayList <Airplane> airplanes = DataRetriever.getAirplaneList();
			return ListToXMLBuilder.buildAirplanesXML(airplanes);
		}
		
		return null;
	}
	
	public static void actionUpdate(){
		
		DataRetriever.updateLists(); 
	}
	
}