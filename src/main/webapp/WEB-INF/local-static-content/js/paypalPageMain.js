(function($){
	
	requirejs(
		["/resources/js/shopping/Shopping.js"], function(shopping){
			
			$("body").mask("Go to paypal <img src='/resources/css/img/loading_dark_large.gif' style='width:60px' />");
			
			shopping.checkout2Pending($("#payByPaypal").attr("data-orderId"));
			
			
		/*	$("#payByPaypal").click(function(){
				$(this).button("loading");
				
				shopping.checkout2Pending($(this).attr("data-orderId"));
				
				
			});*/
			
		}
	
	);
	
	
})(jQuery);