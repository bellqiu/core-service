(function($){
	
	requirejs(
		["/resources/js/shopping/Shopping.js", "/resources/js/user/Address.js"], function(shopping, userAdd){
			
			$("#new_shipping_address").click(function(e){
				
				userAdd.saveOrupdateAddress(0, null);
				
				e.preventDefault();
			});
		}
	
	);
	
	
})(jQuery);