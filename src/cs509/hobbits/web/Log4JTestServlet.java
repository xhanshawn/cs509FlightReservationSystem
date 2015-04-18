package cs509.hobbits.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.log4j.Logger;

public class Log4JTestServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 1L;
//	static Logger log = Logger.getLogger(Log4JTestServlet.class);

	public Log4JTestServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("Howdy<br/>");
//		log.debug("debug message");
//		log.info("info message for hobbits");
//		log.warn("warn message");
//		log.error("error message");
//		log.fatal("fatal message");
	}

}