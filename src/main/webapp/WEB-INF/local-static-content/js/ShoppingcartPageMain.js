(function($){
	
	requirejs(
		["/resources/js/shopping/Shopping.js"], function(shopping){
			
			$(".shopping_cart_container").mask("<img src='/resources/css/img/loading_dark_large.gif' style='width:60px' />");
			
			$(".shopping_cart_container").load("/fragment/sp/shoppingcart", function(){
				$(".shopping_cart_container").unmask();
			});
		}
	
	);
	
	
})(jQuery);