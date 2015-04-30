var urlInfo = window.location.href;
var argsIndex = urlInfo.indexOf("?");
var args = urlInfo.substring((argsIndex+1)).split("&");
var dep_airport = args[0].split("=")[1];
var arr_airport = args[1].split("=")[1];
var dep_date = args[2].split("=")[1];
var arg_1 = dep_date.split("/");
var departure_date = arg_1[2]+"_"+arg_1[0]+"_"+arg_1[1];


var flightPlans;
var flightPlans_1;
var flightPlans_2;

$(document).ready(function(){
 $(".SearchResults").hide();
});

window.onload = ONLOAD();
function ONLOAD() {
	
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
	 
	var url = "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart="+dep_airport+"&&arrival="+arr_airport+"&&day="+departure_date+"&&stop=0";
	
	
	xmlhttp.open("GET", url, true);
	xmlhttp.onreadystatechange = function() {
	if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {


			if(xmlhttp.responseText== "No Flight!") alert("no flight");
	
	
	 flightPlans =xmlhttp.responseXML.documentElement.getElementsByTagName("FlightPlan");
 	var plan_detail = new Array();
	
	var start=0;
	var stopover = 0;
	var CREATE = createDiv(start,flightPlans.length);
    var showPlan = flight_Plan(start,flightPlans.length,stopover,flightPlans,plan_detail);
}
}
xmlhttp.send();


 var xmlhttp_1;

if (window.XMLHttpRequest) { // code for IE7+, Firefox, Chrome, Opera, Safari
	try {
		xmlhttp_1 = new XMLHttpRequest();
	} catch (e) {
		xmlhttp_1 = false;
	}
} else { // code for IE6, IE5
	try {
		xmlhttp_1 = new ActiveXObject("Microsoft.XMLHTTP");
	} catch (e) {
		xmlhttp_1 = false;
	}
}


if (!xmlhttp_1)
	alert("cant do this");
 
var url_1 = "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart="+dep_airport+"&&arrival="+arr_airport+"&&day="+departure_date+"&&stop=1";


xmlhttp_1.open("GET", url_1, true);
xmlhttp_1.onreadystatechange = function() {
if (xmlhttp_1.readyState == 4 && xmlhttp_1.status == 200) {


		if(xmlhttp_1.responseText== "No Flight!") alert("no flight");


   flightPlans_1 = xmlhttp_1.responseXML.documentElement.getElementsByTagName("FlightPlan");
var plan_detail_1 = new Array();    
var start_1 = flightPlans.length;
var stopover_1 = 1;
var CREATE_1 = createDiv(start_1,start_1+flightPlans_1.length);
var showPlan_1 = flight_Plan(start_1,start_1+flightPlans_1.length,stopover_1,flightPlans_1,plan_detail_1);
}
}

xmlhttp_1.send();

var xmlhttp_2;

if (window.XMLHttpRequest) { // code for IE7+, Firefox, Chrome, Opera, Safari
	try {
		xmlhttp_2 = new XMLHttpRequest();
	} catch (e) {
		xmlhttp_2 = false;
	}
} else { // code for IE6, IE5
	try {
 		xmlhttp_2 = new ActiveXObject("Microsoft.XMLHTTP");
	} catch (e) {
		xmlhttp_2 = false;
	}
}


if (!xmlhttp_2)
	alert("cannot do this");
 
var url_2 = "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart="+dep_airport+"&&arrival="+arr_airport+"&&day="+departure_date+"&&stop=2";


xmlhttp_2.open("GET", url_2, true);
xmlhttp_2.onreadystatechange = function() {
if (xmlhttp_2.readyState == 4 && xmlhttp_2.status == 200) {


		if(xmlhttp_2.responseText== "No Flight!") alert("no flight");


 flightPlans_2 = xmlhttp_2.responseXML.documentElement.getElementsByTagName("FlightPlan");
var plan_detail_2 = new Array();
var start_2 = flightPlans_1.length+flightPlans.length;
var stopover_2 = 2;
var CREATE_2 = createDiv(start_2,flightPlans_1.length+flightPlans.length+flightPlans_2.length);
var showPlan_2 = flight_Plan(start_2,flightPlans_1.length+flightPlans.length+flightPlans_2.length,stopover_2,flightPlans_2, plan_detail_2);
}
}

xmlhttp_2.send();

  
  
	

   
}

 

   $(".SearchResults").hide();



function createDiv(start,length){
	for(var i = start; i<length; i++){
	         var result = document.createElement('div');
			 result.id = "SearchResults"+i;
			 result.className = "SearchResults";
		 
			 var result_left = document.createElement('div');
	         result_left.id = "Results-LeftRow"+i;
			 result_left.innerHTML = "PRICE";
			 result_left.className = "Results-LeftRow";
   	 
		
		
	
            var result_center = document.createElement('div');
            result_center.id = "Results-CenterRow"+i;
            result_center.className = "Results-CenterRow";
	 
	 
		
			 var result_right = document.createElement('div');
			 result_right.id = "Results-RightRow"+i;
			 result_right.className = "Results-RightRow";
			 var p_11 =  document.createElement('p');
			 p_11.id = "stop"+i;
			 p_11.innerHTML = "Flight Number";
			  p_11.className = "Number";
			  var select = document.createElement('a');
			  select.href = "#0";
			  select.className = "cd-popup-trigger";
			  select.id = "cd-popup-trigger"+i;
			  select.innerHTML = "Select";
			  
		//	  var button = document.createElement('button');
			  
			  
		//	  button.id = "button"+i;
		//	  button.className = "button";
		//	  button.innerHTML = "button";
		 
			 var hr = document.createElement('hr');
			  document.getElementById("DisplaySearchResults").appendChild(result);
			   document.getElementById("DisplaySearchResults").appendChild(hr);
			  document.getElementById("SearchResults"+i).appendChild(result_left);
		
		
			  
			  document.getElementById("SearchResults"+i).appendChild(result_right);
	 
  
             document.getElementById("SearchResults"+i).appendChild(result_center);
	
		 document.getElementById("Results-RightRow"+i).appendChild(p_11);
	     document.getElementById("Results-RightRow"+i).appendChild(select);
	//	  document.getElementById("Results-RightRow"+i).appendChild(button);
	}
}

