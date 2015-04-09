/**
 * This is the class to generalize Airports
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
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;



public class Airport {
	final private float PERCENTAGE = 0.33f; 
	
	
	private String code;
	private String name;
	private float latitude;
	private float longitude;
	private String time_zone;
	private long offset;
	
	public Airport(){
		
		code = "";
		name = "";
		latitude = 0.0f;
		longitude = 0.0f;
	
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
	
	public void setTimeZone(String _day){
		_day += " 00:00 GMT";
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy_MM_dd HH:mm z",Locale.ENGLISH);
		
		String location = this.getLatitude() + "," + this.getLongitude();
		
		JSONObject obj = null;
		
		String url = "https://maps.googleapis.com/maps/api/timezone/json?location="
		+ location +"&timestamp=";
		try {
			Date date = (Date) date_format.parse(_day);
			url += "" + date.getTime()/1000;
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
						try{Thread.sleep(2000);
						}
						catch(Exception ex){}
					
						setTimeZone(_day);
					}
				
				String timezone = obj.getString("timeZoneName");
				
				String[] sArray = timezone.split(" ");
				int length = 0;
				String acronym = ""; 
				while(length<sArray.length){
					acronym += sArray[length].charAt(0);
					length++;
				}
				
				time_zone = acronym;
					
				
				offset = obj.getLong("dstOffset") + obj.getLong("rawOffset");
				
				
				
				
				
			}
			
		} catch (ParseException | IOException e) {
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
	}
	
	public long getOffset(){
		return offset;
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
				return true;}
			
		}else{
			if(Math.abs(this.getLatitude() - _depart.getLatitude()) > 
			Math.abs(PERCENTAGE*_arrival.getLatitude() - PERCENTAGE*_depart.getLatitude())){
				return true;}
		}
		
		return false;
	}
	
	
}
