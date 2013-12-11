(function($){
	
	requirejs(
		["/resources/js/user/ForgotPassword.js"], function(forgotpassword){
			
			var sendEmailCallBack =  function(sendEmail){
				console.log("callBack");
				block=$("#forgotblock")[0];
				/*<div class="alert alert-warning fade in">
		        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		        <strong>Holy guacamole!</strong> Best check yo self, you're not looking too good.
		      </div>*/
				sendEmail.responseText;
			};
			
			$("#sendEmail").click(function(){
				var valid = $("#forgotpasswordform")[0].checkValidity();
				if(valid) {
					var email = $("#forgotEmail").val();
					if(email != "") {
						console.log("valid");
						forgotpassword.sendemail(email, sendEmailCallBack);
					}
				}
			});
			/*$(".shopping_cart_container").load("/fragment/sp/shoppingcart", function(){
				$(".shopping_cart_container").unmask();
				
				shopping.initCardEvent();
				
			});*/
			
		}
	
	);
	
	
})(jQuery);