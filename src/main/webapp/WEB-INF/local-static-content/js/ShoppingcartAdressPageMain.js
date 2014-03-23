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
				
				$(".ShippingAddresslineContainer").append('<li><input  class="shippingAddress" data-order-id="'+orderId+'" type="radio" checked="checked" name="shipping_address_id" id="addr_'+address.id+'" value="'+address.id+'"><label for="saddr_'+address.id+'"><strong>'+addressLine+'</strong></label>&nbsp;<a class="toEditShippingAddr" href="javascript:void(0)" data-address-id="'+address.id+'" data-order-id="'+orderId+'">Edit</a></li>');
				$(".BillingAddresslineContainer").append('<li><input  class="billingAddress" data-order-id="'+orderId+'" type="radio" checked="checked" name="billing_address_id" id="addr_'+address.id+'" value="'+address.id+'"><label for="baddr_'+address.id+'"><strong>'+addressLine+'</strong></label>&nbsp;<a class="toEditBillingAddr" href="javascript:void(0)" data-address-id="'+address.id+'" data-order-id="'+orderId+'">Edit</a></li>');
				
				bindEditEvent();
			}
			
			function bindEditEvent(){
				$(".toEditShippingAddr").each(function(index, el){
					$(el).unbind();
					var orderId = $(el).attr("data-order-id");
					$(el).click(function(){
						userAdd.saveOrupdateAddress($(el).attr("data-address-id"), function(address){
							shopping.applyOrderAddress(orderId, address.id ,"SHIPPING");
						});
						
					});
				});
				
				$(".toEditBillingAddr").each(function(index, el){
					$(el).unbind();
					var orderId = $(el).attr("data-order-id");
					$(el).click(function(){
						userAdd.saveOrupdateAddress($(el).attr("data-address-id"), function(address){
							shopping.applyOrderAddress(orderId, address.id ,"BILLING");
						});
						
					});
				});
				
				$("input.shippingAddress[name='shipping_address_id']").each(function(index, el){
					$(el).unbind();
					var orderId = $(el).attr("data-order-id");
					$(el).click(function(){
							shopping.applyOrderAddress(orderId, $(el).val() ,"SHIPPING");
					});
				});
				
				$("input.billingAddress[name='billing_address_id']").each(function(index, el){
					$(el).unbind();
					var orderId = $(el).attr("data-order-id");
					$(el).click(function(){
						shopping.applyOrderAddress(orderId, $(el).val() ,"BILLING");
					});
				});
				
			}
			
			bindEditEvent();
			
			$("#new_shipping_address").click(function(e){
				
				var orderId = $("#new_shipping_address").attr("data-order-id");
				
				userAdd.saveOrupdateAddress(0, function(address){
					appendNewAddLine(orderId, address);
					shopping.applyOrderAddress(orderId, address.id ,"SHIPPING");
				});
				
				e.preventDefault();
			});

			$("#new_billing_address").click(function(e){
				
				var orderId = $("#new_billing_address").attr("data-order-id");
				
				userAdd.saveOrupdateAddress(0, function(address){
					appendNewAddLine(orderId, address);
					shopping.applyOrderAddress(orderId, address.id ,"BILLING");
				});
				
				e.preventDefault();
			});
			
			$("input[name=shippingMethod]").each(function(index, el){
				$(el).click(function(){
					var orderId = $(el).attr("data-order-id");
					shopping.applyShippingMethod(orderId, $(el).val());
				});
			});
			
			
			$("#paymentProcessToCheckoutBtn").click(function(){
				$(this).button("loading");
				var paymentMethod = $("input[name='paymentmethod'][checked='checked']").val();
				var customerMsg = $("textarea[name='orderMsg']").val();
				var orderId = $(this).attr("data-order-id");
				var shippingAddress = $("input[class='shippingAddress'][checked='checked']").val();
				var billingAddress = $("input[class='billingAddress'][checked='checked']").val();
				var shippingMethod = $("input[name='shippingMethod'][checked='checked']").val();
				
				if(!shippingAddress || shippingAddress.length < 1){
					$(this).popover({
						"content" :  "<p class='alert alert-danger'>Please select or add a shipping address</p>", 
					  	title : "Error",
					  	html : true,
						trigger : 'manual',
						placement : 'left'
					});
				
					$(this).popover('show');
					
					$(this).button("reset");
					
					setTimeout(function(){
						$("#paymentProcessToCheckoutBtn").popover("destroy");
					}, 3000);
				} else if(!billingAddress || billingAddress.length < 1){
					$(this).popover({
						"content" :  "<p class='alert alert-danger'>Please select or add a billing address</p>", 
					  	title : "Error",
					  	html : true,
						trigger : 'manual',
						placement : 'left'
					});
				
					$(this).popover('show');
					
					$(this).button("reset");
					
					setTimeout(function(){
						$("#paymentProcessToCheckoutBtn").popover("destroy");
					}, 3000);
				} else if(!shippingMethod || shippingMethod.length < 1){
					$(this).popover({
						"content" :  "<p class='alert alert-danger'>Please select or add a shipping method</p>", 
					  	title : "Error",
					  	html : true,
						trigger : 'manual',
						placement : 'left'
					});
				
					$(this).popover('show');
					
					$(this).button("reset");
					
					setTimeout(function(){
						$("#paymentProcessToCheckoutBtn").popover("destroy");
					}, 3000);
				} else if(!paymentMethod || paymentMethod.length < 1){
					$(this).popover({
						"content" :  "<p class='alert alert-danger'>Please select a payment method</p>", 
					  	title : "Error",
					  	html : true,
						trigger : 'manual',
						placement : 'left'
					});
				
					$(this).popover('show');
					
					$(this).button("reset");
					
					setTimeout(function(){
						$("#paymentProcessToCheckoutBtn").popover("destroy");
					}, 3000);
				}else{
					
					shopping.checkOrderForPayment(orderId, paymentMethod , customerMsg);
				}
				
				
			});
			
			
			
		}
	
	);
	
	
})(jQuery);