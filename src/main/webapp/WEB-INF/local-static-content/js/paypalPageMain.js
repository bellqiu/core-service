(function($){
	
	requirejs(
		["/resources/js/shopping/Shopping.js"], function(shopping){
			
			$("#payByPaypal").click(function(){
				$(this).button("loading");
				
				shopping.checkout2Pending($(this).attr("data-orderId"));
				
			});
			
		}
	
	);
	
	
})(jQuery);