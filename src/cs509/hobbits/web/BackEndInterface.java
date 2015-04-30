package cs509.hobbits.web;

/**
 * This class is main class to communicate with the front end
 * 
 * @author Xu Han 
 * 
 */

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cs509.hobbits.search.DataRetriever;


/**
 * Servlet implementation class TeamHobbits
 */
@WebServlet("/HobbitsFlight")
public class BackEndInterface extends HttpServlet {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8419071157430367156L;
	private static final double VersionUID = 0.42d;
	
	
	
	@PostConstruct
	public void initialize() {
		System.out.println("initial");
		DataRetriever.updateLists();
		DataRetriever.setTimeZone();
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
	    		
	    		response.setContentType("text/XML");
	    		
	    		out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><NoFlight/>");
	    			
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
	    if(action.equals("update")) {
	    	
	    	ResponseFactory.actionUpdate();
	    	response.setContentType("test/XML");
	    	out.flush();
	    	out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Updated/>");
	    }
	    
	}
	  	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}