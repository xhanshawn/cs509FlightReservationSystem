package Practice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatePrac {
	public static void main(String[] args){
		
		DatePrac prac = new DatePrac();
		prac.practice2();
	}

	
	private void practice(){
		
		String time = "2015 May 10 00:20 GMT";
		
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy MMM dd HH:mm z",Locale.ENGLISH);
		
		try {
			Date dat = (Date) date_format.parse(time);
			System.out.println(dat.getYear()+" "+dat.getMonth()+" "+dat.getDay()+" "
			+dat.getHours()+" "+dat.getMinutes()+" "+dat.getTimezoneOffset());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String time1 = "11:18";
		String time2 = "12:20";
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		try{
			Date d1 = format.parse(time1);
			Date d2 = format.parse(time2);
			
			long duration = d2.getTime() - d1.getTime();
			System.out.println(duration/1000/60+"");
		}catch (ParseException e){
			e.printStackTrace();
		}
		
		String month = "jan";
		SimpleDateFormat format2 = new SimpleDateFormat("MMM",Locale.ENGLISH);
		try{
			Date d3 = format2.parse(month);
			
			
			
			System.out.println(""+d3.getMonth());
		}catch (ParseException e){
			e.printStackTrace();
		}
		
		
	
		
		Date da = new Date();
		long seconds = da.getTime();
		da.setTime(seconds);
		da.toString();
		System.out.println(da.toString());
		
	}
	
	
	private void practice2(){
		
		String time = "2015 May 10 00:20 GMT";
		
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy MMM dd HH:mm z",Locale.ENGLISH);
		
		try {
			Date da = (Date) date_format.parse(time);
			System.out.println(da.toGMTString());
			System.out.println(""+da.getTime());
			Date da2 = new Date();
			da2.setTime(da.getTime()-18000000l+3600000l);
			
			System.out.println(da2.toGMTString());
			System.out.println(da.toString());
			System.out.println(da.getHours()+":"+da.getMinutes());
			System.out.println(da2.getTime());
			

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date date4 = new Date();
		System.out.println(date4.toString());
		
		
	}
}
