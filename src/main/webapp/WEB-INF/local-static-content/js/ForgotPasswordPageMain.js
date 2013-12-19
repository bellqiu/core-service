(function($){
	
	requirejs(
		["/resources/js/user/ForgotPassword.js"], function(forgotpassword){
			
			var sendEmailCallBack =  function(sendEmail){
				if(sendEmail.status == "true") {
					$("#fogot-modal-body")[0].innerHTML="<div class='alert alert-success'>" + sendEmail.message +"</div>"
					$("#sendEmail").hide();
					
				} else {
					$("#forgotblock")[0].innerHTML="<ul role='alert' class='alert alert-danger fade in'><li><strong>" + sendEmail.message + "</strong></li></ui>";
				}
				$("#sendEmail").button('reset');
			};
			
			$("#sendEmail").click(function(){
				var valid = this.form.checkValidity();
				if(valid) {
					var email = $("#forgotEmail").val();
					if(email != "") {
						$(this).button('loading');
						forgotpassword.sendemail(email, sendEmailCallBack);
					}
				}
			});
		}
	
	);
	
	
})(jQuery);