/**
 * This is the class to generalize Airplanes
 * 
 * @author Xu Han 
 * 
 */
package cs509.hobbits.search;

public class Airplane {
	
	private String manufacturer;
	private String model;
	private int first_class_seats;
	private int coach_seats;
	
	public Airplane(){
		
		manufacturer = "";
		model = "";
	
	}
	
	
	/*
	 * Setters of some attributes
	 */
	public void setModel(String _manufacturer, String _model){
		
		manufacturer = _manufacturer;
		model = _model;
	
	}

	public void setSeats(int first_class, int coach ){
		
		first_class_seats = first_class;
		coach_seats = coach;	
		
	}
	
	public String getManufacturer(){
		return manufacturer;
		
	}
	
	public String getModel(){
		return model;
		
	}
	
	public int getSeatNumber(boolean _first_class){
		
		if(_first_class){
			return first_class_seats;
		}else{
			return coach_seats;
		}
		
	}
}
