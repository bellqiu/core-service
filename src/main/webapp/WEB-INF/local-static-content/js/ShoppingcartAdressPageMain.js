(function($){
	
	requirejs(
		["/resources/js/shopping/Shopping.js", "/resources/js/user/Address.js"], function(shopping, userAdd){
			
			function appendNewAddLine(orderId, address){
				var addressLine = address.firstName + " " + address.lastName + " (";
				
				addressLine = addressLine  + address.address1;
				
				if(address.address2){
					addressLine = addressLine  + " " + address.address2;
				}
				
				addressLine = addressLine  + ", " + address.city + ", "; 
				
				if(address.postalCode){
					addressLine = addressLine + address.postalCode + " ";
				}
				
				addressLine = addressLine + address.stateProvince + ", ";
				
				addressLine = addressLine + address.countryName + ")";
				
				$(".ShippingAddresslineContainer").append('<li><input  class="shippingAddress" data-order-id="'+orderId+'" type="radio" name="shipping_address_id" id="addr_'+address.id+'" value="'+address.id+'"><label for="addr_'+address.id+'"><strong>'+addressLine+'</strong></label>&nbsp;<a class="toEditShippingAddr" href="javascript:void(0)" data-address-id="'+address.id+'" data-order-id="'+orderId+'">Edit</a></li>');
				$(".BillingAddresslineContainer").append('<li><input  class="billingAddress" data-order-id="'+orderId+'" type="radio" name="billing_address_id" id="addr_'+address.id+'" value="'+address.id+'"><label for="addr_'+address.id+'"><strong>'+addressLine+'</strong></label>&nbsp;<a class="toEditBillingAddr" href="javascript:void(0)" data-address-id="'+address.id+'" data-order-id="'+orderId+'">Edit</a></li>');
				
				bindEditEvent();
			}
			
			function bindEditEvent(){
				$(".toEditShippingAddr").each(function(index, el){
					$(el).unbind();
					$(el).click(function(){
						userAdd.saveOrupdateAddress($(el).attr("data-address-id"), function(address){
							alert(address.id);
						});
						
					});
				});
				
				$(".toEditBillingAddr").each(function(index, el){
					$(el).unbind();
					$(el).click(function(){
						userAdd.saveOrupdateAddress($(el).attr("data-address-id"), function(address){
							alert(address.id);
						});
						
					});
				});
				
			}
			
			bindEditEvent();
			
			$("#new_shipping_address").click(function(e){
				
				var orderId = $("#new_shipping_address").attr("data-order-id");
				
				userAdd.saveOrupdateAddress(0, function(address){
					appendNewAddLine(orderId, address);
				});
				
				e.preventDefault();
			});
			
			
		}
	
	);
	
	
})(jQuery);