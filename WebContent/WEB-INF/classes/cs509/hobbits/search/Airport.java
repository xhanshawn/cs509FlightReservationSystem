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

/**
 * @author		Xu Han		<xhan@wpi.edu>
 * @version		0.5	
 * @since		2015-04-08	
 * 
 * This is the class to generalize Airports, set attributes of airport and
 * do some logical judgment based on the inner attributes
 * 
 */


public class Airport {
	
	/* *
	 * This float is used for checking if the an airport is a possible lay over airport
	 * It is the distance ratio
	 */
	final private float PERCENTAGE = 0.30f; 
	
	/* *
	 * Attributes name and code for an airport
	 */
	private String code;
	private String name;
	
	/* *
	 * Location attributes for an airport
	 */
	private float latitude;
	private float longitude;

	/* *
	 * Time zone attributes for an airport
	 */
	private String time_zone;
	private long offset;
	private int dst;
	private boolean timezone_retrieved;
	
	public Airport(){
		
		code =null;
		name = null;
		latitude = 0.0f;
		longitude = 0.0f;
		offset = 0l;
		dst = 0;
		timezone_retrieved = false;
		
	}
	
	public void setCodeAndName(String _code, String _name){
		
		//singleton
		if(code==null) code = _code;
		if(name==null) name = _name;
	
	}

	public void setLocation(float lat, float longi ){
		
		latitude = lat;
		longitude = longi;	
		
	}
	
	

	/* *
	 * This setter is only called when initialization and update airport lists 
	 * and time zone information. It is used for update time zone information
	 * @param obj, it is the JSONObject retrieved from the API
	 * 
	 */
	public void setTimeZone(){
		
		JSONObject obj = retrieveTimeZone();
		
		try {
			
			time_zone = obj.getString("abbreviation");
			offset = obj.getLong("gmtOffset");
			dst =  obj.getInt("dst");
			timezone_retrieved = true;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/* *
	 * This method is used to retrieve Time Zone informations by 
	 * using HTTP GETfrom API provided by timezoneDB.
	 * 
	 * The default time is "2015 05 10 12:00 GMT". DST is assumed
	 */
	private JSONObject retrieveTimeZone(){
		
		//This part is for parsing sample date in string to an instance of date
		String sample_date = "2015 05 10 12:00 GMT";
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy MM dd HH:mm z",Locale.ENGLISH);
		
		Date day = null;
		
		try {
			
			day = date_format.parse(sample_date);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//This part is for generating url string
		String location = this.getLatitude() + "&lng=" + this.getLongitude();
		
		JSONObject obj = null;
		
		String url = "http://api.timezonedb.com/?format=json&lat="
		+ location +"&key=NMBW9G7ILB6H&timestamp=" + day.getTime()/1000;
		
		try {
			
			//Try to retrieve data by using HTTP GET
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
						
						// handle the case if API is has error like over query
						throw new JSONException(obj.getString("status"));
					}
			}
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj;
	}
	
	
	/* *
	 * if time zone is retrieved return offset directly
	 */
	public long getOffset(){
		
		if(timezone_retrieved) return offset;
		else{ 
			
			setTimeZone();
			return offset;
		}	
	}
	
	public boolean dstIsUsed(){
		
		if(timezone_retrieved) return (dst==1) ? true : false;
		else{ 
			
			setTimeZone();
			return (dst==1) ? true : false;
		}	
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
	
	public String getAirportName(){
		
		return name;
	}

	/* *
	 * This method is to get the main direction of depart airport and arrival airport.
	 * It is either in the longitude direction or in the latitude direction.
	 * If longitude difference is larger, it is the longitude direction
	 * if latitude difference is larger, it is the latitude direction
	 */
	public boolean getDirection(Airport _arrival){
		
		float lat_dis = Math.abs( this.getLatitude() - _arrival.getLatitude());
		float longi_dis = Math.abs( this.getLongitude() - _arrival.getLongitude());
		
		if(lat_dis>longi_dis){
			
			return false;	
		}else{
			
			return true;
		}

	}
	
	/* *
	 * This method is to check if this airport is a possible lay over airport between
	 * _depart airport and _arrival airport. 
	 * 
	 * First, if the distance between this airport and _depart airport is larger than 
	 * the distance between _depart airport and _arrival airport, then it will not be
	 * viewed as a possible lay over airport.
	 * 
	 * Second, if the main direction distance(Longitude or Latitude) between this airport
	 * and _depart airport is larger than a ratio of the main direction difference between
	 * _depart and _arrival airport, it will be viewed as a possible lay over airport.
	 * 
	 * The ratio is the parameter PERCENTAGE. I will be changed be the trade-off between
	 * Time efficiency and number of results or the requirements of user. 
	 */
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
