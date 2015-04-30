var urlInfo = window.location.href;
var argsIndex = urlInfo.indexOf("?");
var args = urlInfo.substring((argsIndex+1)).split("&");
var xmlhttp;
var xmlhttp_1;
var xmlhttp_2;
var flightPlans;
var flightPlans_1;
var flightPlans_2;
var flightPlans_3;
var flightPlans_4;
var url;
var url_1;
var url_2;
var start;
var stopover;
var CREATE;
var showPlan;
var start_1;
var stopover_1;
var CREATE_1;
var showPlan_1;
var start_2;
var stopover_2;
var CREATE_2;
var showPlan_2;
var flightPlanNumber_list = [];
//jarway~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
var arrival_date = "";
var window_time = "";
//variables for filter
var filter_flag = new Array();
var Flight_num="";
var ready = 0;

var total_flights = $('.Flight:hidden');

if(args.length == 4)
{
if(args[3].split("=")[0] == "return_date")
{
	var arr_date = args[3].split("=")[1];
	var arg_2 = arr_date.split("/");
	arrival_date = arg_2[2]+"_"+arg_2[0]+"_"+arg_2[1];
}
if(args[3].split("=")[0] == "window")
{
	window_time = args[3].split("=")[1];
}
}


if(args.length == 5)
{
	var arr_date = args[3].split("=")[1];
	var arg_2 = arr_date.split("/");
	arrival_date = arg_2[2]+"_"+arg_2[0]+"_"+arg_2[1];
	window_time = args[4].split("=")[1];
}	



	var dep_airport = args[0].split("=")[1];
	var arr_airport = args[1].split("=")[1];
	var dep_date = args[2].split("=")[1];
	var arg_1 = dep_date.split("/");
	var departure_date = arg_1[2]+"_"+arg_1[0]+"_"+arg_1[1];



	if(arrival_date != "")
	{
		window.onload = ONLOAD();
		function ONLOAD() {
		
		    
	
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
			if(window == ""){
				url = "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart="+dep_airport+"&&arrival="+arr_airport+"&&day="+departure_date+"&&stop=0"+"&&return_day="+arrival_date;
			}
			else{
				url = "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart="+dep_airport+"&&arrival="+arr_airport+"&&day="+departure_date+"&&stop=0"+"&&return_day="+arrival_date+"&&window="+window_time;
			}
	
			xmlhttp.open("GET", url, true);
			xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {


					if(xmlhttp.responseText== "No Flight!") alert("no flight");
	
	
			 flightPlans =xmlhttp.responseXML.documentElement.getElementsByTagName("FlightPlan");
	//	 	var plan_detail = new Array();
	
			 start=0;
			 stopover = 0;
			 CREATE = createDiv(start,flightPlans.length);
		     showPlan = flight_Plan_Round(start,flightPlans.length,stopover,flightPlans);
		}
		}
		xmlhttp.send();


	 

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
 
		 //url_1 = "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart="+dep_airport+"&&arrival="+arr_airport+"&&day="+departure_date+"&&stop=1"+"&&return_day="+arrival_date;

		 if(window == ""){
				url_1 = "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart="+dep_airport+"&&arrival="+arr_airport+"&&day="+departure_date+"&&stop=1"+"&&return_day="+arrival_date;
			}
			else{
				url_1 = "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart="+dep_airport+"&&arrival="+arr_airport+"&&day="+departure_date+"&&stop=1"+"&&return_day="+arrival_date+"&&window="+window_time;
			}

		xmlhttp_1.open("GET", url_1, true);
		xmlhttp_1.onreadystatechange = function() {
		if (xmlhttp_1.readyState == 4 && xmlhttp_1.status == 200) {


			


		   flightPlans_1 =xmlhttp_1.responseXML.documentElement.getElementsByTagName("FlightPlan");
	//	 plan_detail_1 = new Array();    
		 start_1=flightPlans.length;
		 stopover_1 = 1;
		 CREATE_1 = createDiv(start_1,start_1+flightPlans_1.length);
		 showPlan_1 = flight_Plan_Round(start_1,start_1+flightPlans_1.length,stopover_1,flightPlans_1);
		}
		}

		xmlhttp_1.send();




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
			alert("cant do this");
 
		 //url_2 = "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart="+dep_airport+"&&arrival="+arr_airport+"&&day="+departure_date+"&&stop=2"+"&&return_day="+arrival_date;
		if(window == ""){
				url_2 = "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart="+dep_airport+"&&arrival="+arr_airport+"&&day="+departure_date+"&&stop=2"+"&&return_day="+arrival_date;
			}
			else{
				url_2 = "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart="+dep_airport+"&&arrival="+arr_airport+"&&day="+departure_date+"&&stop=2"+"&&return_day="+arrival_date+"&&window="+window_time;
			}

		xmlhttp_2.open("GET", url_2, true);
		xmlhttp_2.onreadystatechange = function() {
		if (xmlhttp_2.readyState == 4 && xmlhttp_2.status == 200) {


				if(xmlhttp_2.responseText== "No Flight!") alert("no flight");


		flightPlans_2 =xmlhttp_2.responseXML.documentElement.getElementsByTagName("FlightPlan");
	//	var plan_detail_2 = new Array();
		start_2=flightPlans_1.length+flightPlans.length;
		 stopover_2 = 2;
		 CREATE_2 = createDiv(start_2,flightPlans_1.length+flightPlans.length+flightPlans_2.length);
		 showPlan_2 = flight_Plan_Round(start_2,flightPlans_1.length+flightPlans.length+flightPlans_2.length,stopover_2,flightPlans_2);


		 //Xu Han
		  document.getElementById("loading-pic").style.visibility="hidden";
		  ready = 1;
		}
		}

		xmlhttp_2.send(); 

	}
	}

   


	else if(arrival_date == "")
{ 
	window.onload = ONLOAD_1();
function ONLOAD_1() {
	
	
	
	
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
	if(window_time == ""){
	    url = "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart="+dep_airport+"&&arrival="+arr_airport+"&&day="+departure_date+"&&stop=0";
	}
	else{
		
		url = "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart="+dep_airport+"&&arrival="+arr_airport+"&&day="+departure_date+"&&stop=0"+"&&window="+window_time;
	}
		
	
	xmlhttp.open("GET", url, true);
	xmlhttp.onreadystatechange = function() {
	if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {


			if(xmlhttp.responseText== "No Flight!") alert("no flight");
	
	
	 flightPlans =xmlhttp.responseXML.documentElement.getElementsByTagName("FlightPlan");
 //	var plan_detail = new Array();
	
	 start=0;
	 stopover = 0;
	 CREATE = createDiv(start,flightPlans.length);
     showPlan = flight_Plan(start,flightPlans.length,stopover,flightPlans);
}
}
xmlhttp.send();


  

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
 
  //url_1 = "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart="+dep_airport+"&&arrival="+arr_airport+"&&day="+departure_date+"&&stop=1";
if(window_time == ""){
	    url_1 = "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart="+dep_airport+"&&arrival="+arr_airport+"&&day="+departure_date+"&&stop=1";
	}
	else{
		url_1 = "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart="+dep_airport+"&&arrival="+arr_airport+"&&day="+departure_date+"&&stop=1"+"&&window="+window_time;
	}
  

xmlhttp_1.open("GET", url_1, true);
xmlhttp_1.onreadystatechange = function() {
if (xmlhttp_1.readyState == 4 && xmlhttp_1.status == 200) {


		if(xmlhttp_1.responseText== "No Flight!") alert("no flight");


   flightPlans_1 = xmlhttp_1.responseXML.documentElement.getElementsByTagName("FlightPlan");
//var plan_detail_1 = new Array();    
 start_1 = flightPlans.length;
 stopover_1 = 1;
 CREATE_1 = createDiv(start_1,start_1+flightPlans_1.length);
 showPlan_1 = flight_Plan(start_1,start_1+flightPlans_1.length,stopover_1,flightPlans_1);
}
}

xmlhttp_1.send();



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
 
  //url_2 = "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart="+dep_airport+"&&arrival="+arr_airport+"&&day="+departure_date+"&&stop=2";
if(window_time == ""){
	    url_2 = "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart="+dep_airport+"&&arrival="+arr_airport+"&&day="+departure_date+"&&stop=2";
	}
	else{
		url_2 = "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/HobbitsFlight?AccessKey=TeamHobbits&&action=Search&&depart="+dep_airport+"&&arrival="+arr_airport+"&&day="+departure_date+"&&stop=2"+"&&window="+window_time;
	}

xmlhttp_2.open("GET", url_2, true);
xmlhttp_2.onreadystatechange = function() {
if (xmlhttp_2.readyState == 4 && xmlhttp_2.status == 200) {


		if(xmlhttp_2.responseText== "No Flight!") alert("no flight");


 flightPlans_2 = xmlhttp_2.responseXML.documentElement.getElementsByTagName("FlightPlan");
//var plan_detail_2 = new Array();
 start_2 = flightPlans_1.length+flightPlans.length;
 stopover_2 = 2;
 CREATE_2 = createDiv(start_2,flightPlans_1.length+flightPlans.length+flightPlans_2.length);
 showPlan_2 = flight_Plan(start_2,flightPlans_1.length+flightPlans.length+flightPlans_2.length,stopover_2,flightPlans_2);

 //Xu Han
 document.getElementById("loading-pic").style.visibility="hidden";
 ready =1;
   
}
}

xmlhttp_2.send();

  
  
	

}
}
	

