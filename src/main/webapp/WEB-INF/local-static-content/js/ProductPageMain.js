(function($){
	
	requirejs(
		["/resources/js/shopping/Shopping.js"], function(shopping){
			
			var productChangeCallBack =  function(productChange){
				if(productChange.priceChange > 0.1){
					$(".productChangedPrice").html("+" + productChange.strPriceChange);
				}else if (productChange.priceChange <  -0.1){
					$(".productChangedPrice").html(productChange.strPriceChange);
				}
			};
			
			$(".product_opts_select").each(function(index, el){
				
				$(el).change(function(){
					var itemId = $(el).attr("name");
					var itemValue =  $(el).val();
					shopping.selectOption(productName, itemId , itemValue, productChangeCallBack);
					
				});
			});
			
			$(".product_opts_input_wrap").each(function(index, el){
				
				var input = $(el).children("input.product_opts_input");
				
				$(el).click(function(){
					var itemId = $(input).attr("name");
					var itemValue =  $(input).val();
					shopping.selectOption(productName, itemId , itemValue, productChangeCallBack);
					
				});
			});
			
			//console.log(productOpt);
			
		}
	
	);
	
	
})(jQuery);