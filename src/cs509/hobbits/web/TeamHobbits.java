package cs509.hobbits.web;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import cs509.hobbits.search.Flight;
import cs509.hobbits.search.FlightPlan;
import cs509.hobbits.search.SearchResults;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet implementation class TeamHobbits
 */
@WebServlet("/HobbitsFlight")
public class TeamHobbits extends HttpServlet {
	
	
	
	private static final long serialVersionUID = 1L;
	private int req_stop;
	private boolean round_trip = false;
	private String return_day = "";
	private Document mDoc = null;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeamHobbits() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	    
		response.setHeader("Access-Control-Allow-Origin", "*");
	    PrintWriter out = response.getWriter();
	    
	    
	    
	    String access_key = request.getParameter("AccessKey");
	    String action = request.getParameter("action");
	    String depart = request.getParameter("depart");
	    String arrival = request.getParameter("arrival");
	    String day = request.getParameter("day");
	    String window = request.getParameter("window");
	    String stopover = request.getParameter("stop");
	    return_day = request.getParameter("return_day");

	    
	    if(return_day!=""&&return_day!=null) {
	    	round_trip = true;
	    }
	    	
	    if(stopover!=null)
	    req_stop = Integer.parseInt(stopover);
	    
	    
	    
	    if(access_key==null||action==null||depart==null||arrival==null||day==null||access_key==null)
	    	throw new ServletException("Parameters Doesn't Match");
	    
	    
	    if(access_key.equals("TeamHobbits")){
	    	
	    	if(action.equals("Search")){
	    		
	    		if(!depart.equals(null)&&!depart.equals(null)&&!depart.equals(null)){
	    			
	    			String res = "";
	    			

	    			
	    			
	    			
	    			if((res = SearchFlight(depart,arrival,day))!=null
	    					&& res!="")
	    			{
	    				response.setContentType("text/XML");
	    				out.write(res);
	    			}else{
	    				
	    				response.setContentType("text/plain");
	    				out.write("No Flight!");
	    			}
	    			
	    			
	    		}
	    	}
	   	
	    	
			
	    }
	    
	    
	    
	}
	  	
	
	
	private String SearchFlight(String _depart, String _arrival, String _day){
  		
  		Element mRootNode = null;
  		
  		
  		
  		
  		ArrayList <FlightPlan> results = new ArrayList<FlightPlan>();
  		
  		
  		
  		
  		
  		
  		if(!round_trip||return_day==null){
  			SearchResults search = new SearchResults(_depart,_arrival,_day, req_stop);
  			
  			results = search.getPlans();
  		
  		}else{
  			
  			ArrayList <FlightPlan> departs = new ArrayList<FlightPlan> ();
  			int count1 = 0;
  			
  			while(count1<=req_stop&&count1<=2){
  	  			
  				if(req_stop-count1<=2){
  					SearchResults search1 = new SearchResults(_depart,_arrival,_day, count1);
  					ArrayList <FlightPlan> result = search1.getPlans();
  					if(result!=null) departs.addAll(result);
  				}
  				count1++;
  			}
  			

  			ArrayList <FlightPlan> [] result = new ArrayList[3];
  			for(int i=0; i< 3; i++){
  				SearchResults search = new SearchResults(_arrival,_depart,return_day, i );
  				result[i] = new ArrayList<FlightPlan>();
  				result[i] = search.getPlans();
  			}

  			
  			for(int i=0; i<departs.size(); i++){
  				int re_stop = req_stop - departs.get(i).getStopOver();
  				

  				
  				int count2=0;
  				
  				while(count2<result[re_stop].size()){
  					FlightPlan temp = new FlightPlan(null);

  					temp.buildReturnPlan(departs.get(i), result[re_stop].get(count2));;
  					
  					results.add(temp);
  					count2++;
  				}
  				
  				
  				
  			}
  			
  			if(results==null||results.size()==0) return null;
  		}
  		try{
  			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
  			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
  			
  			mDoc = docBuilder.newDocument(); 
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
  			
  			if(results.get(i).isRoundTrip()){
  				
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
	
	private void appendChildren(Element parent, ArrayList<Element> elements){
		
		for(int i=0; i<elements.size(); i++){
			parent.appendChild(elements.get(i));
		}
		
	}
	
	private ArrayList<Element> buildPlan(ArrayList<Flight> _plan){
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
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
