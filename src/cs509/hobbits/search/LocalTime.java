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
	
	private Date time_in_date;
	private String time_zone;
	private String gmt_time_str;
	private String time_str;
	
	private Airport airport;
	
	public LocalTime(){
		
		gmt_time_str = "";
		time_zone = "";
		time_in_date = null;
		
	}
	
	
	/*
	 * This method convert String of date to attributes
	 */
	
	
	
	/*
	 * This method converts string to specific time
	 */
	public void setTime(String _time){
		
		gmt_time_str = _time;
			
	}
	
	public void setAirport(Airport _port){
		airport = _port;
	}
	
	
	private void convertToDate(){
		time_in_date = new Date();
		
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy MMM dd HH:mm z",Locale.ENGLISH);
		
		try {
			Date da1 = date_format.parse(gmt_time_str);
			time_in_date.setTime(airport.getOffset() + da1.getTime());
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public Date getTime(){
		
		if(time_in_date==null) this.convertToDate();
		return time_in_date;
		
	}
	
	
	
	public String getTimeString(){
		
		if(time_in_date==null) this.convertToDate();
		
		String str = time_in_date.toGMTString();
		String strs [] =  str.split(" ");
		strs[strs.length-1] = this.getTimeZone();
		
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
		
		if(time_in_date==null) this.convertToDate();

		SimpleDateFormat date_format = new SimpleDateFormat("yyyy_MM_dd",Locale.ENGLISH);
		
		
		date_code = date_format.format(time_in_date);
		
		return date_code;
			
	}
	
	public String getTimeZone()
	{	
		if(time_zone.equals("")) time_zone = airport.getTimeZone();
		return time_zone;
	}
	
}
