package Practice;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

public class PracticeLocalTime {
	
	public static void main(String[] args){
		PracticeLocalTime p = new PracticeLocalTime();
		p.practice();
	}

	private void practice(){
	
		LocalDateTime lc = LocalDateTime.now();
		OffsetDateTime odt = OffsetDateTime.now();
		
		Date da = new Date();
		
		SimpleDateFormat date_format = new SimpleDateFormat("E yyyy MM dd HH:mm z");
		date_format.setTimeZone(TimeZone.getTimeZone("GMT"));
		System.out.println(date_format.format(da));
		
		System.out.println(da.toGMTString());
		
		String[] strs = date_format.format(da).split(" ");
		
		System.out.println(Integer.parseInt(strs[4].substring(0, 2))+"") ;
		
		System.out.println(lc.getHour());

		System.out.println(lc.getHour());

		System.out.println(odt.getOffset());
		
	}


}