package cs509.hobbits.search;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author		Yuzhou Xu		
 * @version		1.17	
 * @since		2015-04-08	
 * It is a Utility Class that sends the POST methods to the backend the database
 * it offers the function of locking and unlocking the database,
 * as well as fetch the flight number and seating information when called to handle
 *  the function of reserving the tickets
 */
public class ReserveAndLockDB{

	private final String mUrlBase = "http://cs509.cs.wpi.edu:8181/CS509.server/ReservationSystem";


		/*
		 * This method is used to lock database
		 */
    public boolean lock (){
    	URL url;
    	HttpURLConnection connection;

        try {
              
        	  url = new URL(mUrlBase);
              connection = (HttpURLConnection) url.openConnection();
              connection.setRequestMethod("POST");
          
              String params = QueryFactory.lock();
 
              connection.setDoOutput(true);
              connection.setDoInput(true);
              
              DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
              
              writer.writeBytes(params);
              writer.flush();
              writer.close();
 
              int responseCode = connection.getResponseCode();

              
              if ((responseCode>=200) && (responseCode <=299)){
            	  BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            	  String line;
            	  StringBuffer response = new StringBuffer();

            	  while ((line = in.readLine()) != null) {
                   
            		  response.append(line);
            	  
            	  }
            	  in.close();
              }
              
              //peer review and added by Xu Han
              if(responseCode>=400) return false;
              
          }catch (Exception ex) {
                  ex.printStackTrace();
                  return false;
          }
          
          return true;
}

		/*
		 * This method is used to unlock
		 */

      public boolean unlock (){
    	  URL url;
    	  HttpURLConnection connection;
         
          try {
        	  url = new URL(mUrlBase);
        	  connection = (HttpURLConnection) url.openConnection();
        	  connection.setRequestMethod("POST");
        	  connection.setRequestProperty("Use8-Agent", "Team08");
        	  connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
        	  String params = QueryFactory.unlock();
 
        	  connection.setDoOutput(true);
        	  DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
          
        	  writer.writeBytes(params);
        	  writer.flush();
        	  writer.close();
 
        	  int responseCode = connection.getResponseCode();
        	  BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        	  String line;
        	  StringBuffer response = new StringBuffer();
        	  while ((line = in.readLine()) != null) {
        		  response.append(line);
        	  }
        	  in.close();
        	  
              //peer review and added by Xu Han
              if(responseCode>=400) return false;
              
          }catch (Exception ex) {
              ex.printStackTrace();
              return false;
          }
          
          return true;
      }

      /*
       * This method is used to reserve 
       */

      public boolean reserve(String number, String seating)  {
    	  URL url;
    	  HttpURLConnection connection;
    
    	  try{

    		  url = new URL(mUrlBase);
   
    		  connection = (HttpURLConnection) url.openConnection();
    		  connection.setRequestMethod("POST");
    		  connection.setRequestProperty("Use8-Agent", "Team08");
    		 
    		  String params = QueryFactory.reserve(number, seating);
 
    		  connection.setDoOutput(true);
    		  DataOutputStream writer = new DataOutputStream(connection.getOutputStream());

    		  writer.writeBytes(params);
    		  writer.flush();
    		  writer.close();
 
    		  int responseCode = connection.getResponseCode();
    		  if ((responseCode>=200) && (responseCode <=299)){
            	  

    			  BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    			  String line;
    			  StringBuffer response = new StringBuffer();
    			  while ((line = in.readLine()) != null) {
    				  response.append(line);
    			  }
    			  in.close();
    		  }
    		  
              //peer review and added by Xu Han
              if(responseCode>=400) return false;
            
              
    	  }catch(Exception ex) {
   
    		  ex.printStackTrace();
 
    	  }
    	  return true;
      }
}