//Xu Han Peer review: Last part has a lot of codes which could be reused. 


function createDiv(start,length){
	for(var i = start; i<length; i++){


		var result_all = document.createElement('div');
        result_all.id = "Flight"+i;
        result_all.className = "Flight";


	         var result = document.createElement('div');
			 result.id = "SearchResults"+i;
			 result.className = "SearchResults";
		 
			 var result_left = document.createElement('div');
	         result_left.id = "Results-LeftRow"+i;
			// result_left.innerHTML = "PRICE";
			 result_left.className = "Results-LeftRow";
   	 
		
		
			 var price = document.createElement('p');
			 price.id ="price"+i;
   	          price.innerHTML = "PRICE";
		      price.className = "price";

		      var first_sign = document.createElement('p');
			 first_sign.id ="first_sign"+i;
   	          first_sign.innerHTML = "FirstClass";
		      first_sign.className = "first_sign";

		      var firstClass_div = document.createElement('div');
		      firstClass_div.id = "firstClass"+i;
		      firstClass_div.className = "firstClass";

		      var firstClass_radio = document.createElement('input');
		      firstClass_radio.type = "radio";
		      firstClass_radio.id = "firstClass_radio"+i;
		      firstClass_radio.className = "firstClass_radio";
//firstClass_radio.checked = true;
		      firstClass_radio.name = "firstClass"+i;
		      firstClass_radio.value = "n";

		      var firstClass_price = document.createElement('p');
		      firstClass_price.id = "firstClass_price"+i;
		      firstClass_price.className = "firstClass_price";

		      var coach_sign = document.createElement('p');
			 coach_sign.id ="coach_sign"+i;
   	          coach_sign.innerHTML = "Coach";
		      coach_sign.className = "coach_sign";

		      var coach_div = document.createElement('div');
		      coach_div.id = "coach"+i;
		      coach_div.className = "coach";

		      var coach_radio = document.createElement('input');
		      coach_radio.type = "radio";
		      coach_radio.id = "coach_radio"+i;
		      coach_radio.className = "coach_radio";
		      coach_radio.name = "firstClass"+i;
		      coach_radio.value = "y";

		      var coach_price = document.createElement('p');
		      coach_price.id = "coach_price"+i;
		      coach_price.className = "coach_price";


	
            var result_center = document.createElement('div');
            result_center.id = "Results-CenterRow"+i;
            result_center.className = "Results-CenterRow";
          

         //*********   zheming
           var result_time  = document.createElement('div');
	         result_time.id = "result_time"+i;
	         result_time.className = "result_time";

	         var time_all = document.createElement('div');
             time_all.id = "time_all"+i;
              time_all.className = "time_all";
              time_all.innerHTML = "total time";

              var time_result = document.createElement('div');
              time_result.id = "time_result"+i;
              time_result.className = "time_result";
//*********     zheming

	 
	 
		
			 var result_right = document.createElement('div');
			 result_right.id = "Results-RightRow"+i;
			 result_right.className = "Results-RightRow";
			 var p_11 =  document.createElement('p');
			 p_11.id = "stop"+i;
			// p_11.innerHTML = "Flight Number";
			  p_11.className = "stop";
			  var select = document.createElement('a');
			  select.href = "#0";
			  select.className = "cd-popup-trigger";
			  select.id = "cd-popup-trigger"+i;
			  select.innerHTML = "Select";
			 select.addEventListener("click",showMessage,false);

			 //Yuzhou's Popuo Dialog
		

			  
		 
			  var hr = document.createElement('hr');
			  document.getElementById("DisplaySearchResults").appendChild(result_all);
			//   document.getElementById("DisplaySearchResults").appendChild(hr);
			document.getElementById("Flight"+i).appendChild(result);
			document.getElementById("Flight"+i).appendChild(hr);
			  document.getElementById("SearchResults"+i).appendChild(result_left);

		document.getElementById("SearchResults"+i).appendChild(result_right);
	 
  
             document.getElementById("SearchResults"+i).appendChild(result_center);
         //*********   zheming
              document.getElementById("SearchResults"+i).appendChild(result_time);
             document.getElementById("result_time"+i).appendChild(time_all);
             document.getElementById("result_time"+i).appendChild(time_result);
         //*********   zheming
	
	          document.getElementById("Results-LeftRow"+i).appendChild(price);
	          document.getElementById("Results-LeftRow"+i).appendChild(first_sign);
			  document.getElementById("Results-LeftRow"+i).appendChild(firstClass_div);
			  document.getElementById("Results-LeftRow"+i).appendChild(coach_sign);
			  document.getElementById("Results-LeftRow"+i).appendChild(coach_div);
		 document.getElementById("Results-RightRow"+i).appendChild(p_11);
	     document.getElementById("Results-RightRow"+i).appendChild(select);

	     document.getElementById("firstClass"+i).appendChild(firstClass_radio);
	     document.getElementById("firstClass"+i).appendChild(firstClass_price);
	      document.getElementById("coach"+i).appendChild(coach_radio);
	     document.getElementById("coach"+i).appendChild(coach_price);

//Yuzhou's Popuo Dialog
	 




	}
}

