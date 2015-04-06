/**
 * This is the class to generalize Flights
 * 
 * @author Xu Han 
 * 
 */
package cs509.hobbits.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;


public class Flight {
	private Airplane model;
	private int flight_time;
	private int number;
	
	private String dep_code;
	private String arr_code;
	
	
	
	private Airport dep_port;
	private Airport arr_port;
	
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
	
	public void setAirports(Airport _dep_port,Airport _arr_port){
		
		dep_code = _dep_port.getCode();
		arr_code = _arr_port.getCode();
		
		dep_port = _dep_port;
		arr_port = _arr_port;
		
		dep_time.setAirport(dep_port);
		arr_time.setAirport(arr_port);
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
		dep_time.setTime(_dep_time);
		
		arr_time.setDate(_arr[1], Integer.parseInt(_arr[2]), Integer.parseInt(_arr[0]));
		arr_time.setTime(_arr_time);

	
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
	
	
	public LocalTime getLocalTime(boolean depart){
		
		
		
		if (depart){
			return dep_time;
		}else{
			return arr_time;
		}
	}
	
	public String getLocalTimeString(boolean depart){
		if (depart){
			return dep_time.getTimeString();
		}else{
			return arr_time.getTimeString();
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
	
	
	
}