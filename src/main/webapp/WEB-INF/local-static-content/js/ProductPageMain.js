(function($){
	
	requirejs(
		["/resources/js/shopping/Shopping.js"], function(shopping){
			
			var productChangeCallBack =  function(productChange){
                $(".productChangedPrice").hide();
				if(productChange.priceChange > 0.1 || productChange.priceChange <  -0.1){
					//$(".productChangedPrice").html("+" + productChange.strPriceChange);
                    $("#finalPriceOrigin").html(productChange.strPriceOriginFinal);
                    $("#finalPriceSale").html(productChange.strPriceSaleFinal);
					//$(".productChangedPrice").show();
				/*}else if (productChange.priceChange <  -0.1){
					$(".productChangedPrice").html(productChange.strPriceChange);
					//$(".productChangedPrice").show();  */
				}else{
					$(".productChangedPrice").hide();
				}
				$(".productOptionRegion").unmask();
			};
			
			$(".product_opts_select").each(function(index, el){
				
				$(el).change(function(){
					$(".productOptionRegion").mask("<img src='/resources/css/img/loading_dark_large.gif' style='width:60px' />");
					var itemId = $(el).attr("name");
					var itemValue =  $(el).val();
					shopping.selectOption(productName, itemId , itemValue, productChangeCallBack);
					
				});
			});
			
			$(".product_opts_input_wrap").each(function(index, el){
				
				var input = $(el).children("input.product_opts_input");
				
				$(el).click(function(){
					$(".productOptionRegion").mask("<img src='/resources/css/img/loading_dark_large.gif' style='width:60px' />");
					var itemId = $(input).attr("name");
					var itemValue =  $(input).val();
					shopping.selectOption(productName, itemId , itemValue, productChangeCallBack);
					
				});
			});
			
			$("#add2cartButton").click(function(){
				$(this).button('loading');
				var productAmount = parseInt($("#productAmountCustom").val());
				if(!productAmount){
					productAmount = 1;
				}
				$("#productNameSubmitInput").val(productName);
				$("#productOptsSubmitInput").val(productOpts);
				$("#productAmountSubmitInput").val(productAmount);
				$("#add2cartForm").submit();
			});
			
			$(".likeSpan").click(function() {
				var id = $(this).attr("data-id");
				shopping.like(id);
			});
			
		}
	
	);
	
	$("#decreaseQty").click(function(btn) {
		var qty = $("#productAmountCustom").val();
		var qtyNum = parseInt(qty);
		if(qtyNum > 1) {
			$("#productAmountCustom").val(qtyNum - 1);
		} else {
			$("#productAmountCustom").val(1);
		} 
		return false;
	});
	$("#increaseQty").click(function(btn) {
		var qty = $("#productAmountCustom").val();
		var qtyNum = parseInt(qty);
		if(qtyNum > 0) {
			$("#productAmountCustom").val(qtyNum + 1);
		} else {
			$("#productAmountCustom").val(1);
		}  
		return false;
	});
	
	/*$("#productAmountCustom").onchange(function(btn) {
		console.log("OK");
	})*/
	
})(jQuery);