function showMessage() {

  var str_class = this.className;
  var str_id = this.id;
  var class_length = str_class.length;
  var id_length = str_id.length;
  var str="";
  for(var i = class_length;i<id_length;i++){
  	str += str_id[i];
  }
 var num = parseInt(str);
// alert(flightPlanNumber_list[num][0] + "     " + flightPlanNumber_list[num][1]);
  var r = confirm("Are you Sure to Purchase the Tickets?");
if (r == true) {

    if (document.getElementById("coach_radio"+num).checked == true){
	for( var i = 0; i<flightPlanNumber_list[num].length; i++){
					var number = flightPlanNumber_list[num][i];
                 $.ajax({
					  type: "POST",
					  url: "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/purchasetickets?flightnum="+flightPlanNumber_list[num][i]+"&&seating=Coach",
					  error: function() {
					  	//Xu Han Peer review: need to handle error situation
					  		buyAgain(number,"Coach");

					  },
				   	 
				  
		          });
	}
}
else if(document.getElementById("firstClass_radio"+num).checked == true ){
	for( var i = 0; i<flightPlanNumber_list[num].length; i++){
							var number = flightPlanNumber_list[num][i];

                 $.ajax({
					  type: "POST",
					  url: "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/purchasetickets?flightnum="+flightPlanNumber_list[num][i]+"&&seating=FirstClass",
					  error: function() {

					  	//Xu Han Peer review: need to handle error situation

						 buyAgain(number,"FirstClass");
					  },
				   	 
				  
		          });
	}
}
else {alert("please select an appropriate seating")}
} else {
    x = "You pressed Cancel!";
}



}

//Xu Han

function buyAgain(Flightnumber, seat){
	$.ajax({
					  type: "POST",
					  url: "http://default-environment-kmx2ref3wn.elasticbeanstalk.com/purchasetickets?flightnum="+Flightnumber+"&&seating=" + seat,
					  error: function() {
						 alert("purchase could not be processed correctly. Please try again or Call our agancy! ");

					
					  },
				   	 
				  
	});
}

// it trigeers the event to close the dialog
function closeDialog() {
	 $('.cd-popup').removeClass('is-visible');
}

