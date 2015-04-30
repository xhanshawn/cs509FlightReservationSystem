package cs509.hobbits.search;

/**
 * This class is used to get rationally possible direct flight plans
 * 
 * @author Xu Han 
 * 
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;



public class SearchResults {
	
	final private static boolean DEP = true;
	final private static boolean ARR = false;
	final private static boolean HORIZONTAL = true;
	final private static boolean VERTICAL = false;
	
	private String dep_code;
	private String arr_code;
	private String depart_date;

	private float standard_price;
	private Airport depart;
	private Airport arrival;
	
	private LocalTime start_of_depart;
	private LocalTime end_of_depart;

	private boolean first_class=true;
	private boolean direction;
	
	private int stop_num;
	
	private ArrayList <Airport> airport_list;
	
	private static long window = 0l;
	private long max_window = 24*60l;
	
	public SearchResults(String _depart, String _arrival, String _depart_date, int _stop_num, long _window){
		
		
		window = _window;
		dep_code = _depart;
		arr_code = _arrival;
		depart_date = _depart_date;
		stop_num = _stop_num;

		
		//this part sort the airports 
		airport_list = DataRetriever.getAirportList();
		
		
		
		
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
	
	
	
	
	public ArrayList<FlightPlan> getPlans(){
		/*
		 * this method is responsible for getting the possible plans
		 */
		
		if (depart==arrival||stop_num>2)  return null;
		
		ArrayList<FlightPlan> plans =  new ArrayList<FlightPlan>();
		
		if(stop_num==0){
			
			ArrayList<FlightPlan> plan0 = getDirectPlan(dep_code, arr_code, start_of_depart);
	
			// this part get the direct plans and stopover plans.
			plans.addAll(plan0);
		}
		
		else plans.addAll(getStopOverPlans(depart,arrival,start_of_depart, stop_num ));
		
		return plans;
		
	}
	
	private ArrayList<FlightPlan> getDirectPlan(String _dep_code, String _arr_code, LocalTime _time){
		/*
		 * this method return all the direct plans from depart place to arrival place.
		 * date_code string is used for getting the flight list
		 * _time is to filter all plans which doesn't has enough time for changing the flight
		 * I assuming 2 hours for layover.
		 */
		DataRetriever retrieve = new DataRetriever();
		
		//if the time of depart is larger than 22:00, 
		String date_code = "";
		ArrayList <Flight> depart_list = null;
		
		if(_dep_code.equals(dep_code)) {
			
			String date1 = LocalTime.parseToDateCode(_time);
			String date2 = LocalTime.parseToDateCode(end_of_depart);

			depart_list = retrieve.getFlightList(_dep_code, date1, DEP);
			depart_list.addAll(retrieve.getFlightList(_dep_code, date2, DEP));
		
		}else {
			
			LocalTime query_date = _time;
			query_date.setTime(_time.getTime() + window*60*1000); 
			date_code = LocalTime.parseToDateCode(query_date);
			depart_list = retrieve.getFlightList(_dep_code, date_code, DEP);
			
		}
		
		
		ArrayList <FlightPlan> plan = new ArrayList <FlightPlan>();
		
		for(int i=0; i<depart_list.size(); i++){
			if(depart_list.get(i).getCode(ARR).equals(_arr_code)){
				
				Flight current = depart_list.get(i);
				
				//first judgement requirement is for to get lay over plans
				//second is for direct flight plans.
				if(_dep_code.equals(dep_code)){
					
					if(current.getDATime(DEP).getTime()>start_of_depart.getTime() 
							&&current.getDATime(DEP).getTime()<end_of_depart.getTime())
					{
						
						FlightPlan temp = new FlightPlan(depart_list.get(i));
						plan.add(temp);
						
					}
					
				}else{
					if((current.getDATime(DEP).getTime() - _time.getTime() > window*60*1000
							&& current.getDATime(DEP).getTime() - _time.getTime() <max_window*60*1000) ){
				
						if(depart_list.get(i).hasSeat()){
							FlightPlan temp = new FlightPlan(depart_list.get(i));
							plan.add(temp);
						}
				
					}
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
	private ArrayList<FlightPlan> getStopOverPlans(Airport _depart, Airport _arrival, LocalTime _time, int _stop ){
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
		int start_index = Math.min(dep_index, arr_index);
		int end_index = Math.max(dep_index, arr_index) + 1;
		int index_diff = end_index - start_index;
		if(index_diff<8){
			start_index = Math.max(start_index-4, 0);
			end_index = Math.min(end_index +4 , airport_list.size());
		}
		
		//get plan from the depart airport to the arrival airport by the main direction.
		for (int i = start_index; i< end_index; i++){
			
			Airport current = airport_list.get(i);  //current airport
				
			if(current.isLayover(_depart, _arrival)||index_diff<15){	
				ArrayList<FlightPlan> first_part = new ArrayList<FlightPlan>();
				first_part = getDirectPlan(
				_depart.getCode(),current.getCode(), _time);
				
				for(int j=0; j< first_part.size(); j++){
					
					FlightPlan first = first_part.get(j);
					LocalTime cur_time = first.getLastFlight().getDATime(ARR);
					ArrayList <FlightPlan> following = new ArrayList<FlightPlan>();
					
					if(_stop == 1){
						
						following = getDirectPlan(current.getCode(), _arrival.getCode(),  cur_time);
					}
						
					if(_stop > 1){
	
						int num = _stop -1;
						following = getStopOverPlans(current, _arrival, cur_time,num);

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

}	
	