function flight_Plan(start,length,stopover,flightPlans,plan_detail){
	for(var i = start; i<length; i++){
		var coach_price = Math.round(flightPlans[i-start].getAttribute("Coach")*100)/100;
		
		var FirstClass_price = Math.round(flightPlans[i-start].getAttribute("FirstClass")*100)/100;
		var stop = flightPlans[i-start].getAttribute("Stopover");
		document.getElementById("Results-LeftRow"+i).innerHTML = "PRICE:<br>"+"Coach<br><br>"+"$"+coach_price+"<br><br>"+"FirstClass<br><br>"+"$"+FirstClass_price;
	

/*var dep_list = "";
var arr_list = "";
var depTime_list = "";
var arrTime_list = "";*/
var flight_list = flightPlans[i-start].getElementsByTagName("Flight");
for (var j = 0; j<flight_list.length; j++){
/*	 dep_list = dep_list+flight_list[j].firstChild.firstChild.textContent+"<br><br>";
	 arr_list = arr_list+flight_list[j].firstChild.nextSibling.firstChild.textContent+"<br><br>";
	 depTime_list = depTime_list+flight_list[j].firstChild.lastChild.textContent+"<br><br>";
	 arrTime_list = arrTime_list+flight_list[j].firstChild.nextSibling.lastChild.textContent+"<br><br>";}
	 	document.getElementById("Results-depInfo"+i).innerHTML = dep_list;
	    document.getElementById("Results-arrInfo"+i).innerHTML = arr_list;
		document.getElementById("Results-depTimeInfo"+i).innerHTML = depTime_list;
		document.getElementById("Results-arrTimeInfo"+i).innerHTML = arrTime_list;*/
 var result_center_show = document.createElement('div');
 result_center_show.id = "Results-CenterShowRow"+i+j;
 result_center_show.className = "Results-CenterShowRow";
  document.getElementById("Results-CenterRow"+i).appendChild(result_center_show);
   var result_center_dep = document.createElement('span');
   result_center_dep.className = "Results-center-dep";
   var dep = flight_list[j].firstChild.firstChild.textContent;
   result_center_dep.innerHTML = dep;
   var result_center_arr = document.createElement('span');
   result_center_arr.className = "Results-center-arr";
   var arr =flight_list[j].firstChild.nextSibling.firstChild.textContent;
   result_center_arr.innerHTML = arr;
   var result_center_depTime = document.createElement('span');
   result_center_depTime.className = "Results-center-depTime";
   var depTime = flight_list[j].firstChild.lastChild.textContent;
   result_center_depTime.innerHTML = depTime;
   var result_center_arrTime = document.createElement('span');
   result_center_arrTime.className = "Results-center-arrTime";
  var arrTime = flight_list[j].firstChild.nextSibling.lastChild.textContent;
   result_center_arrTime.innerHTML = arrTime;
   var result_center_label = document.createElement('span');
   result_center_label.className = "Results-center-label";
 
   result_center_label.innerHTML = "&#8594";
   
    document.getElementById("Results-CenterShowRow"+i+j).appendChild(result_center_depTime);
	document.getElementById("Results-CenterShowRow"+i+j).appendChild(result_center_dep);
	document.getElementById("Results-CenterShowRow"+i+j).appendChild(result_center_label);
	document.getElementById("Results-CenterShowRow"+i+j).appendChild(result_center_arrTime);
	document.getElementById("Results-CenterShowRow"+i+j).appendChild(result_center_arr);
/*  	 var arr =flight_list[j].firstChild.nextSibling.firstChild.textContent;
  	var depTime = flight_list[j].firstChild.lastChild.textContent;
	var arrTime = flight_list[j].firstChild.nextSibling.lastChild.textContent;
	var flight_inf = depTime+" "+dep+" "+"&#8594"+" "+arrTime+" "+arr;
	document.getElementById("Results-CenterShowRow"+i+j).innerHTML = flight_inf;*/
 }
		document.getElementById("stop"+i).innerHTML = stopover+" "+"stopover";
}

/*  for (var i = start; i<length;i++){
	   var Flight_information   = flightPlans[i-start].getElementsByTagName("Flight");
	   var flight_detail = new Array();
	   for(var j = 0; j < Flight_information.length; j++){
		   var dep_code = Flight_information[j].firstChild.firstChild.textContent;
		   var dep_time_code = Flight_information[j].firstChild.lastChild.textContent;
		   var arr_code = Flight_information[j].firstChild.nextSibling.firstChild.textContent;
		   var arr_time_code = Flight_information[j].firstChild.nextSibling.lastChild.textContent;
		   var price_firstClass = Flight_information[j].lastChild.firstChild.getAttribute("Price");
		   var left_firstClass = Flight_information[j].lastChild.firstChild.textContent;
		   var price_coach = Flight_information[j].lastChild.firstChild.nextSibling.getAttribute("Price");
		   var left_coach = Flight_information[j].lastChild.firstChild.nextSibling.textContent;
		   var single_flight_detail = new Array(dep_code,dep_time_code,arr_code,arr_time_code,price_firstClass,left_firstClass,price_coach,left_coach);
		   flight_detail.push(single_flight_detail);
	   }
	   plan_detail.push(flight_detail);
   }*/
}