function flight_Plan(start,length,stopover,flightPlans){
	for(var i = start; i<length; i++){
		var coach_price = Math.round(flightPlans[i-start].getAttribute("Coach")*100)/100;
		
		var FirstClass_price = Math.round(flightPlans[i-start].getAttribute("FirstClass")*100)/100;
		var stop = flightPlans[i-start].getAttribute("Stopover");
		
		document.getElementById("firstClass_price"+i).innerHTML = "$"+FirstClass_price;
		document.getElementById("coach_price"+i).innerHTML = "$"+coach_price;
		
		var FlightNmuber_list = [];
		var FirstClass_left_list = [];
		var Coach_left_list = [];
	     var flag_1 = true;
	     var flag_2 = true;
       //**********   zheming
        var layover_arrive_list = [];
         var layover_dep_list = [];

         var flightTime_total = 0;
         // *********  zheming
var flight_list = flightPlans[i-start].getElementsByTagName("Flight");

	for (var j = 0; j<flight_list.length; j++){
//*********   zheming
		var arr_once = [];
    var dep_once = [];
	if(j==0){
   var arrTime_only = flight_list[j].firstChild.nextSibling.lastChild.textContent.split(" ")[2];
   var  arrTime_day = flight_list[j].firstChild.nextSibling.lastChild.textContent.split(" ")[0];
   var  arrTime_hour = arrTime_only.split(":")[0];
   var arrTime_min = arrTime_only.split(":")[1];
   var arr_once_1 = [];
   
      arr_once_1.push(arrTime_hour);
      arr_once_1.push(arrTime_min);
      arr_once_1.push(arrTime_day);
      layover_arrive_list.push(arr_once_1);
	}
// ************   zheming
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

    var total_Time = document.createElement('span');
    total_Time.className = "Results-center-totalTime";
    var totalMin = parseInt(flight_list[j].getAttribute("FlightTime"));
    var total_Time_hour;
    flightTime_total = flightTime_total + totalMin;
    if(totalMin<60) 
    {
    	total_Time_hour = totalMin+"min";

    }
    else{
    var totalHour = Math.floor(totalMin/60);
    var remainMin = totalMin-60*totalHour;
    total_Time_hour = totalHour+"h"+remainMin+"min";
    }
   total_Time.innerHTML = total_Time_hour;
//*********  zheming
   if((j!=0)&&(j!=flight_list.length-1))
   {
   var depTime_only = depTime.split(" ")[2];
   var  depTime_hour = depTime_only.split(":")[0];
   var depTime_min = depTime_only.split(":")[1];
   var depTime_day = depTime.split(" ")[0];

   var arr_temp = arrTime.split(" ")[2];
   var arr_temp_hour = arr_temp.split(":")[0];
   var arr_temp_min = arr_temp.split(":")[1];
   var arr_temp_day = arrTime.split(" ")[0];

   dep_once.push(depTime_hour);
   dep_once.push(depTime_min);
   dep_once.push(depTime_day);
   arr_once.push(arr_temp_hour);
   arr_once.push(arr_temp_min);
   arr_once.push(arr_temp_day);
   layover_arrive_list.push(arr_once);
   layover_dep_list.push(dep_once);
   }
   if(j == flight_list.length-1)
 {
	var depTime_only_1 = depTime.split(" ")[2];
   var  depTime_hour_1 = depTime_only_1.split(":")[0];
   var depTime_min_1 = depTime_only_1.split(":")[1];
   var depTime_day_1 = depTime.split(" ")[0];
     dep_once.push(depTime_hour_1);
   dep_once.push(depTime_min_1);
   dep_once.push(depTime_day_1);
    layover_dep_list.push(dep_once);

 } 
 //***********  zheming
    document.getElementById("Results-CenterShowRow"+i+j).appendChild(result_center_depTime);
	document.getElementById("Results-CenterShowRow"+i+j).appendChild(result_center_dep);
	document.getElementById("Results-CenterShowRow"+i+j).appendChild(result_center_label);
	document.getElementById("Results-CenterShowRow"+i+j).appendChild(result_center_arrTime);
	document.getElementById("Results-CenterShowRow"+i+j).appendChild(result_center_arr);
    document.getElementById("Results-CenterShowRow"+i+j).appendChild(total_Time);
	
	var FlightNumber = flight_list[j].getAttribute("Number");
 FlightNmuber_list.push(FlightNumber);

    var FirstClass_left = flight_list[j].lastChild.firstChild.textContent;
     var Coach_left = flight_list[j].lastChild.firstChild.nextSibling.textContent;
   FirstClass_left_list.push(FirstClass_left);

   Coach_left_list.push(Coach_left);

 }        
//*********   zheming
if(flightTime_total<60){
    	document.getElementById("time_result"+i).innerHTML = flightTime_total+"min";
    }
    else{
    	var flightTime_total_hour = Math.floor(flightTime_total/60);
    	var flightTime_total_min = flightTime_total-60*flightTime_total_hour;
    	document.getElementById("time_result"+i).innerHTML = flightTime_total_hour+"h"+flightTime_total_min+"min";
    }
//********   zheming
		document.getElementById("stop"+i).innerHTML = stopover+" "+"stopover";
		flightPlanNumber_list.push(FlightNmuber_list);
		

 for(var m =0; m< FirstClass_left_list.length; m++){
     if( FirstClass_left_list[m] == "0")
     	{flag_1 = false;
     	break;}
} 

for(var n =0; n<Coach_left_list.length; n++){
     if( Coach_left_list[n] == "0")
     	{flag_2 = false;
     	break;}

}    
if(flag_1 == false)
	document.getElementById("firstClass_radio"+i).disabled = true;
if(flag_2 == false)
	document.getElementById("coach_radio"+i).disabled = true;
//*********  zheming
var layover_final = [];
  for(var t = 0; t<layover_dep_list.length; t++){
      var layover_hour = parseInt(layover_dep_list[t][0])-parseInt(layover_arrive_list[t][0]);
      var layover_min =  parseInt(layover_dep_list[t][1])-parseInt(layover_arrive_list[t][1]);
      var layover_day =  parseInt(layover_dep_list[t][2])-parseInt(layover_arrive_list[t][2]);

      if((layover_hour<0)&&(layover_min<0))
      	{   if(layover_day ==1)
      		{layover_min = layover_min+60;
       	 	layover_hour = layover_hour+23;}
       	 	else if(layover_day ==2){
       	 		layover_min = layover_min+60;
       	 	layover_hour = layover_hour+47;
       	 	}
         }

       else if((layover_hour<0)&&(layover_min>=0))
      
     			
     	{ if(layover_day ==1)
     		layover_hour = layover_hour+24;
          else if(layover_day ==2)
          	layover_hour = layover_hour+48;
     	}
 
     else if((layover_hour>=0)&&(layover_min<0))
     	{  if(layover_day == 0)
            {layover_hour = layover_hour-1;
     		layover_min= layover_min+60;}
     		else if (layover_day == 1)
              {
              	layover_hour = layover_hour+23;
               layover_min= layover_min+60;
           }
           else if (layover_day == 2)
           { layover_hour = layover_hour+47;
               layover_min= layover_min+60;

           }
     	}

     	else if((layover_hour>=0)&&(layover_min>=0))
     	{
     		if(layover_day ==1)
     			layover_hour = layover_hour+24;
     		else if (layover_day ==2)
     			layover_hour = layover_hour+48;
     	}

       var layover_1 = layover_hour+"h"+layover_min+"min";

            layover_final.push(layover_1);

      }
       
       if (flight_list.length!=1){
       	var layover_all = document.createElement('div');
       	layover_all.className = "layover_all";
       	layover_all.id = "layover_all"+i;
       	layover_all.innerHTML = "layover";
       	document.getElementById("result_time"+i).appendChild(layover_all);

       	var layover_all_time = document.createElement('div');
       	layover_all_time.className = "layover_all_time";
       	layover_all_time.id = "layover_all_time"+i;
        document.getElementById("result_time"+i).appendChild(layover_all_time);
        var layover_all_time_list ="";
        for(var p =0; p<layover_final.length; p++){
           layover_all_time_list = layover_all_time_list+layover_final[p]+"<br>";
        }
        layover_all_time.innerHTML = layover_all_time_list;

       }
//**********   zheming
}


}


