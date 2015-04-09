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
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8419071157430367156L;
	private static final double VersionUID = 0.3d;
	
	
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
	   
	    
	    if(!access_key.equals("TeamHobbits")) response.sendError(407, "Wrong access key, you don't have the access authorization");
	    
	    if(action.equals("Search")) {
	    	String res = "";
	    	
	    	if((res = ResponseFactory.actionSearch(request))!=null) {
	    		if(res.equals("Error")) {
	    			response.sendError(406, "Request parameters don't match");
	    		}
	    		response.setContentType("text/XML");
	    		out.flush();
	    		
				out.write(res);
	    	}else{
	    		
	    		response.setContentType("text/plain");
	    		out.write("No Flight!");
	    			
	    	}
	    }
	   
	    if(action.equals("List")) {
	    	String res = "";
	    	
	    	if((res = ResponseFactory.actionList(request))!=null)
	    	{
	    		response.setContentType("text/XML");
	    		out.flush();
	    		out.write(res);
	    		
	    	}
    		
			
	    }
	    
	    
	    
	}
	  	
	
	
	
	
	
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
