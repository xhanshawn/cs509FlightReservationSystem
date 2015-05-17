package Practice;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;




public class Prac_arraylist_sort {
	public static void main (String[] args){
		Prac_arraylist_sort sort = new Prac_arraylist_sort();
		
		sort.practice();
		
	}
	private void practice(){
		
		ArrayList<Airport> list = new ArrayList<Airport> ();
		
		GetXML getxml = new GetXML();
		
		try {
			list = getxml.getAirportList();
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
		
		Collections.sort(list, new  Comparator<Airport>(){

			
			public int compare(Airport o1, Airport o2) {
				// TODO Auto-generated method stub
				
				if (o1.getLongitude()*1000000 !=o2.getLongitude()*1000000){
					return (int) (o1.getLongitude()*1000000-o2.getLongitude()*1000000);
				}else{
				return 0;
				}
			}});
		
		for(int i=0; i<list.size(); i++){
			System.out.println("Code: " + list.get(i).getCode() + " Price: " + list.get(i).getLongitude());
		}
		System.out.print(list.size());
	}
}

class GetXML {
	private final String mUrlBase = 
			"http://cs509.cs.wpi.edu:8181/CS509.server/ReservationSystem?team=Team08";
	private final boolean test = true;
	
	private String query_airport = "";
	
	private String query_date = "";
	
	private InputStream getStream ( String list_type){
		
		URL url = null;
		HttpURLConnection connection;
	
		
		
		try{
			
			switch(list_type){
				
			case "Airports":
				url = new URL(mUrlBase + QueryFactory.getAirports());
				break;
			case "Airplanes":
				url = new URL(mUrlBase + QueryFactory.getAirplanes());
				break;
			case "Depart":
				
				url = new URL(mUrlBase + QueryFactory.getDeparting(query_airport, query_date));
				break;
			case "Arrival":
				url = new URL(mUrlBase + QueryFactory.getDeparting(query_airport, query_date));
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
	
	
	
	public ArrayList<Airport> getAirportList() throws SAXException, IOException, ParserConfigurationException{
		
		InputStream input = getStream("Airports");
		
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
			String Name = ((Element)list.item(i)).getAttribute("Name");;
			airport.setCode(Code, Name);
			
			float latitude = Float.parseFloat(list.item(i).getFirstChild().getTextContent());
			float longitude = Float.parseFloat(list.item(i).getLastChild().getTextContent());
			airport.setLocation(latitude, longitude);
			
			airport_List.add(airport);
			
		}
		
		return airport_List;
	}
	
	
public ArrayList<Airplane> getAirplaneList(){
	
		InputStream input = getStream("Airports");
	
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
			airplane.setModel(Manufacturer, Model);
			
			int first_class = Integer.parseInt(list.item(i).getFirstChild().getTextContent());
			int coach = Integer.parseInt(list.item(i).getLastChild().getTextContent());
			airplane.setSeats(first_class, coach);
			
			airplane_List.add(airplane);
			
		}
		
		return airplane_List;
	}
	
public ArrayList<Flight> getFlightList(String code, String day, boolean depart){
	
	String list_type = "";
	
	query_airport = code ;
	query_date = day ;
	
	if(depart){
		list_type = "Depart";
	}else{
		list_type = "Arrival";
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
		flight.setNumber(Number);
		flight.setCode(dep_code, arr_code);
		flight.setFlightTime(Time);
		flight.setLocalTime(dep_time, arr_time);
		flight.setPrice(first_class, coach);
		flight.setSeats(first_class_seat,  coach_seat);
		
		flight_list.add(flight);
		
	}
	
	
	return flight_list;
}

	
	
	
	
}





class Airport {
	
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
	
	
	public boolean isBetween(Airport port1, Airport port2){
		
		float lat_dis = Math.abs( port1.getLatitude()-port2.getLatitude());
		float longi_dis = Math.abs( port1.getLongitude()-port2.getLongitude());
		
		if(lat_dis>=longi_dis){
			if (latitude>Math.min(port1.getLatitude(), port2.getLatitude())
				&&latitude<	Math.max(port1.getLatitude(), port2.getLatitude())){
				return true;}	
		}else{
			if (longitude>Math.min(port1.getLongitude(), port2.getLongitude())
					&&longitude<Math.max(port1.getLongitude(), port2.getLongitude())){
					return true;}
		}
		
		return false;
	}
}
	

class Airplane {
	
