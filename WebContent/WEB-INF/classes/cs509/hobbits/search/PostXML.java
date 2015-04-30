package cs509.hobbits.search;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/*
 * This class is used to post server
 */
public class PostXML{

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
        	  System.out.println("\nSending 'POST' to unlock database");
        	  System.out.println(("\nResponse Code : " + responseCode));
        	  BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        	  String line;
        	  StringBuffer response = new StringBuffer();
        	  while ((line = in.readLine()) != null) {
        		  response.append(line);
        	  }
        	  in.close();
          }catch (Exception ex) {
              ex.printStackTrace();
              return false;
          }
          
          return true;
      }

      /*
       * This method is used to reserve 
       */

      public void reserve(String number, String seating)  {
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
    	  }catch(Exception ex) {
   
    		  ex.printStackTrace();
 
    	  }
      }
}

