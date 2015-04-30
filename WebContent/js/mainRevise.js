


var urlInfo = window.location.href;
var argsIndex = urlInfo.indexOf("?");
var args = urlInfo.substring((argsIndex+1)).split("&");
var dep_airport = args[0].split("=")[1];
var arr_airport = args[1].split("=")[1];
var dep_date = args[2].split("=")[1];


 // function getXML() {
	
//	get();
	var xmlhttp;

	if (window.XMLHttpRequest) { // code for IE7+, Firefox, Chrome, Opera, Safari
		try {
			xmlhttp = new XMLHttpRequest();
		} catch (e) {
			xmlhttp = false;
		}
	} else { // code for IE6, IE5
		try {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e) {
			xmlhttp = false;
		}
	}


	if (!xmlhttp)
		alert("cant do this");
	 
	

	var url = "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart="+dep_airport+"&&arrival="+arr_airport+"&&day="+dep_date;
	var url2 = 
	"http://localhost:8080/cs509.hobbits/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart=JFK&&arrival=LAX&&day=2015_05_09";
	
	xmlhttp.open("GET", url, true);
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {


			if(xmlhttp.responseText== "No Flight!") alert("no flight");
			

	/*		var flightPlans =xmlhttp.responseXML.documentElement.getElementsByTagName("Flight");
			

			for (i = 0; i < flightPlans.length; i++) {
				text = text + flightPlans[i].getAttribute("Number");
				var flights = flightPlans[i].getElementsByTagName("Departure");
				
				for (j = 0; j < flights.length; j++ ){
					text = text + flights[0].firstChild.textContent;
				
					alert(text);
				}
				document.getElementById("myDiv").innerHTML = text;
			}

			
		}else{
			
		} */
	var flightPlans = xmlhttp.responseXML.documentElement.getElementsByTagName("Flight");
	var flightText = "";
	var flightInformation = new Array();
	for (i = 0; i < flightPlans.length; i++){
		
		var departure = flightPlans[i].getElementsByTagName("Departure")[0].firstChild.textContent;
		var arrival = flightPlans[i].getElementsByTagName("Arrival")[0].firstChild.textContent;
		var departure_date = flightPlans[i].getElementsByTagName("Departure")[0].firstChild.nextSibling.textContent;
		var number = flightPlans[i].getAttribute("Number");
		var firstClass_price = flightPlans[i].getElementsByTagName("Seating")[0].firstChild.getAttribute("Price");
	    var firstClass_left = flightPlans[i].getElementsByTagName("Seating")[0].firstChild.textContent;
		var coach_price = flightPlans[i].getElementsByTagName("Seating")[0].firstChild.nextSibling.getAttribute("Price");
	    var coach_left = flightPlans[i].getElementsByTagName("Seating")[0].firstChild.nextSibling.textContent;
		var new_flight= new flights(departure,arrival,dep_date,number,firstClass_price,firstClass_left,coach_price,coach_left);
		flightText = flightText +"<p>Departure:"+new_flight.departure+"<br />"+"Arrival:"+new_flight.arrival+"<br />"+"Dep_date:"+new_flight.dep_date+"<br />"+"Number:"+new_flight.number+"<br />"+"FirstClass price:"+new_flight.firstClass_price+"<br />"+"Left:"+new_flight.firstClass_left+"<br />"+"Coach price:"+new_flight.coach_price+"<br />"+"Left:"+new_flight.coach_left+"</p>";
	
//	}
	document.getElementById("Results-TimeInfo-p_"+i).innerHTML = departure_date;
	document.getElementById("Number_"+i).innerHTML = number;
	}
//	document.getElementById("Results-CenterRow").style.display="block";
}
}
	xmlhttp.send();
	
	
// }


function flights(departure,arrival,dep_date,number,firstClass_price,firstClass_left,coach_price,coach_left){
	this.departure = departure;
	this.arrival = arrival;
	this.dep_date = dep_date;
	this.number = number;
	this.firstClass_price = firstClass_price;
	this.firstClass_left = firstClass_left;
	this.coach_price = coach_price;
	this.coach_left = coach_left;
	
}
