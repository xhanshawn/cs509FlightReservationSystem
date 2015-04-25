package cs509.hobbits.search;

/**
 * This is the class to generalize Flights
 * 
 * @author Xu Han 
 * 
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;



public class Flight {
	
	private Airplane model;
	
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
	private int fir_seat;
	private String coa_price;
	private int coa_seat;
	
	
	Flight(){
		
		model = new Airplane();
		dep_code = "";
		dep_time = null;
		arr_code = "";
		arr_time = null;
		fir_price = "";
		coa_price = "";
		
		dep_time_str = null;
		arr_time_str = null;
	}
	
	public void setPlane(Airplane airplane){
		 
		model = airplane;

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
		
		String price = (first_class) ? fir_price : coa_price; 
		
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
		
		return (float) ((Math.round(Float.parseFloat(String.valueOf(price_ch))*100))*0.01);
		
	}
	
	
	private void convertToTime(){
		
		dep_time = new LocalTime();
		arr_time = new LocalTime();
		
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy MMM dd HH:mm z",Locale.ENGLISH);
		
		if(dep_time_str!=""&&arr_time_str!="") {
			try {
				
				Date da1 = date_format.parse(dep_time_str);
				dep_time.setTime(da1.getTime());
				Date da2 = date_format.parse(arr_time_str);
				arr_time.setTime(da2.getTime());

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public LocalTime getDATime(boolean depart){
		
		if(dep_time==null||arr_time==null) convertToTime();
		
		if (depart){
			return dep_time;
		}else{
			return arr_time;
		}
		
	}
	
	public String getGMTDateCode(boolean depart){
		
		if(dep_time==null||arr_time==null) convertToTime();
		return  (depart) ? LocalTime.parseToDateCode(dep_time) : LocalTime.parseToDateCode(arr_time);
		
	}
	
	
	@SuppressWarnings("static-access")
	public Airport getAirport(boolean depart){
		
		String code = "";
		if(depart==true){
			code = dep_code;
		}else{
			code = arr_code;
		}
		DataRetriever r = new DataRetriever();
		ArrayList<Airport> list = new ArrayList<Airport>();

			list = r.getAirportList();
			
		Iterator<Airport> ite = list.iterator();
		
		while(ite.hasNext()){
			Airport temp = ite.next();
			
			if(code.equals(temp.getCode())){
				return temp;
			}
		}
		
		return null;
	}
	
	public String getDAOffsetString(boolean depart){
		
		String str = "";
		
		
		str = getDATime(depart).toOffsetTimeString((depart)? dep_port: arr_port);
		
		String zone_name = (depart)? dep_port.getTimeZone(): arr_port.getTimeZone();
		String substr = str.substring(0, str.length()-3);
		
		return substr + zone_name;
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
	
	
	public boolean hasSeat(){
		
		if(fir_seat==0 && coa_seat==0 ) return false;
		else return true;
		
	}
	
}