	private String manufacturer;
	private String model;
	private int first_class_seats;
	private int coach_seats;
	
	Airplane(){
		
		manufacturer = "";
		model = "";
	
	}
	
	public void setModel(String _manufacturer, String _model){
		
		manufacturer = _manufacturer;
		model = _model;
	
	}

	public void setSeats(int first_class, int coach ){
		
		first_class_seats = first_class;
		coach_seats = coach;	
		
	}


}
	
class Flight{
	private String airplane;
	private int flight_time;
	private int number;
	
	private String dep_code;
	private String arr_code;
	
	private LocalTime dep_time;
	private LocalTime arr_time;
	
	private float fir_price;
	private int fir_seat;
	private float coa_price;
	private int coa_seat;
	
	
	Flight(){
		
		airplane = "";
		dep_code = "";
		dep_time = new LocalTime();
		arr_code = "";
		arr_time = new LocalTime();
		fir_price = 0.0f;
		coa_price = 0.0f;
	}
	
	public void setPlane(String _airplane){
		airplane=_airplane;
	}
	
	public void setNumber(int num){
		number = num;
	}
	
	public void setCode(String _dep_code, String _arr_code){
		dep_code = _dep_code;
		arr_code = _arr_code;
	}
	
	public void setFlightTime(int _flight_time){
		flight_time = _flight_time;
	}
	
	public void setLocalTime(String _dep_time, String _arr_time){
		//Time Format 2015(0) May(1) 10(2) 01:49(3) EDT(4)
		String[] _dep = _dep_time.split(" ");
		String[] _arr = _arr_time.split(" ");
		
		
		
		dep_time.setDate(_dep[1], Integer.parseInt(_dep[2]), Integer.parseInt(_dep[0]));
		dep_time.setTime(_dep[3]);
		
		arr_time.setDate(_arr[1], Integer.parseInt(_arr[2]), Integer.parseInt(_arr[0]));
		arr_time.setTime(_arr[3]);
		
		
		dep_time.setTimeZone(_dep[4]);
		dep_time.setTimeZone(_arr[4]);
	}
	
	public void setPrice(String _fir_price, String _coa_price){
		char[] fir = new char[_fir_price.length()+1];
		char[] coa = new char[_coa_price.length()+1];
		
		fir = _fir_price.toCharArray();
		coa = _coa_price.toCharArray();
		
		int thousand=0;
		for (int m=0; m<_fir_price.length()-1; m++){
			
			if(m+1+thousand<_fir_price.length()){
			if(fir[m+1]==','){
				thousand++;
			}
			
				fir[m] = fir[m+1+thousand];
			}
		}
		thousand=0;
		for (int n=0; n<_coa_price.length(); n++){
			
			if(n+thousand+1<_coa_price.length()){
			if(coa[n+1]==','){
				thousand++;
			}
			
			coa[n] = coa[n+1+thousand];
			}
		}
		
		fir_price = Float.parseFloat(String.valueOf(fir));
		coa_price = Float.parseFloat(String.valueOf(coa));
	}
	
	public void setSeats(int _first, int _coach){
		
		fir_seat = _first;
		coa_seat = _coach;
		
	}
	
	public String getCode(boolean dep){
		if (dep){
			return dep_code;
		}else{
			return arr_code;
		}
		
	}
	
	public int getFlightTime(){
		return flight_time;
	}
	
	public float getPrice(boolean first_class){
		if (first_class==true){
			return fir_price;
		}
		else return coa_price;
	}
	
	
	public Date getLocalTime(boolean depart){
		
		
		
		if (depart){
			return dep_time.getTime();
		}else{
			return arr_time.getTime();
		}
	}
	
