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
	
	
	public boolean getDirection(Airport _arrival){
		float lat_dis = Math.abs( this.getLatitude() - _arrival.getLatitude());
		float longi_dis = Math.abs( this.getLongitude() - _arrival.getLongitude());
		if(lat_dis>=longi_dis){
			return false;	
		}else{
			return true;
		}

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
