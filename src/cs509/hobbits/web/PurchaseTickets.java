package cs509.hobbits.web;

import java.io.IOException;
import cs509.hobbits.search.ReserveAndLockDB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author		Yuzhou Xu		
 * @version		1.17	
 * @since		2015-04-08	
 *  It is the main Servlet class that serves as the Interface of our Controller,
 *  The main function is to interact with the front end 
 *  when the user request a POST method and call the corresponding the Utility Class.
 */

@WebServlet("/purchasetickets")

public class PurchaseTickets extends HttpServlet {
	
	
	public PurchaseTickets(){
		super();
	}
	
	public void init(ServletConfig config) throws ServletException {

		super.init(config);
	}

	public void doPost(HttpServletRequest request, 
			HttpServletResponse response)
			throws ServletException, IOException
	{
		
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		  String flightnumber=request.getParameter("flightnum");
		  String seating= request.getParameter("seating");
		  ReserveAndLockDB pxml = new ReserveAndLockDB();
		  boolean lock = pxml.lock();
		  boolean reserve = pxml.reserve(flightnumber, seating);
		  boolean unlock = pxml.unlock();

		  if(!lock) response.sendError(551, "lock DB fail");
		  if(!reserve) response.sendError(552, "reserve DB fail");
		  if(!unlock) response.sendError(553, "unlock DB fail!");

	}
}