/**
 * This is the class to generalize Airports
 * 
 * @author Xu Han 
 * 
 */


package cs509.hobbits.search;

public class Airport {
	final private float PERCENTAGE = 0.1f; 
	
	
	private String code;
	private String name;
	private float latitude;
	private float longitude;
	
	Airport(){
		
		code = "";
		name = "";
		latitude = 0.0f;
		longitude = 0.0f;
	
	}
	
	/*
	 * Setters and getters 
	 */
	public void setCode(String _code, String _name){
		
		code = _code;
		name = _name;
	
	}

	public void setLocation(float lat, float longi ){
		
		latitude = lat;
		longitude = longi;	
		
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
	

}
