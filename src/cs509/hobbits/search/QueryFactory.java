/**
 * This is the class to add string at the end of URL to get flight information list from the server
 *
 * @author Xu Han 
 * 
 */


package cs509.hobbits.search;


public class QueryFactory {

	public static String getAirports(){
		return "&action=list&list_type=airports";
	}
	
	/*
	 * This is the static method to get airplanes information from the server
	 */
	public static String getAirplanes(){
	
		return "&action=list&list_type=airplanes";
	}

	/*
	 * This is the static method to get flight information from the server
	 */
	public static String getFlights(String airport, String date, String type){
	
		return "&action=list&list_type="+type+"&airport="+airport+"&day="+date;
	}

	public static String lock (String ticketAgency) {
		return "team=" + ticketAgency + "&action=lockDB";
	}
	
	public static String unlock (String ticketAgency) {
		return "team=" + ticketAgency + "&action=unlockDB";
	}

	public static String lock () {
		return "team=" + "Team08" + "&action=lockDB";
	}
	
	public static String unlock () {
		return "team=" + "Team08" + "&action=unlockDB";
	}
	public static String reserve (String number, String seating) {
		return "team="+"Team08"+"&action=buyTickets"+"&flightData="+"<Flights>"+"<Flight number='" +number+"' seating='"+seating+"' /></Flights>";
		}
}
