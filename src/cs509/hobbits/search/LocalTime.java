/**
 * This is the class to generalize Local Time of depart places or arrival places
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

public class LocalTime {
	private int year;
	private int month;
	private int day;
	private Date time;
	private String time_zone;
	private String time_str;
	
	private Airport airport;
	
	LocalTime(){

		time_zone = "";
		time = new Date();
	}
	
	
	/*
	 * This method convert String of date to attributes
	 */
	public boolean setDate(String _month, int _day, int _year){
		
		if((8<=_day&&_day<=17)
			&&(_year==2015)){
			

			SimpleDateFormat format = new SimpleDateFormat("MMM",Locale.ENGLISH);
			try{
				Date d = format.parse(_month);
				
				
				month = d.getMonth() + 1;
			}catch (ParseException e){
				e.printStackTrace();
			}
			
			
			day = _day;
			year = _year;
			return true;
		}
	
		return false;
	}
	
	
	/*
	 * This method converts string to specific time
	 */
	public void setTime(String _time){
		
			time_str = _time;
			SimpleDateFormat date_format = new SimpleDateFormat("yyyy MMM dd HH:mm z",Locale.ENGLISH);
			try {
				Date da1 = date_format.parse(time_str);
				time.setTime(airport.getOffset() + da1.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	public void setTimeZone(String _time_zone){
		time_zone = _time_zone;
	}
	
	public void setAirport(Airport _port){
		airport = _port;
	}
	
	private Date convertLocalTime(String _date){
		
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy MMM dd HH:mm z",Locale.ENGLISH);
		
		String location = this.airport.getLatitude() + "," + this.airport.getLongitude();
		
		
		String url = "https://maps.googleapis.com/maps/api/timezone/json?location="
		+ location +"&timestamp=";
		try {
			Date date = (Date) date_format.parse(_date);
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
				System.out.println(str.toString());
				JSONObject obj = new JSONObject(str.toString());
				
				
				
				
				String timezone = obj.getString("timeZoneName");
				
				String[] sArray = timezone.split(" ");
				int length = 0;
				String acronym = ""; 
				while(length<sArray.length){
					acronym += sArray[length].charAt(0);
					length++;
				}
				
				this.setTimeZone(acronym);
					
				
				long offset = obj.getLong("dstOffset") + obj.getLong("rawOffset");
				
				Date da2 = new Date ();
				da2.setTime(date.getTime() + offset*1000);
				
				System.out.println(url);
				return da2;
				
			}
			
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			
			return convertLocalTime(_date);
		}
		return null; 
		
		
		
	}
	
	public Date getTime(){
		
		
		
//		time = convertLocalTime(time_str);
		return time;
	}
	
	public String getTimeString(){
		String str = time.toGMTString();
		String strs [] =  str.split(" ");
		strs[strs.length-1] = airport.getTimeZone();
		
		StringBuffer buf =  new StringBuffer();
		for(int i=0; i<strs.length-1; i++){
			buf.append(strs[i]+" ");
		}
		buf.append(strs[strs.length-1]);
		time_str = buf.toString();
		
		return time_str;
	}
	
	public String getDateCode(){
		String date_code = "";
		
		if(month<10){
			
			date_code = ""+ year + "_0" +month;
			//Change month to 0X
		
		}else{
			date_code = ""+ year + "_" +month;
			
		}
		
		if(day<10){
			
			date_code = date_code + "_0" +day;
			//Change month to 0X
		
		}else{
			date_code = date_code + "_"+day;
			
		}
		
		return date_code;
			
	}
	
	public String getTimeZone()
	{
		return time_zone;
	}
}
