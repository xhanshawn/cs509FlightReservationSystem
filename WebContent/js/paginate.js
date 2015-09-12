(function($){
	

		
		$.fn.customPaginate = function(options)
		{  
			var paginationContainer=this;
			var itemsToPaginate;
			
			var defaults = {
					
				itemsPerPage : 5
				
			};
			
			var settings = {};
			
			$.extend(settings, defaults, options);
			
			itemsToPaginate = $(settings.itemsToPaginate);
			var numberOfPaginationLinks = Math.ceil((itemsToPaginate.length / settings.itemsPerPage));
			
			$("<ul></ul>").prependTo(paginationContainer);
			
			for(var index=0; index < numberOfPaginationLinks; index++)
				{
				      paginationContainer.find("ul").append("<li>"+ (index+1) +"</li>");
				}
			
			itemsToPaginate.filter(":gt("+(settings.itemsPerPage-1)+")").hide();
			
			paginationContainer.find("ul li").on('click',function(){
				
				     
				      var linkNumber = $(this).text();
				      
				      var itemsToHide= itemsToPaginate.filter(":lt("+((linkNumber-1)* settings.itemsPerPage)+")");
				      
				      $.merge(itemsToHide, itemsToPaginate.filter(":gt("+((linkNumber * settings.itemsPerPage)-1)+")"));
				      itemsToHide.hide();
				      
				      var itemsToShow = itemsToPaginate.not(itemsToHide);
				      itemsToShow.show();
			});
		}

	
}(jQuery));