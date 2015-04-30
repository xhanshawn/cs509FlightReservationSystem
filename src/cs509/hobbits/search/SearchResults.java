package cs509.hobbits.search;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/**
 * @author		Xu Han		<xhan@wpi.edu>
 * @version		0.5	
 * @since		2015-04-08	
 * 
 * This is the class to search possible reasonable flight plans depends on the requirements
 * The main algorithm:
 * 1. sort all the airports by the main direction, and direction is defined as either horizontal 
 * or vertical in the map(longitude difference larger or latitude larger ).
 * 2. Search only the airports whose latitude or longitude is between the depart and arrival airports
 * 3. If the distance between depart and arrival airports is too short, compensation of query range will
 * be made(+or-2).
 * 4. Check if the airport between depart and arrival airports is a possible layover, which will be judged
 * by two standards(1. distance is not longer than that of the destination, 2. distance larger than the direct
 * distance times a ratio. That percentage is assumed in the airport object. 
 */

public class SearchResults {
	
	/* *
	 * assumed parameters
	 */
	final private static boolean DEP = true;
	final private static boolean ARR = false;
	final private static boolean HORIZONTAL = true;
	
	/* *
	 * For check in the flow searching plans
	 */
	private String dep_code;
	private String arr_code;
	private String depart_date;

	private Airport depart;
	private Airport arrival;
	
	private LocalTime start_of_depart;
	private LocalTime end_of_depart;

	private boolean direction;
	
	private int stop_num;
	
	private ArrayList <Airport> airport_list;
	
	private static long window = 0l;
	final private long max_window = 24*60l;
	
