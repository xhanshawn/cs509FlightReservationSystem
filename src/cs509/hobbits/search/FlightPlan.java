package cs509.hobbits.search;

/**
 * This is the class to generalize Airports
 * 
 * @author Xu Han 
 * 
 */

import java.util.ArrayList;

public class FlightPlan {
	
	final private static boolean DEP = true;
	final private static boolean ARR = false;
	final private static boolean FIRST = true;
	final private static boolean COACH = false;
	
	private ArrayList<Flight> plan = new ArrayList<Flight>();
	private ArrayList<Flight> depart_plan = new ArrayList<Flight>();
	private ArrayList<Flight> return_plan = new ArrayList<Flight>();

	private int stopover=0;
	private float fir_price;
	private float coa_price;
	private boolean round_trip = false;
	
	public FlightPlan(Flight initial_flight){
		
		stopover=0;
		
		if(initial_flight!=null){
			
			plan.add(initial_flight);
			
			fir_price = initial_flight.getPrice(FIRST);
			coa_price = initial_flight.getPrice(COACH);
			
		}
		
	}
	
	
	public boolean addFlight(Flight new_flight){
		
		if (new_flight==null)   return false;
		
		plan.add(new_flight);
		
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
		
		if(round_trip){
			
			stopover = depart_plan.size() + return_plan.size() -2;
			
		}else{
			
			stopover = plan.size()-1;
			
		}
		
		return stopover;
	}
	
	public void buildReturnPlan(FlightPlan _depart_plan, FlightPlan _return_plan){
		
		round_trip=true;
		
		depart_plan = _depart_plan.getPlan();
		return_plan = _return_plan.getPlan();
		
		fir_price = _depart_plan.getPrice(FIRST) + _return_plan.getPrice(FIRST);
		coa_price = _depart_plan.getPrice(COACH) + _return_plan.getPrice(COACH);
		
	}
	
	
	public boolean checkRoundTrip(){
		
		if (round_trip==false)  return false;
		
		if(depart_plan.get(depart_plan.size()-1).getDATime(ARR).getTime()<
				return_plan.get(0).getDATime(ARR).getTime()) return true;

		return false;
	}
	
	public boolean isRoundTrip(){
		
		return round_trip;
		
	}
	
	public ArrayList<Flight> getDepartPlan(){
		
		return depart_plan;
		
	}
	public ArrayList<Flight> getReturnPlan(){
		
		return return_plan;
		
	}
}
