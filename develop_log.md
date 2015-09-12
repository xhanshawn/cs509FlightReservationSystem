# CS 509 Server

This the log about developing the server side Java Servlet parts of the Course Project of CS 509 at WPI 2015 Spring

This branch contains all the back end codes. 


1. version 2L: 2015 04 04:

	1. Added function of searching multi-leg flight plans.
	2. Added function of searching round trips.
	3. Modified the protocols of communication with this program.
	4. Improved some data structure classes to meet all the new functions.
	5. Changed the core algorithm because of aiming at saving time by asking for plans separately.
	
2. version 0.2: 2015 04 05:
	1. Modified the get local time function by using Google time zone API;
	2. Added customizing window time function.
	3. Solving the efficiency problems caused by Usage Rate Limits of Google time zone API;
	
	
3. version 0.3: 2015 04 08:
	1. Restructured the codes by creating two new classes called ResponseFactory and XMLTxtBuilder.
	2. Added two functions about getting Airports list and Airplane list.
	3. Tested Class Airplane, Airport and LocalTime.
	4. Rewrited some methods inside these three classes to improve the efficiency.
4. version 0.4: 2015 04 17:
	1. Changed time showed into local time.
	2. Used API from timezoneDB.com insead of Google, so that avoid over query limit
	3. Fixed the bug that cannot show round trip successfully.
5. version 0.41: 2015 04 17:
	1. Fixed the bug that shows the local time not fully correct
	2. Reconstructed all the codes
	3. Changed the protocols to get the round trip flights
	
6. version 0.42: 2015 04 17
	1. Make the price value round to 0.00
	2. Add $ to flightplan price
	3. Fixed the bug that cannot search results for certain places

	