function flight_Plan_Round(start,length,stopover,flightPlans){
	for(var i = start; i<length; i++){
		var coach_price = Math.round(flightPlans[i-start].getAttribute("Coach")*100)/100;
		
		var FirstClass_price = Math.round(flightPlans[i-start].getAttribute("FirstClass")*100)/100;
		var stop = flightPlans[i-start].getAttribute("Stopover");
		document.getElementById("firstClass_price"+i).innerHTML = "$"+FirstClass_price;
		document.getElementById("coach_price"+i).innerHTML = "$"+coach_price;
	//	document.getElementById("Results-LeftRow"+i).innerHTML = "PRICE:<br>"+"Coach<br><br>"+"$"+coach_price+"<br><br>"+"FirstClass<br><br>"+"$"+FirstClass_price;
	
     	var FlightNmuber_list = [];
		var FirstClass_left_list = [];
		var Coach_left_list = [];
	     var flag_1 = true;
	     var flag_2 = true;
//********  zheming
         var flightTime_total = 0;
         var layover_arrive_list = [];
         var layover_dep_list = [];

//*******  zheming
var dep_flight_list = flightPlans[i-start].firstChild.getElementsByTagName("Flight");
for (var j = 0; j<dep_flight_list.length; j++){
//**********  zheming

    var arr_once = [];
    var dep_once = [];
	if(j==0){
   var arrTime_only = dep_flight_list[j].firstChild.nextSibling.lastChild.textContent.split(" ")[2];
   var  arrTime_day = dep_flight_list[j].firstChild.nextSibling.lastChild.textContent.split(" ")[0];
   var  arrTime_hour = arrTime_only.split(":")[0];
   var arrTime_min = arrTime_only.split(":")[1];

   var arr_once_1 = [];
   
      arr_once_1.push(arrTime_hour);
      arr_once_1.push(arrTime_min);
      arr_once_1.push(arrTime_day);
      layover_arrive_list.push(arr_once_1);
	}

//********  zheming
 var result_center_show = document.createElement('div');
 result_center_show.id = "Results-CenterShowRow"+i+j;
 result_center_show.className = "Results-CenterShowRow";
  document.getElementById("Results-CenterRow"+i).appendChild(result_center_show);
  
   var result_center_dep = document.createElement('span');
   result_center_dep.className = "Results-center-dep";
   var dep = dep_flight_list[j].firstChild.firstChild.textContent;
   result_center_dep.innerHTML = dep;
  
   var result_center_arr = document.createElement('span');
   result_center_arr.className = "Results-center-arr";
   var arr =dep_flight_list[j].firstChild.nextSibling.firstChild.textContent;
   result_center_arr.innerHTML = arr;
  
   var result_center_depTime = document.createElement('span');
   result_center_depTime.className = "Results-center-depTime";
   var depTime = dep_flight_list[j].firstChild.lastChild.textContent;
   result_center_depTime.innerHTML = depTime;
  
   var result_center_arrTime = document.createElement('span');
   result_center_arrTime.className = "Results-center-arrTime";
  var arrTime = dep_flight_list[j].firstChild.nextSibling.lastChild.textContent;
   result_center_arrTime.innerHTML = arrTime;
  
   var result_center_label = document.createElement('span');
   result_center_label.className = "Results-center-label";
    result_center_label.innerHTML = "&#8594";

    var total_Time = document.createElement('span');
    total_Time.className = "Results-center-totalTime";
    var totalMin = parseInt(dep_flight_list[j].getAttribute("FlightTime"));
    var total_Time_hour;

    flightTime_total = flightTime_total + totalMin;
    if(totalMin<60) 
    {
    	total_Time_hour = totalMin+"min";
    }
    else{
    var totalHour = Math.floor(totalMin/60);
    var remainMin = totalMin-60*totalHour;
    total_Time_hour = totalHour+"h"+remainMin+"min";
    }
   total_Time.innerHTML = total_Time_hour;
//**********  zheming
    if((j!=0)&&(j!=dep_flight_list.length-1))
   {
   var depTime_only = depTime.split(" ")[2];
   var  depTime_hour = depTime_only.split(":")[0];
   var depTime_min = depTime_only.split(":")[1];
   var depTime_day = depTime.split(" ")[0];

   var arr_temp = arrTime.split(" ")[2];
   var arr_temp_hour = arr_temp.split(":")[0];
   var arr_temp_min = arr_temp.split(":")[1];
   var arr_temp_day = arrTime.split(" ")[0];

   dep_once.push(depTime_hour);
   dep_once.push(depTime_min);
   dep_once.push(depTime_day);
   arr_once.push(arr_temp_hour);
   arr_once.push(arr_temp_min);
   arr_once.push(arr_temp_day);
   layover_arrive_list.push(arr_once);
   layover_dep_list.push(dep_once);
   }
   if(j == dep_flight_list.length-1)
 {
	var depTime_only_1 = depTime.split(" ")[2];
   var  depTime_hour_1 = depTime_only_1.split(":")[0];
   var depTime_min_1 = depTime_only_1.split(":")[1];
   var depTime_day_1 = depTime.split(" ")[0];
     dep_once.push(depTime_hour_1);
   dep_once.push(depTime_min_1);
   dep_once.push(depTime_day_1);
    layover_dep_list.push(dep_once);

 } 
 //**********  zheming
    document.getElementById("Results-CenterShowRow"+i+j).appendChild(result_center_depTime);
	document.getElementById("Results-CenterShowRow"+i+j).appendChild(result_center_dep);
	document.getElementById("Results-CenterShowRow"+i+j).appendChild(result_center_label);
	document.getElementById("Results-CenterShowRow"+i+j).appendChild(result_center_arrTime);
	document.getElementById("Results-CenterShowRow"+i+j).appendChild(result_center_arr);
	 document.getElementById("Results-CenterShowRow"+i+j).appendChild(total_Time);

	var FlightNumber = dep_flight_list[j].getAttribute("Number");
      FlightNmuber_list.push(FlightNumber);

      var FirstClass_left = dep_flight_list[j].lastChild.firstChild.textContent;
     var Coach_left = dep_flight_list[j].lastChild.firstChild.nextSibling.textContent;
   FirstClass_left_list.push(FirstClass_left);
//FirstClass_left_list.push("0");
   Coach_left_list.push(Coach_left);

 }
//*********  zheming
 if(dep_flight_list.length ==1){
   layover_dep_list = [];
   layover_arrive_list = [];
 }
 //******  zheming
 var laber_1 = document.createElement('hr')
document.getElementById("Results-CenterRow"+i).appendChild(laber_1);


var return_flight_list = flightPlans[i-start].firstChild.nextSibling.getElementsByTagName("Flight");

for (var j = dep_flight_list.length; j<dep_flight_list.length+return_flight_list.length; j++){

	//************ zheming
var arr_once = [];
    var dep_once = [];
	if(j==dep_flight_list.length){
   var arrTime_only = return_flight_list[j-dep_flight_list.length].firstChild.nextSibling.lastChild.textContent.split(" ")[2];
   var  arrTime_hour = arrTime_only.split(":")[0];
   var arrTime_min = arrTime_only.split(":")[1];
   var arrTime_day = return_flight_list[j-dep_flight_list.length].firstChild.nextSibling.lastChild.textContent.split(" ")[0];
   var arr_once_1 = [];
   
      arr_once_1.push(arrTime_hour);
      arr_once_1.push(arrTime_min);
      arr_once_1.push(arrTime_day);
      layover_arrive_list.push(arr_once_1);
	}

//*************** zheming

 var result_center_show = document.createElement('div');
 result_center_show.id = "Results-CenterShowRow"+i+j;
 result_center_show.className = "Results-CenterShowRow";
  document.getElementById("Results-CenterRow"+i).appendChild(result_center_show);
   var result_center_dep = document.createElement('span');
   result_center_dep.className = "Results-center-dep";
   var dep = return_flight_list[j-dep_flight_list.length].firstChild.firstChild.textContent;
   result_center_dep.innerHTML = dep;
   var result_center_arr = document.createElement('span');
   result_center_arr.className = "Results-center-arr";
   var arr =return_flight_list[j-dep_flight_list.length].firstChild.nextSibling.firstChild.textContent;
   result_center_arr.innerHTML = arr;
   var result_center_depTime = document.createElement('span');
   result_center_depTime.className = "Results-center-depTime";
   var depTime = return_flight_list[j-dep_flight_list.length].firstChild.lastChild.textContent;
   result_center_depTime.innerHTML = depTime;
   var result_center_arrTime = document.createElement('span');
   result_center_arrTime.className = "Results-center-arrTime";
  var arrTime = return_flight_list[j-dep_flight_list.length].firstChild.nextSibling.lastChild.textContent;
   result_center_arrTime.innerHTML = arrTime;
   var result_center_label = document.createElement('span');
   result_center_label.className = "Results-center-label";
    result_center_label.innerHTML = "&#8594";

    var total_Time = document.createElement('span');
    total_Time.className = "Results-center-totalTime";
    var totalMin = parseInt(return_flight_list[j-dep_flight_list.length].getAttribute("FlightTime"));
    var total_Time_hour;
     flightTime_total = flightTime_total + totalMin;
    if(totalMin<60) 
    {
    	total_Time_hour = totalMin+"min";
    }
    else{
    var totalHour = Math.floor(totalMin/60);
    var remainMin = totalMin-60*totalHour;
    total_Time_hour = totalHour+"h"+remainMin+"min";
    }
   total_Time.innerHTML = total_Time_hour;

   //*********  zheming
    if((j!=dep_flight_list.length)&&(j!=dep_flight_list.length+return_flight_list.length-1))
   {
   var depTime_only = depTime.split(" ")[2];
   var  depTime_hour = depTime_only.split(":")[0];
   var depTime_min = depTime_only.split(":")[1];
   var depTime_day = depTime.split(" ")[0];

   var arr_temp = arrTime.split(" ")[2];
   var arr_temp_hour = arr_temp.split(":")[0];
   var arr_temp_min = arr_temp.split(":")[1];
   var arr_temp_day = arrTime.split(" ")[0];

   dep_once.push(depTime_hour);
   dep_once.push(depTime_min);
   dep_once.push(depTime_day);
   arr_once.push(arr_temp_hour);
   arr_once.push(arr_temp_min);
   arr_once.push(arr_temp_day);
   layover_arrive_list.push(arr_once);
   layover_dep_list.push(dep_once);
   }
   if(j == dep_flight_list.length+return_flight_list.length-1)
 {
	var depTime_only_1 = depTime.split(" ")[2];
   var  depTime_hour_1 = depTime_only_1.split(":")[0];
   var depTime_min_1 = depTime_only_1.split(":")[1];
   var depTime_day_1 = depTime.split(" ")[0];
     dep_once.push(depTime_hour_1);
   dep_once.push(depTime_min_1);
   dep_once.push(depTime_day_1);
    layover_dep_list.push(dep_once);

 } 

 //********* zheming
    document.getElementById("Results-CenterShowRow"+i+j).appendChild(result_center_depTime);
	document.getElementById("Results-CenterShowRow"+i+j).appendChild(result_center_dep);
	document.getElementById("Results-CenterShowRow"+i+j).appendChild(result_center_label);
	document.getElementById("Results-CenterShowRow"+i+j).appendChild(result_center_arrTime);
	document.getElementById("Results-CenterShowRow"+i+j).appendChild(result_center_arr);
	 document.getElementById("Results-CenterShowRow"+i+j).appendChild(total_Time);

var FlightNumber_return = return_flight_list[j-dep_flight_list.length].getAttribute("Number");
	FlightNmuber_list.push(FlightNumber_return);


	var FirstClass_left_return = return_flight_list[j-dep_flight_list.length].lastChild.firstChild.textContent;
     var Coach_left_return = return_flight_list[j-dep_flight_list.length].lastChild.firstChild.nextSibling.textContent;
   FirstClass_left_list.push(FirstClass_left_return);

   Coach_left_list.push(Coach_left_return);

 }
//***********  zheming
 if(return_flight_list.length ==1){
 	layover_arrive_list.pop();
 	layover_dep_list.pop();
 }

     if(flightTime_total<60){
    	document.getElementById("time_result"+i).innerHTML = flightTime_total+"min";
    }
    else{
    	var flightTime_total_hour = Math.floor(flightTime_total/60);
    	var flightTime_total_min = flightTime_total-60*flightTime_total_hour;
    	document.getElementById("time_result"+i).innerHTML = flightTime_total_hour+"h"+flightTime_total_min+"min";
    }
    //********  zheming
  var stopover1 = flightPlans[i-start].getAttribute("Stopover");
		document.getElementById("stop"+i).innerHTML = stopover1+" "+"stopover";
		flightPlanNumber_list.push(FlightNmuber_list);
		if(stopover1>=3) $('SearchResults'+i).height(300);
for(var m =0; m< FirstClass_left_list.length; m++){
     if( FirstClass_left_list[m] == "0")
     	{flag_1 = false;
     	break;}
} 

for(var n =0; n<Coach_left_list.length; n++){
     if( Coach_left_list[n] == "0")
     	{flag_2 = false;
     	break;}

}    
if(flag_1 == false)
	document.getElementById("firstClass_radio"+i).disabled = true;
if(flag_2 == false)
	document.getElementById("coach_radio"+i).disabled = true;
//*************  zheming
var layover_final = [];
if ((dep_flight_list.length!=1)||(return_flight_list.length!=1)){
  for(var t = 0; t<layover_dep_list.length; t++){
      var layover_hour = parseInt(layover_dep_list[t][0])-parseInt(layover_arrive_list[t][0]);
      var layover_min =  parseInt(layover_dep_list[t][1])-parseInt(layover_arrive_list[t][1]);
      var layover_day =  parseInt(layover_dep_list[t][2])-parseInt(layover_arrive_list[t][2]);

      if((layover_hour<0)&&(layover_min<0))
      	{   if(layover_day ==1)
      		{layover_min = layover_min+60;
       	 	layover_hour = layover_hour+23;}
       	 	else if(layover_day ==2){
       	 		layover_min = layover_min+60;
       	 	layover_hour = layover_hour+47;
       	 	}
         }

       else if((layover_hour<0)&&(layover_min>=0))
      
     			
     	{ if(layover_day ==1)
     		layover_hour = layover_hour+24;
          else if(layover_day ==2)
          	layover_hour = layover_hour+48;
     	}
 
     else if((layover_hour>=0)&&(layover_min<0))
     	{  if(layover_day == 0)
            {layover_hour = layover_hour-1;
     		layover_min= layover_min+60;}
     		else if (layover_day == 1)
              {
              	layover_hour = layover_hour+23;
               layover_min= layover_min+60;
           }
           else if (layover_day == 2)
           { layover_hour = layover_hour+47;
               layover_min= layover_min+60;

           }
     	}

     	else if((layover_hour>=0)&&(layover_min>=0))
     	{
     		if(layover_day ==1)
     			layover_hour = layover_hour+24;
     		else if (layover_day ==2)
     			layover_hour = layover_hour+48;
     	}
       var layover_1 = layover_hour+"h"+layover_min+"min";

            layover_final.push(layover_1);

      }
       
       
       	var layover_all = document.createElement('div');
       	layover_all.className = "layover_all";
       	layover_all.id = "layover_all"+i;
       	layover_all.innerHTML = "layover";
       	document.getElementById("result_time"+i).appendChild(layover_all);

       	var layover_all_time = document.createElement('div');
       	layover_all_time.className = "layover_all_time";
       	layover_all_time.id = "layover_all_time"+i;
        document.getElementById("result_time"+i).appendChild(layover_all_time);
        var layover_all_time_list ="";
        for(var p =0; p<layover_final.length; p++){
        //	var p_index = p+1;
           layover_all_time_list = layover_all_time_list+layover_final[p]+"<br>";
        }
        layover_all_time.innerHTML = layover_all_time_list;

       }
//********   zheming


}
}

