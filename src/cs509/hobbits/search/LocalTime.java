package cs509.hobbits.search;
/**
 * This is the class to generalize Local Time of depart places or arrival places
 * 
 * @author Xu Han 
 * 
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;




public class LocalTime extends Date {
	
	private String offset_time_str;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7069728987148131849L;

	public LocalTime(){
		
		offset_time_str = null;
	}
	
	
	@SuppressWarnings("deprecation")
	public String toOffsetTimeString(Airport airport){
		
		if(offset_time_str==null){
			long offset = airport.getOffset();
		
			Date da = new Date();
			da.setTime(this.getTime() + offset*1000);

			long dst_offset = 0;
		
			if(airport.dstIsUsed()) dst_offset = getDSTOffset(da);
		
			this.setTime(this.getTime() + offset*1000 + dst_offset*1000);
			
			offset_time_str = this.toGMTString();
		}
		return offset_time_str;
	}
	
	public static long getDSTOffset(Date da){
		
		SimpleDateFormat date_format = new SimpleDateFormat("E yyyy MM dd HH:mm");
		date_format.setTimeZone(TimeZone.getTimeZone("GMT"));
	
		String formated_time = date_format.format(da);
		String [] strs = formated_time.split(" ");
		String sun = strs[0];
		
		int month = Integer.parseInt(strs[2]);
		int day = Integer.parseInt(strs[3]);
		int hour = Integer.parseInt(strs[4].substring(0,2));

		if(month >4 && month <11) return 0;
		
		if(month == 3 && day>7 && sun.equals("Sun") &&hour>=2){
			return 0;
		}
		
		if(month == 11 && day<=7 && sun.equals("Sun") &&hour<2){
			return 0;
		}
		
		return -3600;
	}
	
	public static String parseToDateCode(LocalTime lc){
		
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy_MM_dd HH:mm z");
		date_format.setTimeZone(TimeZone.getTimeZone("GMT"));

		String gmt_str = date_format.format(lc);
		String[] strs = gmt_str.split(" ");
		
		return strs[0];
		
	}
	
	public static LocalTime parseStringToLocalTime(String lc_str){
		
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy MMM dd HH:mm z",Locale.ENGLISH);
		LocalTime parsed_lc = new LocalTime();
		
		try {
			
			Date da = date_format.parse(lc_str);
			parsed_lc.setTime(da.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return parsed_lc;
	}
	
}
