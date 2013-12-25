(function($){
	
	requirejs(
			["/resources/js/user/Address.js"], function(address){
				
				var addressListCallBack =  function(change){
				};
				
				$("#addAddress").click(function(e){
					
					address.saveOrupdateAddress(0, function(addr){
						window.location.href=window.location.protocol + "//"+window.location.host + "/ac/address";
					});
					
					e.preventDefault();
				});
				
				$(document).ready(function(){
					$(".editAddress").each(function(index, el){
						var addressId = $(el).attr("data-addressId");
						$(el).click(function(){
							address.saveOrupdateAddress(addressId, function(addr){
								window.location.href=window.location.protocol + "//"+window.location.host + "/ac/address";
							});
							
						});
					});
					
					$(".deleteAddress").each(function(index, el){
						var addressId = $(el).attr("data-addressId");
						$(el).click(function(){
							var r = confirm("Are you sure want to delete this address?");
							if(r==true) {
								address.deleteAddress(addressId, function(addr){
									if(addr.success) {
										window.location.href=window.location.protocol + "//"+window.location.host + "/ac/address";
									} 
								});
							} else {
								// Nothing to do
							}
							
						});
					});
				});
			}
		
		);
	
	
})(jQuery);