//code for filter by Jarway ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function filterCheck(){
	$('#checkbox_0').change(function(){  
	if(!$(this).is(":checked")) {
		filter_flag[0] = 1;
		filter();
		return;
    }
    else if($(this).is(":checked")){
		filter_flag[0] = 0;
		filter_show();
		filter_flag[0] = NaN;
		return;
	}	
	});
	
	$('#checkbox_1').change(function(){  
	if(!$(this).is(":checked")) {
		filter_flag[1]= 1;
		filter();
		return;
    }
    else if($(this).is(":checked")){
		filter_flag[1] = 0;
		filter_show();
		filter_flag[1] = NaN;
		return;
	}	
	});

	$('#checkbox_2').change(function(){  
	if(!$(this).is(":checked")) {
		filter_flag[2] = 1;
		filter();
		return;
    }
    else if($(this).is(":checked")){
		filter_flag[2] = 0;
		filter_show();
		filter_flag[2] = NaN;
		return;
	}	
	});
	
	$('#checkbox_3').change(function(){  
	if(!$(this).is(":checked")) {
		filter_flag[3] = 1;
		filter();
		return;
    }
    else if($(this).is(":checked")){
		filter_flag[3] = 0;
		filter_show();
		filter_flag[3] = NaN;
		return;
	}
	});
	
	$('#checkbox_4').change(function(){  
	if(!$(this).is(":checked")) {
		filter_flag[4] = 1;
		filter();
		return;
    }
    else if($(this).is(":checked")){
		filter_flag[4] = 0;
		filter_show();
		filter_flag[4] = NaN;
		return;
	}
	});
	
	$('#checkbox_5').change(function(){  
	if(!$(this).is(":checked")) {
		filter_flag[5] = 1;
		filter();
		return;
    }
    else if($(this).is(":checked")){
		filter_flag[5] = 0;
		filter_show();
		filter_flag[5] = NaN;
		return;
	}
	});
	
	$('#checkbox_6').change(function(){  
	if(!$(this).is(":checked")) {
		filter_flag[6] = 1;
		filter();
		return;
    }
    else if($(this).is(":checked")){
		filter_flag[6] = 0;
		filter_show();
		filter_flag[6] = NaN;
		return;
	}
	});
	
	$('#checkbox_7').change(function(){  
	if(!$(this).is(":checked")) {
		filter_flag[7] = 1;
		filter();
		return;
    }
    else if($(this).is(":checked")){
		filter_flag[7] = 0;
		filter_show();
		filter_flag[7] = NaN;
		return;
	}
	});



	
}

