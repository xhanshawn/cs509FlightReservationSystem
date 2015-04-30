package cs509.hobbits.search;

/**
 * This is the class to retrieve date from server
 * 
 * @author Xu Han 
 * 
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DataRetriever {
	
	final private static String DEPART = "departing";
	final private static String ARRIVAL = "arriving";
	
	final private static int AIRPORTS = 1;
	final private static int AIRPLANES = 2;
	final private static int DEP_FLIGHT = 3;
	final private static int ARR_FLIGHT = 4;

	final private boolean FIRST = true;
	final private boolean COACH = false;
	
	
	private static Map<String, Airplane> Airplane_list = new HashMap<String, Airplane>();
	private static Map<String, Airport> Airport_list = new HashMap<String, Airport>();

	private final static String mUrlBase = 
			"http://cs509.cs.wpi.edu:8181/CS509.server/ReservationSystem?team=Team08";
	
	private static String query_airport = "";
	private static String query_date = "";
	
	public DataRetriever(){
		
		updateAirportList();
		
	}
	
	
	
	private static void updateAirportList(){
		
		if(Airport_list.isEmpty()){
			
			ArrayList <Airport> airports = new ArrayList<Airport>();
			airports = getAirportList();
			
			for(int i=0; i< airports.size(); i++){
				Airport_list.put(airports.get(i).getCode(), airports.get(i));
			}
		}
	}
	
	public static void updateLists(){
		
		Airport_list.clear();
		Airplane_list.clear();
		updateAirportList();
		setTimeZone();
		
	}
	
	public static void setTimeZone(){
		
		if(!Airport_list.isEmpty()){
		
			for(Map.Entry<String, Airport> entry : Airport_list.entrySet()){
				
				entry.getValue().setTimeZone();

			}
		}
	}
	
	
	
	/*
	 * This method is used to retrieve the data stream from server
	 */
	private static InputStream getStream ( int list_type){
		
		URL url = null;
		HttpURLConnection connection;
	
		try{
			
			switch(list_type){
				
			case AIRPORTS:
				url = new URL(mUrlBase + QueryFactory.getAirports());
				break;
			case AIRPLANES:
				url = new URL(mUrlBase + QueryFactory.getAirplanes());
				break;
			case DEP_FLIGHT:
				
				url = new URL(mUrlBase + QueryFactory.getFlights(query_airport, query_date, DEPART));
				break;
			case ARR_FLIGHT:
				url = new URL(mUrlBase + QueryFactory.getFlights(query_airport, query_date, ARRIVAL));
				break;
			default:
				break;
			}
			
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			int responseCode = connection.getResponseCode();
			
			if ((responseCode>=200) && (responseCode <=299)){
				return connection.getInputStream();
			}
			
		
		} catch(IOException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return null; 
	}
	
	
	
	/*
	 * This method is to get airport list from the server 
	 */
	public static ArrayList<Airport> getAirportList() {
		
		InputStream input = getStream(AIRPORTS);
		
		Document document = null;
		
		try {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Reader reader = new InputStreamReader(input,"UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			document = builder.parse(is);
			document.normalize();
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		
		Element root = document.getDocumentElement();
		NodeList list = root.getElementsByTagName("Airport");
		
		ArrayList<Airport> airport_List = new ArrayList<Airport>();
		for (int i = 0; i < list.getLength() ; i++){
			
			Airport airport = new Airport();
			
			String Code = ((Element)list.item(i)).getAttribute("Code");
			String Name = ((Element)list.item(i)).getAttribute("Name");
			airport.setCodeAndName(Code, Name);
			
			float latitude = Float.parseFloat(list.item(i).getFirstChild().getTextContent());
			float longitude = Float.parseFloat(list.item(i).getLastChild().getTextContent());
			airport.setLocation(latitude, longitude);
			
			airport_List.add(airport);
			
		}
		
		return airport_List;
	}
	
	
	
	/*
	 * This method is to get Airplane list from the server
	 */
	
	public static ArrayList<Airplane> getAirplaneList(){
	
		InputStream input = getStream(AIRPLANES);
	
		Document document = null;
		Element root=null;
		
		try {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Reader reader = new InputStreamReader(input,"UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			
			document = builder.parse(is);
			document.normalize();
			root = document.getDocumentElement();
		
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		NodeList list = root.getElementsByTagName("Airplane");
		
		ArrayList<Airplane> airplane_List = new ArrayList<Airplane>();
		
		for (int i = 0; i < list.getLength() ; i++){
			
			Airplane airplane = new Airplane();
			
			String Manufacturer = ((Element)list.item(i)).getAttribute("Manufacturer");
			String Model = ((Element)list.item(i)).getAttribute("Model");;

			
			int first_class = Integer.parseInt(list.item(i).getFirstChild().getTextContent());
			int coach = Integer.parseInt(list.item(i).getLastChild().getTextContent());
			
			airplane.setSeats(first_class, coach);
			airplane.setModel(Manufacturer, Model);
			
			airplane_List.add(airplane);
			
		}
		
		return airplane_List;
		
	}
	
	
	//This part is used to get Flight list from server
	public ArrayList<Flight> getFlightList(String code, String day, boolean depart){
	
		int list_type = 0;
	
		query_airport = code ;
		query_date = day ;
		
		if(depart){
			list_type = DEP_FLIGHT;
		}else{
			list_type = ARR_FLIGHT;
		}
		
		ArrayList<Flight> flight_list = new ArrayList<Flight> ();
		InputStream input = getStream(list_type);
	
		Document document = null;
	
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Reader reader = new InputStreamReader(input,"UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
	    
			document = builder.parse(is);
			document.normalize();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	
		Element root = document.getDocumentElement();
		NodeList list = root.getElementsByTagName("Flight");

		for (int i = 0; i < list.getLength() ; i++){	
		
			String airplane = ((Element)list.item(i)).getAttribute("Airplane");
			int Time = Integer.parseInt(((Element)list.item(i)).getAttribute("FlightTime"));
			int Number = Integer.parseInt(((Element)list.item(i)).getAttribute("Number"));
			
			NodeList detail = list.item(i).getChildNodes();
		
		
			String dep_code = detail.item(0).getFirstChild().getTextContent();
			String dep_time = detail.item(0).getLastChild().getTextContent();
		
		
			String arr_code = detail.item(1).getFirstChild().getTextContent();
			String arr_time = detail.item(1).getLastChild().getTextContent();
		
		
			String first_class = ((Element)detail.item(2).getFirstChild()).getAttribute("Price");
			int first_class_seat = Integer.parseInt(detail.item(2).getFirstChild().getTextContent());
		
			String coach = ((Element)detail.item(2).getLastChild()).getAttribute("Price");
			int coach_seat = Integer.parseInt(detail.item(2).getLastChild().getTextContent());
		
			
			Flight flight = new Flight();
		
			flight.setPlane(airplane);
			flight.setSeats(first_class_seat,  coach_seat);
			flight.setNumber(Number);
			
			/*Reason for query instances of airport is because it will not cost a lot
			 *for a hash table. So this way can make codes concise and only use the airport
			 *list was retrieved
			 */
			flight.setAirports(Airport_list.get(dep_code), Airport_list.get(arr_code));
			flight.setFlightTime(Time);
			flight.setDATime(dep_time, arr_time);
			flight.setPrice(first_class, coach);
			
			if(!flight.hasSeat()) continue;
		
			flight_list.add(flight);
		
		}
	
	
		return flight_list;
	}		
	
}

