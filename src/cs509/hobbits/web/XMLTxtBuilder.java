package cs509.hobbits.web;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import cs509.hobbits.search.Airplane;
import cs509.hobbits.search.Airport;
import cs509.hobbits.search.Flight;
import cs509.hobbits.search.FlightPlan;

public class XMLTxtBuilder {
	
	private static Document mDoc = null;

	
	public static String buildPlanXML(ArrayList<FlightPlan> results, boolean round_trip){
	
		
		Element mRootNode = null;
		
		try{
  			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
  			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
  			
  			mDoc = docBuilder.newDocument(); 
  			mDoc.setXmlVersion("1.0");
  			mDoc.setXmlStandalone(false);
  			mRootNode = mDoc.createElement("FlightPlans");
  			mDoc.appendChild(mRootNode);
  		}catch(ParserConfigurationException e){
  			e.printStackTrace();
  			mDoc = null;
  			
  		}
  		
  		
  		Element elementFP;
  		
  		Attr attr;

  		for (int i=0; i<results.size(); i++){
  			
  			

  			
  			elementFP = mDoc.createElement("FlightPlan");
  			mRootNode.appendChild(elementFP);
  			
  			attr = mDoc.createAttribute("FirstClass");
  			attr.setValue(results.get(i).getPrice(true)+"");
  			elementFP.setAttributeNode(attr);
  			
  			attr = mDoc.createAttribute("Coach");
  			attr.setValue(results.get(i).getPrice(false)+"");
  			elementFP.setAttributeNode(attr);
  			
  			attr = mDoc.createAttribute("Stopover");
  			attr.setValue(results.get(i).getStopOver()+"");
  			elementFP.setAttributeNode(attr);
  			
  			
  			Element elementDepart = null;
  			Element elementReturn = null;
  			
  			if(results.get(i).isRoundTrip()&&round_trip){
  				
  				elementDepart = mDoc.createElement("Depart");
  				elementFP.appendChild(elementDepart);
  				
  				appendChildren(elementDepart,buildPlan(results.get(i).getDepartPlan()));
  				
  				elementReturn = mDoc.createElement("Return");
  				elementFP.appendChild(elementReturn);
  			
  				appendChildren(elementReturn, buildPlan(results.get(i).getReturnPlan()));

  			}else{
  				appendChildren(elementFP, buildPlan(results.get(i).getPlan()));
  			}
  			
  			
  			
  			
  		}
  		
  		try{
  			DOMSource domSource = new DOMSource(mDoc);
  			StringWriter writer = new StringWriter();
  			StreamResult result = new StreamResult(writer);
  			TransformerFactory tf = TransformerFactory.newInstance();
  			Transformer transformer = tf.newTransformer();
  			transformer.transform(domSource, result);

  			
  			return writer.toString();
  			
  		}catch(TransformerException ex){
  			ex.printStackTrace();
  			return null;
  		}
  		
		
		
		
		
		
		
	}
	
	
	private static void appendChildren(Element parent, ArrayList<Element> elements){
		
		for(int i=0; i<elements.size(); i++){
			parent.appendChild(elements.get(i));
		}
		
	}
	
	
	private static ArrayList<Element> buildPlan(ArrayList<Flight> _plan){
		Attr attr;
  		Text text;
  		
		ArrayList<Flight> flights = _plan;
		ArrayList<Element> elements = new ArrayList<Element> ();
			for(int j=0; j<flights.size(); j++){
				
				Element elementF = mDoc.createElement("Flight");
				
				attr = mDoc.createAttribute("Airplane");
	  			attr.setValue(flights.get(j).getAirplane().getModel()+"");
	  			elementF.setAttributeNode(attr);
	  			
	  			attr = mDoc.createAttribute("FlightTime");
	  			attr.setValue(flights.get(j).getFlightTime()+"");
	  			elementF.setAttributeNode(attr);
	  			
	  			attr = mDoc.createAttribute("Number");
	  			attr.setValue(flights.get(j).getNumber()+"");
	  			elementF.setAttributeNode(attr);
	  			
	  			//Departure
	  			Element elementD = mDoc.createElement("Departure");
	  			elementF.appendChild(elementD);
	  			
	  			Element Code = mDoc.createElement("Code");
	  			text = mDoc.createTextNode(flights.get(j).getCode(true));
	  			Code.appendChild(text);
	  			elementD.appendChild(Code);
	  			
	  			Element date_code = mDoc.createElement("Date");
	  			text = mDoc.createTextNode(flights.get(j).getDateCode(true));
  			date_code.appendChild(text);
  			elementD.appendChild(date_code);
  			
  			Element local_time = mDoc.createElement("LocalTime");
  			text = mDoc.createTextNode(flights.get(j).getLocalTimeString(true));
  			local_time.appendChild(text);
  			elementD.appendChild(local_time);
  			
  			//Arrival
  			Element elementA = mDoc.createElement("Arrival");
	  		elementF.appendChild(elementA);
	  			
	  		Code = mDoc.createElement("Code");
	  		text = mDoc.createTextNode(flights.get(j).getCode(false));
	  		Code.appendChild(text);
	  		elementA.appendChild(Code);
	  			
	  		date_code = mDoc.createElement("Date");
	  		text = mDoc.createTextNode(flights.get(j).getDateCode(false));
  			date_code.appendChild(text);
  			elementA.appendChild(date_code);
  			
  			local_time = mDoc.createElement("LocalTime");
  			text = mDoc.createTextNode(flights.get(j).getLocalTimeString(false));
  			local_time.appendChild(text);
  			elementA.appendChild(local_time);
  			
  			//seating
  			Element elementS = mDoc.createElement("Seating");
	  		elementF.appendChild(elementS);
	  			
			
	  		Element First = mDoc.createElement("FirstClass");
	  		text = mDoc.createTextNode(flights.get(j).getSeat(true)+"");
	  		First.appendChild(text);
	  		elementS.appendChild(First);
	  		attr = mDoc.createAttribute("Price");
  			attr.setValue("$"+flights.get(j).getPrice(true));
  			First.setAttributeNode(attr);
	  			
  			Element Coach = mDoc.createElement("Coach");
  			text = mDoc.createTextNode(flights.get(j).getSeat(false)+"");
  			Coach.appendChild(text);
  			elementS.appendChild(Coach);
  			attr = mDoc.createAttribute("Price");
  			attr.setValue("$"+flights.get(j).getPrice(false));
  			Coach.setAttributeNode(attr);
  			
  			
  			elements.add(elementF);
			}
			
		return elements;
	}
	
	
	public static String buildAirportsXML(ArrayList<Airport> airports){
		
		Element mRootNode = null;
		
		try{
  			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
  			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
  			
  			mDoc = docBuilder.newDocument(); 
  			mDoc.setXmlVersion("1.0");
  			mDoc.setXmlStandalone(false);
  			mRootNode = mDoc.createElement("Airports");
  			mDoc.appendChild(mRootNode);
  		}catch(ParserConfigurationException e){
  			e.printStackTrace();
  			mDoc = null;
  			
  		}
  		
  		
  		Element elementA;
  		
  		Attr attr;

  		for (int i=0; i<airports.size(); i++){
  			
  			
  			elementA = mDoc.createElement("Airport");
  			mRootNode.appendChild(elementA);
  			
  			attr = mDoc.createAttribute("Code");
  			attr.setValue(airports.get(i).getCode());
  			elementA.setAttributeNode(attr);
  			
  			attr = mDoc.createAttribute("Name");
  			attr.setValue(airports.get(i).getAirportName());
  			elementA.setAttributeNode(attr);
  			
  				
  			Element elementLat = mDoc.createElement("Latitude");
  			Text text = mDoc.createTextNode(airports.get(i).getLatitude()+"");;
  			elementLat.appendChild(text);
  			elementA.appendChild(elementLat);	
  				
  			Element elementLong = mDoc.createElement("Longtitude");
  			text = mDoc.createTextNode(airports.get(i).getLongitude()+"");;
  			elementLong.appendChild(text);
  			elementA.appendChild(elementLong);
  		
  		}
  		try{
  			DOMSource domSource = new DOMSource(mDoc);
  			StringWriter writer = new StringWriter();
  			StreamResult result = new StreamResult(writer);
  			TransformerFactory tf = TransformerFactory.newInstance();
  			Transformer transformer = tf.newTransformer();
  			transformer.transform(domSource, result);
  			
  			return writer.toString();
  			
  		}catch(TransformerException ex){
  			ex.printStackTrace();
  			return null;
  		}
		
	}
	
