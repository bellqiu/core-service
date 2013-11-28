(function($){
	
	requirejs(
		["/resources/js/shopping/Shopping.js"], function(shopping){
			
			$(".product_opts_select").each(function(index, el){
				
				$(el).change(function(){
					var itemId = $(el).attr("name");
					var itemValue =  $(el).val();
					shopping.selectOption(productName, itemId , itemValue);
					
				});
			});
			
			$(".product_opts_input_wrap").each(function(index, el){
				
				var input = $(el).children("input.product_opts_input");
				
				$(el).click(function(){
					var itemId = $(input).attr("name");
					var itemValue =  $(input).val();
					shopping.selectOption(productName, itemId , itemValue);
					
				});
			});
			
			//console.log(productOpt);
			
		}
	
	);
	
	
})(jQuery);