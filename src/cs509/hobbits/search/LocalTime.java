/**
 * This is the class to generalize Local Time of depart places or arrival places
 * 
 * @author Xu Han 
 * 
 */

package cs509.hobbits.search;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LocalTime {
	private int year;
	private int month;
	private int day;
	private Date time;
	private String time_zone;
	private String time_str;
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
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		
		try {
			time = format.parse(_time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setTimeZone(String _time_zone){
		time_zone = _time_zone;
	}
	
	public Date getTime(){
		return time;
	}
	
	public String getTimeString(){
		
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
