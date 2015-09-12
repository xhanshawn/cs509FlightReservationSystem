package cs509.hobbits.search;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * @author		Xu Han		<xhan@wpi.edu>
 * @version		0.5	
 * @since		2015-04-08	
 * 
 * This is the class to generalize Flights, set attributes of flight and
 * convert, modify and return some attributes
 * 
 */

public class Flight {
	
	private Airplane model;
	private String model_str;
	private int flight_time;
	private int number;
	
	private String dep_code;
	private String arr_code;
	private String dep_time_str;
	private String arr_time_str;
	
	private Airport dep_port;
	private Airport arr_port;
	
	private LocalTime dep_time;
	private LocalTime arr_time;
	
	private String fir_price;
	private int fir_seat_purchased;
	private String coa_price;
	private int coa_seat_purchased;
	
	private static Map<String, Airplane> Airplane_list = new HashMap<String, Airplane>();

	
	Flight(){
		
		model = null;
		model_str = null;
		dep_code = "";
		dep_time = new LocalTime();
		arr_code = "";
		arr_time = new LocalTime();
		fir_price = "";
		coa_price = "";
		
		dep_time_str = null;
		arr_time_str = null;
		updateAirplaneList();
	}
	
	private void updateAirplaneList(){
		
		if(Airplane_list.isEmpty())	 {
			
			ArrayList<Airplane> airplanes = new ArrayList<Airplane>();
			airplanes = DataRetriever.getAirplaneList();
			
			for(int i=0; i< airplanes.size(); i++){
				
				Airplane_list.put(airplanes.get(i).getModel(), airplanes.get(i));
				
			}
		}
	}
	
	
	
	public void setPlane(String airplane){
		 
		model_str = airplane;
	}
	
	
	public void setNumber(int num){
		
		number = num;
	}
	public int getNumber(){
		
		return number;
	}
	
	public void setAirports(Airport _dep_port,Airport _arr_port){
		
		dep_code = _dep_port.getCode();
		arr_code = _arr_port.getCode();
		
		dep_port = _dep_port;
		arr_port = _arr_port;

	}
	
	
	public void setFlightTime(int _flight_time){
		
		flight_time = _flight_time;
	}
	
	// This method is to convert string to time
	public void setDATime(String _dep_time, String _arr_time){
		
		

		if(dep_time_str==null) dep_time_str = _dep_time;
		if(arr_time_str==null) arr_time_str = _arr_time;
		
	}
	
	
	
	// This method is to convert price and set
	public void setPrice(String _fir_price, String _coa_price){
		
		if(fir_price==""||coa_price=="") {
			
			fir_price = _fir_price;
			coa_price = _coa_price;

		}
		
	}
	
	/* *
	 * This setter has been refactored by put integer number conversion into getter.
	 * 
	 * Pointed out by Yuzhou Xu in the Peer Review
	 */
	public void setSeats(int _first, int _coach){
		
		fir_seat_purchased = _first;
		coa_seat_purchased = _coach;
	}
	
	public String getCode(boolean dep){
		
		return (dep) ? dep_code : arr_code;
	}
	
	public int getFlightTime(){
		
		return flight_time;
	}
	
	/* *
	 * Getter for prices value of a flight
	 * Convert a String of price to integer for calculating total price of a Flight Plan
	 */
	public float getPrice(boolean first_class){
		
		String price = (first_class) ? fir_price : coa_price; 
		
		
		//Fetch the integer part of String 
		char[] price_ch = new char[price.length()+1];
		
		price_ch = price.toCharArray();
		
		int thousand=0;
		for (int m=0; m<price.length()-1; m++){
			
			if(m+1+thousand<price.length()){
			
				if(price_ch[m+1]==','){
					thousand++;
				}
			
				price_ch[m] = price_ch[m+1+thousand];
			}
		}
		
		//Round a price value to xxx.xx format float point number
		return (float) ((Math.round(Float.parseFloat(String.valueOf(price_ch))*100))*0.01);
	}
	
	
	/* *
	 * Convert the dep_time string into an instance of LocalTime and return
	 */
	public LocalTime getDATime(boolean depart){
		
		if(dep_time_str!="")	dep_time = LocalTime.parseStringToLocalTime(dep_time_str);
		
		if(arr_time_str!="")	arr_time = LocalTime.parseStringToLocalTime(arr_time_str);
		
		System.out.println(arr_time.toGMTString());

		return (depart) ? dep_time : arr_time;
	}
	
	/* *
	 * Get the GMT Date Code of depart day or arrival day in the format yyyy_MM_dd
	 * GMT is because the system time is in GMT
	 */
	public String getGMTDateCode(boolean depart){
		
		this.getDATime(true);
		
		return  (depart) ? LocalTime.parseToDateCode(dep_time) : LocalTime.parseToDateCode(arr_time);
	}
	
	
	public Airport getAirport(boolean depart){
		
		return (depart) ? dep_port : arr_port;
	}
	
	/* *
	 * Get the depart and arrival time string considered the time zone offset of the location
	 * The time string contains offset gotten from local time class doesn't have TimeZone 
	 * Abbreviation name.
	 */
	public String getDAOffsetString(boolean depart){
		
		String str = "";
		
		str = this.getDATime(depart).toOffsetTimeString((depart)? dep_port: arr_port);
		
		String zone_name = (depart)? dep_port.getTimeZone(): arr_port.getTimeZone();
		String substr = str.substring(0, str.length()-3);
		
		
		return substr + zone_name;
	}
	
	
	public Airplane getAirplane(){
		
		return model;
	}
	
	
	/* *
	 * This is the getter to get the seat number based on the input boolean value
	 * Seat number should be the seat number of a certain model airplane minus the 
	 * purchased seat number
	 */
	public int getSeat(boolean input){
		
		if(model == null){
			
			model = new Airplane();
			model = Airplane_list.get(model_str);
		}
		
		if(input){
			return model.getSeatNumber(true) - fir_seat_purchased;
		}else{
			return model.getSeatNumber(false) - coa_seat_purchased;
		}
	}
	
	/* *
	 * This is for check if a flight has seats available
	 * If neither first class seat nor coach seat is available return false
	 */
	public boolean hasSeat(){
		
		return !((this.getSeat(true) == 0)&&(this.getSeat(false) == 0));
	}
	
}