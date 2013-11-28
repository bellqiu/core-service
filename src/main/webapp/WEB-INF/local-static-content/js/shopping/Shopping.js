(function($){
	define(
		function() {
			var shopping = {};
			
			shopping.selectOption = function(productName, optionId, value){
				
				var options = $.cookie("productOpts");
				
				var optsJson = {};
				
				if(options){
					var optionsArray = options.split("&");
					
					for(var i = 0 ; i < optionsArray.length ; i++){
						var optionPair = optionsArray[i];
						var optArr = optionPair.split("=");
						if(optArr.length == 2){
							optsJson[optArr[0]] =  optArr[1];
						}
					}
				}
				
				optsJson[optionId] = value;
					
				$.removeCookie('productOpts');
				$.cookie("productOpts", $.param(optsJson), { expires: 7, path: '/'+ productName} );
				
			};
			
	        return shopping;
	    }
	);
})(jQuery);
