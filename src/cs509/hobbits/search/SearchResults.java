/**
 * This class is used to get rationally possible direct flight plans
 * 
 * @author Xu Han 
 * 
 */

package cs509.hobbits.search;

import java.util.ArrayList;
import java.util.Iterator;



public class SearchResults {
	
	final private static boolean DEP = true;
	final private static boolean ARR = false;
	
	private String dep_code;
	private String arr_code;
	private String depart_date;
	
	private boolean has_seat;
	
	private Airport depart;
	private Airport arrival;

	private ArrayList <Airport> airport_list;
	
	public SearchResults(String _depart, String _arrival, String _depart_date){
		
		dep_code = _depart;
		arr_code = _arrival;
		depart_date = _depart_date;
		
		
		GetXML getXML = new GetXML();
		

		//this part get the airport list 
		airport_list = getXML.getAirportList();
		
		
		// this part convert the dep_code and arr_code strings into their corresponding airports.
		Iterator <Airport> ite = airport_list.iterator();
		while(ite.hasNext()){
			
			Airport  temp = ite.next();
			if(temp.getCode().equals(dep_code)){
				depart = temp;
				
			}
			if(temp.getCode().equals(arr_code)){
				arrival = temp;
			}	
			
		}
		
	}
	
	
	
	public ArrayList<FlightPlan> getPlans(){
		/*
		 * This method is responsible for getting all the possible plans
		 * If no flights between these two places return null
		 * If no available seat report no seats
		 * 
		 */
		
		if (depart==arrival)  return null;
		
		ArrayList<FlightPlan> plans =  new ArrayList<FlightPlan>();
		
		ArrayList<FlightPlan> plan0 = getDirectPlan(dep_code, arr_code, depart_date);

		plans.addAll(plan0);
		
		if(plans.size()==0) return null; 
		
		return plans;
	}
	
	private ArrayList<FlightPlan> getDirectPlan(String _dep_code, String _arr_code, String _date_code){
		/*
		 * this method return all the direct plans from depart place to arrival place.
		 */
		GetXML getXML = new GetXML();
			
		ArrayList <Flight> depart_list = getXML.getFlightList(_dep_code, _date_code, DEP);
	
		ArrayList <FlightPlan> plan = new ArrayList <FlightPlan>();
		
		for(int i=0; i<depart_list.size(); i++){
			
			// 1. check if the arrival airport of the current flight is the destination
			if(depart_list.get(i).getCode(ARR).equals(_arr_code)){
					
				// 2. check if the current flight has available seat now
				if(!depart_list.get(i).hasSeat(true)&&!depart_list.get(i).hasSeat(false)){
					
					has_seat = false;
					
				}else{
				
					FlightPlan temp = new FlightPlan(depart_list.get(i));
				
					plan.add(temp);
				}
			}
			
		}
		
		return plan;
		
	}
	
	
	public boolean hasSeat(){
		/*
		 * This method reports situation of no available seats
		 */
		return has_seat;
		
	}
	
}	
	