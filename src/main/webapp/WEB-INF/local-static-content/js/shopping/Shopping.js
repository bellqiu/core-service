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
						url : "/json/productChange/"+productName,
						data : {productOpts: paramStr},
						success : callBack
						
					});
					/*$.getJSON("/json/productChange/"+productName, callBack);*/
				}
					
				$.removeCookie('productOpts');
				$.cookie("productOpts", paramStr, { expires: 7, path: '/'+ productName} );
				
			};
		
			
	        return shopping;
	    }
	);
})(jQuery);
