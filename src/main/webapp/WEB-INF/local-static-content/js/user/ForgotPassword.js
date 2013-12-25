(function($){
	define(
		function() {
			var forgotpassword = {};
			
			forgotpassword.sendemail = function(email, callBack){
				
				if(callBack){
					$.ajax({
						dataType : "json",
						url : "/ac/json/forgotpassword?time=" + new Date().getTime(),
						data : {email: email},
						//complete : callBack
						success : callBack
					});
				}
			};
			
	        return forgotpassword;
	    }
	);
})(jQuery);
