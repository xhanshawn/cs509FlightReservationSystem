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
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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

/**
 * Servlet implementation class TeamHobbits
 */
@WebServlet("/HobbitsFlight")
public class TeamHobbits extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(TeamHobbits.class.getName());
	
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
	    
	    
	    myLogger();
	    
	    if(access_key.equals("TeamHobbits")){
	    	
	    	if(action.equals("Search")){
	    		
	    		if(!depart.equals(null)&&!depart.equals(null)&&!depart.equals(null)){
	    			
	    			String res = "";
	    			if((res = SearchFlight(depart,arrival,day))!=null
	    					&& res!="")
	    			{
	    				response.setContentType("text/xml");
	    				out.write(res);
	    			}else{
	    				
	    				response.setContentType("text/plain");
	    				out.write("No Flight!");
	    			}
	    		}
	    	}
	    	
	    	
			
	    }
	    
	    
	    
	}
	private void myLogger(){

		logger.setLevel(Level.FINEST);
	    SimpleDateFormat format = new SimpleDateFormat("M-d_HHmmss");    
	    FileHandler FileTxt=null;
	    try {
			FileTxt = new FileHandler("cs509_hobbits_log_"
		                    + format.format(Calendar.getInstance().getTime()) + ".log");
	    } catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    }
	    
	    logger.addHandler(FileTxt);	    
	    FileTxt.setFormatter(new SimpleFormatter());
	    logger.info("info");
	    	
	}
	
	private String SearchFlight(String _depart, String _arrival, String _day){
  		Document mDoc = null;
  		Element mRootNode = null;
  		
  		SearchResults search = new SearchResults(_depart,_arrival,_day);
  		
  		ArrayList <FlightPlan> results = search.getPlans();
  		
  		if(results==null||results.size()==0) return null;
  		
  		try{
  			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
  			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
  			
  			mDoc = docBuilder.newDocument(); 
  			mRootNode = mDoc.createElement("FlightPlans");
  			mDoc.appendChild(mRootNode);
  		}catch(ParserConfigurationException e){
  			e.printStackTrace();
  			mDoc = null;
  			
  			logger.log(Level.SEVERE, "ParserConfigurationException: ", e);
  		}
  		
  		
  		Element elementFP;
  		
  		Attr attr;
  		Text text;
  		
  		
  		
  		for (int i=0; i<results.size(); i++){
  			ArrayList<Flight> flights = results.get(i).getPlan();
  			
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
  			
  			
  			
  			for(int j=0; j<flights.size(); j++){
  				Element elementF = mDoc.createElement("Flight");
  				elementFP.appendChild(elementF);
  				
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
  	  			
  	  			Element Time = mDoc.createElement("Time");
  	  			text = mDoc.createTextNode(flights.get(j).getDateCode(true));
	  			Time.appendChild(text);
	  			elementD.appendChild(Time);
	  			
	  			
	  			//Arrival
	  			Element elementA = mDoc.createElement("Arrival");
  	  			elementF.appendChild(elementA);
  	  			
  	  			Code = mDoc.createElement("Code");
  	  			text = mDoc.createTextNode(flights.get(j).getCode(false));
  	  			Code.appendChild(text);
  	  			elementA.appendChild(Code);
  	  			
  	  			Time = mDoc.createElement("Time");
  	  			text = mDoc.createTextNode(flights.get(j).getDateCode(false));
	  			Time.appendChild(text);
	  			elementA.appendChild(Time);
	  			
	  			
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
  			logger.log(Level.SEVERE, "TransformerException: ", ex);
  			return null;
  		}
  		
  		

  		
  	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
