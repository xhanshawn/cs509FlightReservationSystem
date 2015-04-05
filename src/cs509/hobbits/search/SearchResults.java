/**
 * This class is used to get rationally possible direct flight plans
 * 
 * @author Xu Han 
 * 
 */

package cs509.hobbits.search;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.SimpleFormatter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger; 
import org.apache.logging.log4j.LogManager;










public class SearchResults {
	
	final private long Version = 2L;
	final private static boolean DEP = true;
	final private static boolean ARR = false;
	final private static boolean HORIZONTAL = true;
	final private static boolean VERTICAL = false;
	
	

	
	private String dep_code;
	private String arr_code;
	private boolean first_class=true;
	private float total_price;
	private float standard_price;
	private Airport depart;
	private Airport arrival;
	
	private boolean direction;
	private boolean has_seat;
	
	private String depart_date;
	private int stop_num;
	private ArrayList <Airport> airport_list;
	
	
	
public SearchResults(String _depart, String _arrival, String _depart_date, int _stop_num){
		
	
	
		dep_code = _depart;
		arr_code = _arrival;
		
		depart_date = _depart_date;
		
		
		stop_num = _stop_num;
		
		GetXML getXML = new GetXML();
		
		
		
		// this part compute the max_date of the end of the trip. I assume the max date is the 
		// depart date + 2 days(Date is the local day of the arrival place)
		String[] str = _depart_date.split("_");
		int day = Integer.parseInt(str[str.length-1]);
		day += 1 ;
		str[str.length-1] = String.valueOf(day);
//		max_date="";
		for(int i=0; i<str.length-1; i++){
//			max_date += str[i]+"_";
		
		}
//		max_date += str[str.length-1];
		

		
		
		//this part sort the airports 
		airport_list = getXML.getAirportList();
		
		Collections.sort( airport_list , new Comparator<Airport>(){

			@Override
			public int compare(Airport o1, Airport o2) {
				// TODO Auto-generated method stub
				
				if (o1.getLongitude()*1000000 != o2.getLongitude()*1000000){
					if (direction==HORIZONTAL){
						return (int) (o1.getLongitude()*1000000 - o2.getLongitude()*1000000);
					}else{
						return (int) (o1.getLatitude()*1000000 - o2.getLatitude()*1000000);
					}
				}
				
				return 0;
			}
	
		});
		
		

		
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
		
		//this part get the main direction of this flight. the direction is either horizontal or vertical
		direction = depart.getDirection(arrival);
	}
	
	
	
	
	public ArrayList<FlightPlan> getPlans(){
		/*
		 * this method is responsible for getting the possible plans
		 */
		
		if (depart==arrival||stop_num>2)  return null;

		ArrayList<FlightPlan> plans =  new ArrayList<FlightPlan>();
		
		
		LocalTime zero_time = new LocalTime();
		zero_time.setTime("00:00");
		
		
		if(stop_num==0){
			
			ArrayList<FlightPlan> plan0 = getDirectPlan(dep_code, arr_code, depart_date,zero_time.getTime());
	
			// this part get the direct plans and stopover plans.
		
			plans.addAll(plan0);
		}
		
		
		plans.addAll(getStopOverPlans(depart,arrival,depart_date,zero_time.getTime(), stop_num ));

		
		
		return plans;
		
		
	}
	
	private ArrayList<FlightPlan> getDirectPlan(String _dep_code, String _arr_code, String _date_code, Date _time){
		/*
		 * this method return all the direct plans from depart place to arrival place.
		 * date_code string is used for getting the flight list
		 * _time is to filter all plans which doesn't has enough time for changing the flight
		 * I assuming 2 hours for layover.
		 */
		GetXML getXML = new GetXML();
		
		//if the time of depart is larger than 22:00, 
		if (_time.getHours()>22&&(!_dep_code.equals(dep_code))) {
			char[] ch = _date_code.toCharArray();
			int day = ch[ch.length-2]*10 + ch[ch.length-1];
			day++;     //not available for all systems or all dates
			ch[ch.length-2] = (char) (day/10);
			ch[ch.length-1] = (char) (day%10);
			
			_date_code = String.valueOf(ch);
			
		}
		
		ArrayList <Flight> depart_list = getXML.getFlightList(_dep_code, _date_code, DEP);
		
		
		ArrayList <FlightPlan> plan = new ArrayList <FlightPlan>();
		for(int i=0; i<depart_list.size(); i++){
			if(depart_list.get(i).getCode(ARR).equals(_arr_code)){
				
				
				FlightPlan temp = new FlightPlan(depart_list.get(i));
				
				//first judgement requirement is for to get lay over plans
				//second is for direct flight plans.
				if((temp.getPlan().get(0).getLocalTime(DEP).getTime() - _time.getTime() > 120*60*1000)
						||(_dep_code.equals(dep_code)) ){
				
				if(depart_list.get(i).hasSeat(first_class)){
				plan.add(temp);}
				
				
				
				
				}
				//this part get the standard_price which is defined as the direct flight
				//currently this variable hasn't been used
				//but it may become another filter reason in the future
				standard_price = Math.max(depart_list.get(i).getPrice(first_class),standard_price);

			}
			
		}
		
		return plan;
		
	}
	
	
	//The main recursion algorithm to get all the plans has stopovers.
		private ArrayList<FlightPlan> getStopOverPlans(Airport _depart, Airport _arrival, String _date_code, Date _time, int _stop ){
			/*
			 * the main idea is:
			 * 1.get the sorted airport list by latitude or longitude.
			 * 2.get the depart airport and arrival airport
			 * 3.check if the following airports match the requirements of being a possible layover airport
			 * 4.recursively get the flight plans and connect them and return.
			 */
			
			
			
			ArrayList<FlightPlan> plans = new ArrayList<FlightPlan>();
			//limit the max stopover numbers
		
		
			
		
			int dep_index = airport_list.indexOf(_depart);
			int arr_index = airport_list.indexOf(_arrival);
			
			//get plan from the depart airport to the arrival airport by the main direction.
			for (int i = Math.min(dep_index, arr_index); i< Math.max(dep_index, arr_index) + 1; i++){
				
				Airport current = airport_list.get(i);  //current airport
				

				
				
				if(current.isLayover(_depart, _arrival)){	
					ArrayList<FlightPlan> first_part = new ArrayList<FlightPlan>();
					first_part = getDirectPlan(
					_depart.getCode(),current.getCode(),_date_code, _time);
					
					
					
					
					for(int j=0; j< first_part.size(); j++){
						
						FlightPlan first = first_part.get(j);

						Date cur_time = first.getLastFlight().getLocalTime(ARR);
						
						ArrayList <FlightPlan> following = new ArrayList<FlightPlan>();
						
						if(_stop == 1){
							following = getDirectPlan(current.getCode(), _arrival.getCode(), first.getLastFlight().getDateCode(ARR), cur_time);
						}
						
						if(_stop > 1){
							int num = _stop -1;
							
							following = getStopOverPlans(current, _arrival, first.getLastFlight().getDateCode(ARR), cur_time,num);

						}
						
						
						
						if(following == null) break;
						
						for(int k=0; k< following.size(); k++){
							
							FlightPlan new_plan = new FlightPlan(null);
							
							//direct only one flight
							new_plan.addPlan(first);
							new_plan.addPlan(following.get(k));
							
							plans.add(new_plan);

							
							
						}
						
					}
				}
			}
			
			

			
			
			return plans;
			
			
			
		}
	
	
	
	
	public boolean hasSeat(){
		/*
		 * This method reports situation of no available seats
		 */
		return has_seat;
		
	}
	
}	
	