	public static String buildAirplanesXML(ArrayList<Airplane> airplanes){
		
		Element mRootNode = null;
		
		try{
  			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
  			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
  			
  			mDoc = docBuilder.newDocument(); 
  			mDoc.setXmlVersion("1.0");
  			mDoc.setXmlStandalone(false);
  			mRootNode = mDoc.createElement("Airplanes");
  			mDoc.appendChild(mRootNode);
  		}catch(ParserConfigurationException e){
  			e.printStackTrace();
  			mDoc = null;
  			
  		}
  		
  		
  		Element elementA;
  		
  		Attr attr;

  		for (int i=0; i<airplanes.size(); i++){
  			
  			
  			elementA = mDoc.createElement("Airplane");
  			mRootNode.appendChild(elementA);
  			
  			attr = mDoc.createAttribute("Manufacturer");
  			attr.setValue(airplanes.get(i).getManufacturer());
  			elementA.setAttributeNode(attr);
  			
  			attr = mDoc.createAttribute("Model");
  			attr.setValue(airplanes.get(i).getModel());
  			elementA.setAttributeNode(attr);
  			
  				
  			Element elementF = mDoc.createElement("FirstClassSeats");
  			Text text = mDoc.createTextNode(airplanes.get(i).getSeatNumber(true)+"");;
  			elementF.appendChild(text);
  			elementA.appendChild(elementF);	
  				
  			Element elementC = mDoc.createElement("CoachSeats");
  			text = mDoc.createTextNode(airplanes.get(i).getSeatNumber(false)+"");;
  			elementC.appendChild(text);
  			elementA.appendChild(elementC);
  		
  		}
  		try{
  			DOMSource domSource = new DOMSource(mDoc);
  			StringWriter writer = new StringWriter();
  			StreamResult result = new StreamResult(writer);
  			TransformerFactory tf = TransformerFactory.newInstance();
  			Transformer transformer = tf.newTransformer();
  			transformer.transform(domSource, result);
  			
  			return writer.toString();
  			
  		}catch(TransformerException ex){
  			ex.printStackTrace();
  			return null;
  		}
		
	}
}
