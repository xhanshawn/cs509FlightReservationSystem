package cs509.hobbits.search;

/**
 * @author		Xu Han			<xhan@wpi.edu>
 * @version		0.5	
 * @since		2015-04-08	
 * 
 * This is the class to generalize Airplanes and set attributes of airplane
 * 
 */

public class Airplane {
	
	
	/* *
	 * a string value for manufacturer of an airplane
	 */
	private String manufacturer;
	 
	/* *
	 * a string value for model of an airplane
	 */
	private String model;
	
	
	/* *
	 * integer variable for first class seats number and coach seats number
	 */
	private int first_class_seats;
	private int coach_seats;
	
	public Airplane(){
		
		manufacturer = null;
		model = null;
		
		first_class_seats = 0;
		coach_seats = 0;
		
	}
	
	
	/* *
	 * Setters of attribute manufacturer and model
	 */
	public void setModel(String _manufacturer, String _model){
		
		//singleton pattern
		if(manufacturer==null) manufacturer = _manufacturer;
		if(model==null) model = _model;;
	
	}

	/* *
	 * Setters of attribute first_class_seats and coach_seats
	 */
	public void setSeats(int first_class, int coach ){
		
		first_class_seats = first_class;
		coach_seats = coach;	
		
	}
	
	
	public String getManufacturer(){
		
		if(manufacturer==null) return "";
		return manufacturer;
		
	}
	
	public String getModel(){
		
		if(model==null) return "";
		return model;
		
	}
	
	/* *
	 * Getters of attribute first_class_seats and coach_seats
	 * @param _first_class, true for first class; false for coach seat
	 * 
	 */
	public int getSeatNumber(boolean _first_class){
		
		if(_first_class){
			
			return first_class_seats;
			
		}else{
			
			return coach_seats;
			
		}
	}
}