	public String getDateCode(boolean depart){
		if (depart){
			return dep_time.getDateCode();
		}else{
			return arr_time.getDateCode();
		}
	}
	
	
	public Airport getAirport(boolean depart){
		String code = "";
		if(depart==true){
			code = dep_code;
		}else{
			code = arr_code;
		}
		GetXML getXML = new GetXML();
		ArrayList<Airport> list = new ArrayList<Airport>();
		
		try {
			list = getXML.getAirportList();
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
			
		Iterator<Airport> ite = list.iterator();
		
		while(ite.hasNext()){
			Airport temp = ite.next();
			if(code.equals(temp.getCode())){
				return temp;
			}
		}
		
		return null;
	}
	
	
	public void forTest(){
		System.out.println("");
		System.out.println("Number: " + number + " Time: " + flight_time + " Departure Airport: "
				+ dep_code + " Arrival Airport: " + arr_code );
		System.out.println("Dep_time: " + dep_time.getTime().getHours()+":"+dep_time.getTime().getMinutes()
				+ " Arr_time: " + arr_time.getTime().getHours() + ":" + arr_time.getTime().getMinutes());
		System.out.println("FirstClass: $" + fir_price + " left: "+fir_seat +
				" Coach: $" + coa_price + " left: "+ coa_seat );
		System.out.println("");
	}
}
	

class LocalTime {
	
	private int year;
	private int month;
	private int day;
	private Date time;
	private String time_zone;
	
	LocalTime(){

		time_zone = "";
		time = new Date();
	}
	
	
	public boolean setDate(String _month, int _day, int _year){
		
		if((8<=_day&&_day<=17)
			&&(_year==2015)){
			

			SimpleDateFormat format = new SimpleDateFormat("MMM",Locale.ENGLISH);
			try{
				Date d = format.parse(_month);
				
				
				month = d.getMonth() + 1;
			}catch (ParseException e){
				e.printStackTrace();
			}
			
			
			day = _day;
			year = _year;
			return true;
		}
	
		return false;
	}
	
	public void setTime(String _time){
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		
		try {
			time = format.parse(_time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setTimeZone(String _time_zone){
		time_zone = _time_zone;
	}
	
	public Date getTime(){
		return time;
	}
	
	public String getDateCode(){
		String date_code = "";
		
		if(month<10){
			
			date_code = ""+ year + "_0" +month + "_" + day;
			//Change month to 0X
		
		}else{
			date_code = ""+ year + "_" +month + "_" + day;
			
		}
		
		return date_code;
			
	}
	
	
}


class FlightPlan{
	final private static boolean FIRST = true;
	final private static boolean COACH = false;
	
	private ArrayList<Flight> plan;
	private int stopover;
	private float fir_price;
	private float coa_price;
	
	public FlightPlan(Flight initial_flight){
		plan = new ArrayList<Flight>();
		plan.add(initial_flight);
		stopover=0;
		fir_price = initial_flight.getPrice(FIRST);
		coa_price = initial_flight.getPrice(COACH);
	}
	
	public boolean addFlight(Flight new_flight){
		
		if (new_flight==null)   return false;
		
		plan.add(new_flight);
		stopover++;
		
		fir_price += new_flight.getPrice(FIRST);
		coa_price += new_flight.getPrice(COACH);
		
		return true;
	}
	
	public ArrayList<Flight> getPlan(){
		
		return plan;
	}

	public float getPrice(boolean first_class){
		if(first_class){
			return fir_price;
		}else{
			return coa_price;
		}
	}
}
	
class QueryFactory{
	
	/*
	 * Static method to get Airports information from the server
	 */
	
	public static String getAirports(){
		return "&action=list&list_type=airports";
	}
	
	/*
	 * Static method to get airplanes information from the server
	 */
	public static String getAirplanes(){
		
		return "&action=list&list_type=airplanes";
	}
	
	public static String getDeparting(String airport, String date){
		
		return "&action=list&list_type=departing&airport="+airport+"&day="+date;
	}
}


	
	
	
	
	