	public SearchResults(String _depart, String _arrival, String _depart_date, int _stop_num, long _window){
		
		window = _window;
		dep_code = _depart;
		arr_code = _arrival;
		depart_date = _depart_date;
		stop_num = _stop_num;

		// this part convert the dep_code and arr_code strings into their corresponding airports.
		airport_list = DataRetriever.getAirportList();
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
		
		direction = depart.getDirection(arrival);
		
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
		
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy_MM_dd HH:mm z",Locale.ENGLISH);
		String depart_time = depart_date + " 00:00 GMT";
		
		try {
			
			Date da = date_format.parse(depart_time);
			start_of_depart = new LocalTime();
			start_of_depart.setTime(da.getTime() - LocalTime.getDSTOffset(da) *1000 - depart.getOffset()*1000 );
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		end_of_depart = new LocalTime();
		end_of_depart.setTime(start_of_depart.getTime() + 24*3600*1000 - 1000);

		//this part get the main direction of this flight. the direction is either horizontal or vertical
		direction = depart.getDirection(arrival);
	}
	
	
	
	/* *
	 * this method is responsible for getting the possible plans
	 */
	public ArrayList<FlightPlan> getPlans(){
		
		if (depart==arrival||stop_num>2)  return null;
		
		ArrayList<FlightPlan> plans =  new ArrayList<FlightPlan>();
		
		if(stop_num==0){
			
			//direct plan
			ArrayList<FlightPlan> plan0 = getDirectPlan(dep_code, arr_code, start_of_depart);
			plans.addAll(plan0);
		}
		
		else plans.addAll(getStopOverPlans(depart,arrival,start_of_depart, stop_num ));
		
		return plans;
	}
	
	/* *
	 * this method return all the direct plans from depart place to arrival place.
	 * date_code string is used for getting the flight list
	 * _time is to filter all plans which doesn't has enough time for changing the flight
	 * I assuming 2 hours for least lay over time.
	 * If _dep_code is depart airport, then we need to convert the query date regarding the airport time zone
	 */
	private ArrayList<FlightPlan> getDirectPlan(String _dep_code, String _arr_code, LocalTime _time){
		
		DataRetriever retrieve = new DataRetriever();
		
		String date_code = "";
		ArrayList <Flight> depart_list = null;
		
		if(_dep_code.equals(dep_code)) {
			
			//query two days if it is dep_code
			String date1 = LocalTime.parseToDateCode(_time);
			String date2 = LocalTime.parseToDateCode(end_of_depart);

			depart_list = retrieve.getFlightList(_dep_code, date1, DEP);
			depart_list.addAll(retrieve.getFlightList(_dep_code, date2, DEP));
		}else {
			
			//if not add the window time
			LocalTime query_date = new LocalTime();
			query_date.setTime(_time.getTime() + window*60*1000); 
			date_code = LocalTime.parseToDateCode(query_date);
			depart_list = retrieve.getFlightList(_dep_code, date_code, DEP);
		}
		
		ArrayList <FlightPlan> plan = new ArrayList <FlightPlan>();
		
		for(int i=0; i<depart_list.size(); i++){
			if(depart_list.get(i).getCode(ARR).equals(_arr_code)){
				
				Flight current = depart_list.get(i);
				
				if(_dep_code.equals(dep_code)){
					
					//if it is the first part from the depart place don't consider window
					if(current.getDATime(DEP).getTime()>start_of_depart.getTime() 
							&&current.getDATime(DEP).getTime()<end_of_depart.getTime())
					{
						
						FlightPlan temp = new FlightPlan(depart_list.get(i));
						plan.add(temp);
						
					}
					
				}else{
					
					//consider window if it is a multi-leg plan
					if((current.getDATime(DEP).getTime() - _time.getTime() > window*60*1000
							&& current.getDATime(DEP).getTime() - _time.getTime() <max_window*60*1000) ){
				
						if(depart_list.get(i).hasSeat()){
							FlightPlan temp = new FlightPlan(depart_list.get(i));
							plan.add(temp);
						}
					}
				}
			}
		}
		
		return plan;
	}
	
	
	/* *
	 * the main idea is:
	 * 1.get the sorted airport list by latitude or longitude.
	 * 2.get the depart airport and arrival airport
	 * 3.check if the following airports match the requirements of being a possible layover airport
	 * 4.recursively get the flight plans and connect them and return.
	 */
	private ArrayList<FlightPlan> getStopOverPlans(Airport _depart, Airport _arrival, LocalTime _time, int _stop ){

		ArrayList<FlightPlan> plans = new ArrayList<FlightPlan>();
			
		int dep_index = airport_list.indexOf(_depart);
		int arr_index = airport_list.indexOf(_arrival);
		int start_index = Math.min(dep_index, arr_index);
		int end_index = Math.max(dep_index, arr_index) + 1;
		int index_diff = end_index - start_index;
		
		//compensation for two neighbor airports
		if(index_diff<5){
			start_index = Math.max(start_index-2, 0);
			end_index = Math.min(end_index +2 , airport_list.size());
		}
		
		//get plan from the depart airport to the arrival airport by the main direction.
		for (int i = start_index; i< end_index; i++){
			
			if(airport_list.get(i).getCode()==arr_code||airport_list.get(i).getCode()==dep_code) continue; //filter depart and arrival
			Airport current = airport_list.get(i);  //current airport
			
			//check if it is possible a lay over
			if(current.isLayover(_depart, _arrival)){	
				ArrayList<FlightPlan> first_part = new ArrayList<FlightPlan>();
				
				//get the first part of a flight plan
				first_part = getDirectPlan(
				_depart.getCode(),current.getCode(), _time);
				
				for(int j=0; j< first_part.size(); j++){
					
					LocalTime cur_time = new LocalTime();
					cur_time = first_part.get(j).getLastFlight().getDATime(ARR);

					ArrayList <FlightPlan> following = new ArrayList<FlightPlan>();
					
					//if required stop number is 1, then find the other part
					if(_stop == 1){
						
						following = getDirectPlan(current.getCode(), _arrival.getCode(),  cur_time);
					}
					
					//if required stop number is 2, then continue recursion
					if(_stop > 1){
	
						int num = _stop -1;
						following = getStopOverPlans(current, _arrival, cur_time,num);

					}
						
					if(following == null) break;
						
					for(int k=0; k< following.size(); k++){
							
						FlightPlan new_plan = new FlightPlan(null);
							
						//direct only one flight
						new_plan.addPlan(first_part.get(j));
						new_plan.addPlan(following.get(k));
							
						plans.add(new_plan);

					}
				}
			}
		}
			
		return plans;
	}
}	
	