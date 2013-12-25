(function($){
	
	requirejs(
			["/resources/js/user/Address.js"], function(address){
				
				var addressListCallBack =  function(change){
				};
				
				$("#addAddress").click(function(e){
					
					address.saveOrupdateAddress(0, function(addr){
						//alert(addr.id);
						window.location.href=window.location.protocol + "//"+window.location.host + "/ac/address";
					});
					
					e.preventDefault();
				});
			}
		
		);
	
	
})(jQuery);