(function($){
	
	requirejs(
		["/resources/js/shopping/Shopping.js", "/resources/js/user/Address.js"], function(shopping, userAdd){
			
			$("#new_shipping_address").click(function(e){
				
				userAdd.saveOrupdateAddress(0, function(address){
					alert(address.id);
				});
				
				e.preventDefault();
			});
			
			$(".toEditShippingAddr").each(function(index, el){
				$(el).click(function(){
					userAdd.saveOrupdateAddress($(el).attr("data-address-id"), function(address){
						alert(address.id);
					});
					
				});
			});
		}
	
	);
	
	
})(jQuery);