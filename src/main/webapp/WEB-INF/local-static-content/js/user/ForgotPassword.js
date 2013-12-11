(function($){
	define(
		function() {
			var forgotpassword = {};
			
			forgotpassword.sendemail = function(email, callBack){
				
				if(callBack){
					$.ajax({
						dataType : "json",
						url : "/ac/json/forgotpassword",
						data : {email: email},
						complete : callBack
						
					});
				}
			};
			
	        return forgotpassword;
	    }
	);
})(jQuery);