var flight_hide = new Array();
flight_hide[9] = $('.Flight');

	function filter(){
		var flight_filter = $(".Flight").not($('.Flight:hidden'));
		var flight_fit = $(".Flight").not($('.Flight:hidden'));
		for(var i=0; i<8; i++)
		{
			if(filter_flag[i] == 1)
			{
				switch(i)
				{
					case 0:
						flight_filter = flight_fit.filter(function(){
						return $(".stop",this).text() == "0 stopover";
						});
						 
						break;
					case 1:
						flight_filter = flight_fit.filter(function(){
						return $(".stop",this).text() == "1 stopover";
						}); 
						 
						break;
					case 2:
						flight_filter = flight_fit.filter(function(){
						return $(".stop",this).text() == "2 stopover";
						}); 
						 
						break;
					case 3:
						flight_filter = flight_fit.filter(function(){
						return $(".stop",this).text() == "3 stopover";
						}); 
						 
						break;
					case 4:
						flight_filter = flight_fit.filter(function(){
						var dep_time = $(".Results-center-depTime",this).eq(0).text().split(" ")[2].split(":")[0];
						return (dep_time > 7 && dep_time <= 12)
						});
						 
						break;
					case 5:
						flight_filter = flight_fit.filter(function(){
						var dep_time = $(".Results-center-depTime",this).eq(0).text().split(" ")[2].split(":")[0];
						return (dep_time > 12 && dep_time <= 18)
						});
						 
						break;
					case 6:
						flight_filter = flight_fit.filter(function(){
						var dep_time = $(".Results-center-depTime",this).eq(0).text().split(" ")[2].split(":")[0];
						return (dep_time > 18 && dep_time <= 22)
						});
						 
						break;
					case 7:
						flight_filter = flight_fit.filter(function(){
						var dep_time = $(".Results-center-depTime",this).eq(0).text().split(" ")[2].split(":")[0];
						return (dep_time > 22 || dep_time <= 7)
						});
						 
						break;	
					default:
						return;
				}
				flight_filter.hide(500);
				flight_hide[i] = flight_filter;
			}
		}
	}
		
		function filter_show(){
		for(var j=0; j<8; j++)
		{
			if(filter_flag[j] == 0)
			{
				for(var i=0;i<8;i++)
				{
					if(filter_flag[i]==1){
				switch(i)
				{
					case 0:
						flight_hide[j] = flight_hide[j].filter(function(){
						return $(".stop",this).text() != "0 stopover";
						});
						break;
					case 1:
						flight_hide[j] = flight_hide[j].filter(function(){
						return $(".stop",this).text() != "1 stopover";
						}); 
						 
						break;
					case 2:
						flight_hide[j] = flight_hide[j].filter(function(){
						return $(".stop",this).text() != "2 stopover";
						}); 
						 
						break;
					case 3:
						flight_hide[j] = flight_hide[j].filter(function(){
						return $(".stop",this).text() != "3 stopover";
						}); 
						 
						break;
					case 4:
						flight_hide[j] = flight_hide[j].filter(function(){
						var dep_time = $(".Results-center-depTime",this).eq(0).text().split(" ")[2].split(":")[0];
						return (dep_time <= 7 || dep_time > 12)
						});
						 
						break;
					case 5:
						flight_hide[j] = flight_hide[j].filter(function(){
						var dep_time = $(".Results-center-depTime",this).eq(0).text().split(" ")[2].split(":")[0];
						return (dep_time <= 12 || dep_time > 18)
						});
						 
						break;
					case 6:
						flight_hide[j] = flight_hide[j].filter(function(){
						var dep_time = $(".Results-center-depTime",this).eq(0).text().split(" ")[2].split(":")[0];
						return (dep_time <= 18 || dep_time > 22)
						});
						 
						break;
					case 7:
						flight_hide[j] = flight_hide[j].filter(function(){
						var dep_time = $(".Results-center-depTime",this).eq(0).text().split(" ")[2].split(":")[0];
						return (dep_time <= 22 && dep_time > 7)
						});
						 
						break;	
					default:
						return;
				}
				}
				}
				flight_hide[j].show(500);			
		} 
	}
	
}


	//Xu Han ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function checkHasFlight(){
	
	var size = $('.Flight:hidden').size() + $('.Flight').size();
	
	
	$('.Flight').each(function(){
		if($(this).is(":hidden")) size = size -1;

	});
	
	
	
	if(size==0&&$('.Flight').size()!=0) {
		$('#head-message').text("Sorry! No Flights for your requirements");

	}
	else{
		$('#head-message').text("The Hobbits have found "+ size + " Results!",1000);
	}
	
		
}


