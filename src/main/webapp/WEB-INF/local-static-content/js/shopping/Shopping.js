(function($){
	define(
		function() {
			var shopping = {};
			
			shopping.selectOption = function(productName, optionId, value, callBack){
				
				var options = $.cookie("productOpts");
				
				var optsJson = {};
				
				if(options){
					var optionsArray = options.split("&");
					
					for(var i = 0 ; i < optionsArray.length ; i++){
						var optionPair = optionsArray[i];
						var optArr = optionPair.split("=");
						if(optArr.length == 2){
							optsJson[optArr[0]] =  decodeURIComponent(optArr[1]);
						}
					}
				}
				
				optsJson[optionId] = value;
				
				var paramStr =  $.param(optsJson);
				window.productOpts = paramStr;
				
				if(callBack){
					$.ajax({
						dataType : "json",
						url : "/json/productChange/"+productName + "?_tp=" + new Date().getTime(),
						data : {productOpts: paramStr},
						success : callBack
						
					});
					/*$.getJSON("/json/productChange/"+productName, callBack);*/
				}
					
				$.removeCookie('productOpts');
				$.cookie("productOpts", paramStr, { expires: 7, path: '/'+ productName} );
				
			};
			
			shopping.initCardEvent = function(){
				 $(".OrderItemIncrement").each(function(index, el){
						var itemId = $(el).attr("data-orderitemid");
						var changes = "1";
						shopping.modifyCart(itemId, changes, el);
				});
				 
				 $(".OrderItemDecrement").each(function(index, el){
						var itemId = $(el).attr("data-orderitemid");
						var changes = "-1";
						shopping.modifyCart(itemId, changes, el);
				});
				 
				 $(".removeItemFromOrder").each(function(index, el){
						var itemId = $(el).attr("data-orderitemid");
						var changes = "ALL";
						shopping.modifyCart(itemId, changes, el);
				});
			};
			
			shopping.modifyCart = function(itemId, changes, bindItem){
				$(bindItem).click(function(){
					
					$(".shopping_cart_container").mask("<img src='/resources/css/img/loading_dark_large.gif' style='width:60px' />");
					$(".shopping_cart_container").load("/fragment/sp/shoppingcart/modify?itemId="+itemId+"&changes="+changes, function(){
						$.ajax({
							url : "/sp/shoppingcart/itemCount?_tp="+new Date().getTime(),
							complete : function(response){
								var count = parseInt(response.responseText);
								if(count || count == 0){
									$("#ShoppingDockedbarCount").html("Cart("+count+")");
								}
							}
							
						});
						$(".shopping_cart_container").unmask();
						shopping.initCardEvent();
					});
				});
			};
			
			shopping.applyOrderAddress = function(orderid, addressId, addType ){
				$("#OrderAddressPanel").mask("<img src='/resources/css/img/loading_dark_large.gif' style='width:60px' />");
				$.ajax({
					url : "/sp/payment/updateOrderAdd?_tp="+new Date().getTime(),
					data : {"orderId" : orderid, "addressId": addressId, "addType" : addType ? addType : "SHIPPING"},
					complete : function(response){
						
						var json = null;
						
						try{
							json = JSON.parse(response.responseText);
						}catch(e){
							
						}
						
						if(json && json.success){
						
							if(addType="SHIPPING"){
								$("#saddr_"+addressId).attr("checked", "checked");
							}else{
								$("#baddr_"+addressId).attr("checked", "checked");
							}
							
							updateOrderPrice(json.messageMap);
						
						}else{
							alert("Failed");
						}
						
						$("#OrderAddressPanel").unmask();
						
						//alert(response.responseText);
					}
					
				});
			};
			
			shopping.applyShippingMethod = function(orderid, shippingMethod ){
				$("#ShippingMethodPanel").mask("<img src='/resources/css/img/loading_dark_large.gif' style='width:60px' />");
				
				$.ajax({
					url : "/sp/payment/updateShippingMethod?_tp="+new Date().getTime(),
					data : {"orderId" : orderid, "shippingMethod" : shippingMethod ? shippingMethod : "NORMAL"},
					complete : function(response){
						var json = null;
						
						try{
							json = JSON.parse(response.responseText);
						}catch(e){
							
						}
						
						if(json && json.success){
							updateOrderPrice(json.messageMap);
						}else{
							alert("Failed");
						}
						
						$("#ShippingMethodPanel").unmask();
					}
				});
			};
			
			function updateOrderPrice(changedPrice){
				$("#orderShippingMethodNormalSpan").html(changedPrice.normalDeliverPrice);
				$("#orderShippingMethodExpeditedSpan").html(changedPrice.expeditedDeliverPrice);
				$("#orderItemTotalSpan").html(changedPrice.orderProductTotal);
				$("#orderShippingCostSpan").html(changedPrice.shippingCost);
				$("#orderGrandTotalSpan").html(changedPrice.grandTotal);
				$("#orderCouponCutOffSpan").html(changedPrice.couponPrice);
			}
			
	        return shopping;
	    }
	);
})(jQuery);
