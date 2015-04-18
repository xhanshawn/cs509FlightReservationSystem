package cs509.hobbits.search;

/**
 * This is the class to generalize Airports
 * 
 * @author Xu Han 
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;


public class Airport {
	
	final private float PERCENTAGE = 0.2f; 
	
	
	private String code;
	private String name;
	private String time_zone;
	
	private float latitude;
	private float longitude;

	private long offset;
	private int dst;
	
	public Airport(){
		
		code = "";
		name = "";
		latitude = 0.0f;
		longitude = 0.0f;
		dst = 0;
		
	}
	
	/*
	 * Setters and getters 
	 */
	public void setCodeAndName(String _code, String _name){
		
		code = _code;
		name = _name;
	
	}

	public void setLocation(float lat, float longi ){
		
		latitude = lat;
		longitude = longi;	
		
	}
	
	//this setter is only called when initialization and update airport lists and time zone information
	public void setTimeZone(){
		
		JSONObject obj = retrieveTimeZone();
		
		try {
			
			time_zone = obj.getString("abbreviation");
			offset = obj.getLong("gmtOffset");
			dst =  obj.getInt("dst");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//this method is used to retrieve Time Zone informations from API provided by timezoneDB
	//the default time is "2015 05 10 12:00 GMT". The DST is assumed
	private JSONObject retrieveTimeZone(){
		
		String sample_date = "2015 05 10 12:00 GMT";
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy MM dd HH:mm z",Locale.ENGLISH);
		
		Date day = null;
		
		try {
			
			day = date_format.parse(sample_date);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String location = this.getLatitude() + "&lng=" + this.getLongitude();
		
		JSONObject obj = null;
		
		String url = "http://api.timezonedb.com/?format=json&lat="
		+ location +"&key=NMBW9G7ILB6H&timestamp=" + day.getTime()/1000;
		
		try {
			
			URL u = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) u.openConnection();
			connection.setRequestMethod("GET");
			
			int responseCode = connection.getResponseCode();
			
			if ((responseCode>=200) && (responseCode <=299)){
				
				InputStream is = connection.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				StringBuilder str = new StringBuilder();
				String line = "";
				
				while((line=reader.readLine())!=null){
					
					str.append(line);
					
				}
				
				obj = new JSONObject(str.toString());
				
				if(obj.has("errorMessage")||!obj.getString("status").equals("OK"))
					{
						try{
							Thread.sleep(2000);
						
						}
						catch(Exception ex){
							ex.printStackTrace();
						}
					
						retrieveTimeZone();
						
					}
				
			}
			
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				System.out.println(obj.getString("status"));
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		return obj;
		
	}
	
	public long getOffset(){
		
		if(offset!=0) return offset;
		else {
			setTimeZone();
			return offset;	
		}
		
	}
	
	public boolean dstIsUsed(){
		
		if(dst==1) return true;
		else return false;
	
	}
	
	
	public String getCode(){
		
		return code;
		
	}
	
	public float getLatitude(){
		
		return latitude;
		
	}
	public float getLongitude(){
		
		return longitude;
		
	}
	
	public String getTimeZone(){
		
		return time_zone;
		
	}
	
	//This method is to get the main direction of the flight requirements
	//eithber be in the longitude direction or in the latitude direction
	public boolean getDirection(Airport _arrival){
		
		float lat_dis = Math.abs( this.getLatitude() - _arrival.getLatitude());
		float longi_dis = Math.abs( this.getLongitude() - _arrival.getLongitude());
		
		if(lat_dis>longi_dis){
			return false;	
		}else{
			return true;
		}

	}
	
	public String getAirportName(){
		
		return name;
		
	}
	
	public boolean isLayover(Airport _depart, Airport _arrival){
		
		boolean direction = _depart.getDirection(_arrival);
		
		double arr_dis = Math.sqrt((double)( _arrival.getLongitude() - _depart.getLongitude()))
				+ Math.sqrt((double)( _arrival.getLatitude() - _depart.getLatitude()));
		
		double stopover_dis = Math.sqrt((double)( this.getLongitude() - _depart.getLongitude()))
				+ Math.sqrt((double)( this.getLatitude() - _depart.getLatitude()));
		
		if(stopover_dis>arr_dis) return false;
		
		if(direction){
			
			if(Math.abs(this.getLongitude() - _depart.getLongitude()) > 
			Math.abs(PERCENTAGE*_arrival.getLongitude() - PERCENTAGE*_depart.getLongitude())){
				return true;
			}
			
			
		}else{
			
			if(Math.abs(this.getLatitude() - _depart.getLatitude()) > 
			Math.abs(PERCENTAGE*_arrival.getLatitude() - PERCENTAGE*_depart.getLatitude())){
				return true;
			}
		}
		
		return false;
		
	}
	
}
