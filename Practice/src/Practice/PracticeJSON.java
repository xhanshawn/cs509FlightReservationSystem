package Practice;

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

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

public class PracticeJSON {
	public static void main (String[] args){
		PracticeJSON pracJSON= new PracticeJSON();
		int i=1;
		while(i>0){
			pracJSON.practice();
			i--;
		}
	}
	
	
	private void practice(){
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy MMM dd HH:mm z",Locale.ENGLISH);
		
		String location = "40.77,-73.77";
		
		
		String url = "https://maps.googleapis.com/maps/api/timezone/json?location="
		+ location +"&timestamp=";
		try {
			Date date = 
//					new Date();
					(Date) date_format.parse("2015 Feb 10 10:47 GMT");
			System.out.println(date.getTime()/1000);
			System.out.println(date.toString());

			url += "" + date.getTime()/1000;
			URL u = new URL(url);
			u= new URL("https://maps.googleapis.com/maps/api/timezone/json?location=39.053528,-84.66307&timestamp=1423565220");
			
			URL u2 = new URL("http://api.timezonedb.com/?lat=40.7833&lng=-73.75&format=json&key=NMBW9G7ILB6H&timestamp=1418208420");
			HttpsURLConnection connection = (HttpsURLConnection) u.openConnection();
//			HttpURLConnection connection = (HttpURLConnection) u2.openConnection();

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
				
				JSONObject obj = new JSONObject(str.toString());
//				System.out.println(obj.get("status"));
//				System.out.println(obj.get("abbreviation"));
				System.out.println(obj.getString("timeZoneName"));
				System.out.println(obj.getLong("dstOffset"));

				
//				String timezone = obj.getString("zoneName");
//				String[] sArray = timezone.split(" ");
//				int length = 0;
//				String acronym = ""; 
//				while(length<sArray.length){
//					acronym += sArray[length].charAt(0);
//					length++;
//				}
//				long offset =
//						(obj.getInt("dst")-1)*3600 + 
//						obj.getLong("gmtOffset");
				
				Date da2 = new Date ();
				da2.setTime(date.getTime() + (obj.getLong("dstOffset")+obj.getLong("rawOffset"))*1000);
//				date.setMonth(5);
//				System.out.println(acronym);
				
				System.out.println(da2.toGMTString());
				System.out.println(date.toString());

				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
