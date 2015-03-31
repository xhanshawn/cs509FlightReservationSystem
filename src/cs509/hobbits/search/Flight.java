/**
 * This is the class to generalize Flights
 * 
 * @author Xu Han 
 * 
 */
package cs509.hobbits.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Flight {
	private Airplane model;
	private int flight_time;
	private int number;
	
	private String dep_code;
	private String arr_code;
	
	private LocalTime dep_time;
	private LocalTime arr_time;
	
	private float fir_price;
	private int fir_seat;
	private float coa_price;
	private int coa_seat;
	
	
	Flight(){
		
		model = new Airplane();
		dep_code = "";
		dep_time = new LocalTime();
		arr_code = "";
		arr_time = new LocalTime();
		fir_price = 0.0f;
		coa_price = 0.0f;
	}
	
	public void setPlane(String _airplane, ArrayList<Airplane> airplane_list){
		
		
		for(int i=0; i<airplane_list.size(); i++){
			if (airplane_list.get(i).getModel().equals(_airplane)) {
				 
				model = airplane_list.get(i);

			}
		}
		

	}
	
	
	public void setNumber(int num){
		number = num;
	}
	public int getNumber(){
		return number;
	}
	
	public void setCode(String _dep_code, String _arr_code){
		dep_code = _dep_code;
		arr_code = _arr_code;
	}
	
	
	public void setFlightTime(int _flight_time){
		flight_time = _flight_time;
	}
	
	// This method is to convert string to time
	public void setLocalTime(String _dep_time, String _arr_time){
		//Time Format 2015(0) May(1) 10(2) 01:49(3) EDT(4)
		String[] _dep = _dep_time.split(" ");
		String[] _arr = _arr_time.split(" ");
		
		
		
		dep_time.setDate(_dep[1], Integer.parseInt(_dep[2]), Integer.parseInt(_dep[0]));
		dep_time.setTime(_dep[3]);
		
		arr_time.setDate(_arr[1], Integer.parseInt(_arr[2]), Integer.parseInt(_arr[0]));
		arr_time.setTime(_arr[3]);
		
		
		dep_time.setTimeZone(_dep[4]);
		dep_time.setTimeZone(_arr[4]);
	}
	
	// This method is to convert price and set
	public void setPrice(String _fir_price, String _coa_price){
		char[] fir = new char[_fir_price.length()+1];
		char[] coa = new char[_coa_price.length()+1];
		
		fir = _fir_price.toCharArray();
		coa = _coa_price.toCharArray();
		
		int thousand=0;
		for (int m=0; m<_fir_price.length()-1; m++){
			
			if(m+1+thousand<_fir_price.length()){
			if(fir[m+1]==','){
				thousand++;
			}
			
				fir[m] = fir[m+1+thousand];
			}
		}
		thousand=0;
		for (int n=0; n<_coa_price.length(); n++){
			
			if(n+thousand+1<_coa_price.length()){
			if(coa[n+1]==','){
				thousand++;
			}
			
			coa[n] = coa[n+1+thousand];
			}
		}
		
		fir_price = Float.parseFloat(String.valueOf(fir));
		coa_price = Float.parseFloat(String.valueOf(coa));
	}
	
	public void setSeats(int _first, int _coach){
		
		fir_seat = model.getSeatNumber(true) - _first;
		coa_seat = model.getSeatNumber(false) - _coach;
		

		if (fir_seat<0||coa_seat<0){
			throw new IllegalArgumentException("Error: Seat number on the server is not correct");
		}
		
	}
	
	public String getCode(boolean dep){
		if (dep){
			return dep_code;
		}else{
			return arr_code;
		}
		
	}
	
	public int getFlightTime(){
		return flight_time;
	}
	
	public float getPrice(boolean first_class){
		if (first_class==true){
			return fir_price;
		}
		else return coa_price;
	}
	
	
	public Date getLocalTime(boolean depart){
		
		
		
		if (depart){
			return dep_time.getTime();
		}else{
			return arr_time.getTime();
		}
	}
	
	public String getDateCode(boolean depart){
		if (depart){
			return dep_time.getDateCode();
		}else{
			return arr_time.getDateCode();
		}
	}
	
	
	public Airport getAirport(boolean depart){
		String code = "";
		if(depart==true){
			code = dep_code;
		}else{
			code = arr_code;
		}
		GetXML getXML = new GetXML();
		ArrayList<Airport> list = new ArrayList<Airport>();

			list = getXML.getAirportList();
		
			
		Iterator<Airport> ite = list.iterator();
		
		while(ite.hasNext()){
			Airport temp = ite.next();
			if(code.equals(temp.getCode())){
				return temp;
			}
		}
		
		return null;
	}
	
	
	
	public Airplane getAirplane(){
		return model;
	}
	
	public int getSeat(boolean input){
		if(input){
			return fir_seat;
		}else{
			return coa_seat;
		}
	}
	
	
	public boolean hasSeat(boolean _first_class){
		if(_first_class){
			if(fir_seat==0) return false;
		}else{
			if(coa_seat==0) return false;
		}
		return true;
	}
	
	
	public ArrayList<String> getFlightInformation(){
	    ArrayList<String> FlightInformation=new ArrayList<String>();
		   
		FlightInformation.add("Number: " + number);
		FlightInformation.add("Time: " + flight_time);
		FlightInformation.add("Departure Airport: "+ dep_code);
		FlightInformation.add("Arrival Airport: " + arr_code);
		FlightInformation.add("Dep_time: " + dep_time.getTime().getHours()+":"+dep_time.getTime().getMinutes());
		FlightInformation.add("Arr_time: " + arr_time.getTime().getHours() + ":" + arr_time.getTime().getMinutes());
		FlightInformation.add("FirstClass: $" + fir_price);
		FlightInformation.add("left: "+fir_seat);
		FlightInformation.add("Coach: $" + coa_price);
		FlightInformation.add("left: "+ coa_seat);
		  
		return FlightInformation;
	}
}