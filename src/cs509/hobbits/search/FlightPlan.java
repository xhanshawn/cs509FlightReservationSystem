package cs509.hobbits.search;

import java.util.ArrayList;

/**
 * @author		Xu Han		xhan@wpi.edu
 * @version		1.17	
 * @since		2015-04-08		
 * 
 * This is the class to generalize Flight plans.
 * Flight plan are consisted of a list or two list of Flights. 
 * Some parameters are the sum of each flight
 */

public class FlightPlan {
	
	//These final parameters are assumed in the system
	final private static boolean DEP = false;
	final private static boolean ARR = false;
	final private static boolean FIRST = true;
	final private static boolean COACH = false;
	
	//3 lists for one way trip and round trip
	private ArrayList<Flight> plan = new ArrayList<Flight>();
	private ArrayList<Flight> depart_plan = new ArrayList<Flight>();
	private ArrayList<Flight> return_plan = new ArrayList<Flight>();
	
	//attributes
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
		//check precondition
		if (new_flight==null)   return false;
		
		plan.add(new_flight);
		
		fir_price += new_flight.getPrice(FIRST);
		coa_price += new_flight.getPrice(COACH);
		
		return true;
	}
	
	public ArrayList<Flight> getPlan(){
		
		return plan;
	}
	
	/* *
	 * The price result is rounded to the format x.xx of a float variable
	 */
	public float getPrice(boolean first_class){
		
		if(first_class){
			return (float) (Math.round(fir_price*100)*0.01);
		}else{
			return (float) (Math.round(coa_price*100)*0.01);
		}
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
		
		round_trip = true;
		
		depart_plan = _depart_plan.getPlan();
		return_plan = _return_plan.getPlan();
		
		fir_price = _depart_plan.getPrice(FIRST) + _return_plan.getPrice(FIRST);
		coa_price = _depart_plan.getPrice(COACH) + _return_plan.getPrice(COACH);
	}
	
	/* *
	 * This is used for check if the current round trip lists are rational which means the arrival time
	 * of depart must be earlier than the depart time of the return trip. Also window time should be considered
	 */
	public boolean checkRoundTrip(long _window){
		
		if (round_trip==false)  return false;
		
		if(depart_plan.get(depart_plan.size()-1).getDATime(ARR).getTime() + _window*60*1000l<
				return_plan.get(0).getDATime(DEP).getTime()) return true;

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