setInterval(checkHasFlight,1*800);



function sortByPrice() {
	

	var keySelector = ".coach_price";
	if($('#checkbox_10').is(":checked")||$('#checkbox_9').is(":checked")||!$('#checkbox_8').is(":checked")) keySelector = ".coach_price";
	else keySelector = ".firstClass_price";
    var divisions = $('.Flight').sort(function(a, b) {
       	
		
	    var price_str1 = $(keySelector,a).text();
		var price_str2 = $(keySelector,b).text();
        var price1 = parseFloat(price_str1.substring(1,price_str1.length));
        var price2 = parseFloat(price_str2.substring(1,price_str2.length));
		


        return (price1 < price2) ? -1 : (price1 > price2) ? 1 : 0;
    });
	if($('#checkbox_11').is(":checked")) sortByFlightTime(divisions);
   else  $('#DisplaySearchResults').append(divisions);
}


function sortByFlightTime(items) {
	

	var keySelector = ".time_result";
    var divisions = items.sort(function(a, b) {
       	
		
	    var totaltime_str1 = $(keySelector,a).text();
		var totaltime_str2 = $(keySelector,b).text();
		var hour1 = totaltime_str1.split("h")[0];
		var hour2 = totaltime_str2.split("h")[0];
		
		var min1 = totaltime_str1.split("h")[1].split("min")[0];
		var min2 = totaltime_str2.split("h")[1].split("min")[0];
		
        var totaltime1 = Number(hour1)*60 + Number(min1);
        var totaltime2 = Number(hour2)*60 + Number(min2);
	
        return (totaltime1 < totaltime2) ? -1 : (totaltime1 > totaltime2) ? 1 : 0;
    });

    $('#DisplaySearchResults').append(divisions);

}



//setInterval(sortByPrice,1*5*1000);





	var current_num=1;
	var current_tags
        function paginate(pag_divs){

        	var itemsOnPage = 10;
        
        	current_tags = $('.pagination').find("ul").find("li").size();
        	var numOfPages = pag_divs.size()/itemsOnPage;
        	if(numOfPages>current_tags){
        		for (var i = current_tags+1; i <= numOfPages+1; i++) {
        			$('.pagination').find("ul").append("<li>"+ i +"</li>");
        		}
        	}

        	$('.pagination').find("ul li").on('click',function(){
        		current_num = $(this).text();
        	});

			var itemsToHide= pag_divs.filter(":lt("+(itemsOnPage*(current_num-1))+")");
				      
		    $.merge(itemsToHide, pag_divs.filter(":gt("+(itemsOnPage*current_num-1)+")"));
			itemsToHide.hide();
			// var hiddenByFilter = flight_hide[0];

			 for (var i = 0; i < 8; i++) {
				if (undefined !== itemsToHide && itemsToHide.length && undefined !== flight_hide[i] && flight_hide[i].length)  $.merge(itemsToHide, flight_hide[i]);
			 };

			
		    var itemsToShow = pag_divs.not(itemsToHide);
			itemsToShow.show();


        }

function controlDivs(){
	// if(ready!=1){
	// $('.Flight:hidden').show();
	filterCheck();
	sortByPrice();
	paginate($('.Flight'));
// }

}
setInterval(controlDivs,1*1000);

	//Xu Han ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
