package cs509.hobbits.web;

import java.io.File;
import java.io.IOException;

import cs509.hobbits.search.PostXML;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class PurchaseTickets
 */

@WebServlet("/purchasetickets")

public class PurchaseTickets extends HttpServlet {
	
	
	//private static final Logger log = Logger.getLogger(PurchaseTickets.class);
	//private String 
	public PurchaseTickets(){
		super();
	}
	
	public void init(ServletConfig config) throws ServletException {
		System.out.println("Log4JInitServlet is initializing log4j");
		String log4jLocation = config.getInitParameter("log4j-properties-location");

		ServletContext sc = config.getServletContext();

		if (log4jLocation == null) {
			System.err.println("*** No log4j-properties-location init param, so initializing log4j with BasicConfigurator");
//			BasicConfigurator.configure();
		} else {
			String webAppPath = sc.getRealPath("/");
			String log4jProp = webAppPath + log4jLocation;
			File yoMamaYesThisSaysYoMama = new File(log4jProp);
			if (yoMamaYesThisSaysYoMama.exists()) {
				System.out.println("Initializing log4j with: " + log4jProp);
//				PropertyConfigurator.configure(log4jProp);
			} else {
				System.err.println("*** " + log4jProp + " file not found, so initializing log4j with BasicConfigurator");
//				BasicConfigurator.configure();
			}
		}
		super.init(config);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 System.out.print("hehe");
	}
	public void doPost(HttpServletRequest request, 
			HttpServletResponse response)
			throws ServletException, IOException
	{
		  String flightnumber=request.getParameter("flightnum");
		  String seating= request.getParameter("seating");
		  PostXML pxml = new PostXML();
		  pxml.lock();
		  pxml.reserve(flightnumber, seating);
		  pxml.unlock();
		
	}
}