/**
 * This is the class to generalize Airports
 * 
 * @author Xu Han 
 * 
 */


package cs509.hobbits.search;

import java.util.ArrayList;

public class FlightPlan {
	final private static boolean FIRST = true;
	final private static boolean COACH = false;
	
	private ArrayList<Flight> plan;
	private int stopover;
	private float fir_price;
	private float coa_price;
	
	public FlightPlan(Flight initial_flight){
		
		plan = new ArrayList<Flight>();
		plan.add(initial_flight);
		stopover=0;
		fir_price = initial_flight.getPrice(FIRST);
		coa_price = initial_flight.getPrice(COACH);
		
	}
	
	
	public boolean addFlight(Flight new_flight){
		
		if (new_flight==null)   return false;
		
		plan.add(new_flight);
		stopover++;
		
		fir_price += new_flight.getPrice(FIRST);
		coa_price += new_flight.getPrice(COACH);
		
		return true;
	}
	
	public ArrayList<Flight> getPlan(){
		return plan;
	}

	public float getPrice(boolean first_class){
		if(first_class){
			return fir_price;
		}else{
			return coa_price;
		}
	}
	
	
	public Airport getCurrentEnd(){
		return plan.get(plan.size()).getAirport(false);
	}
	
	
	public void addPlan(FlightPlan new_plan){
		ArrayList <Flight> flights = new  ArrayList <Flight> ();
		flights = new_plan.getPlan();
		
		for (int i=0; i<flights.size(); i++){
			this.addFlight(flights.get(i));
		}
		
	}
	

	
	public Flight getLastFlight(){
		return plan.get(plan.size()-1);
	}
	
	public int getStopOver(){
		return stopover;
	}
	
	
}
