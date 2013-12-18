(function($){
	
		$(".bodyContent").ready(function(){
			$('#signUpOrLoginTab a').click(function (e) {
			  // e.preventDefault();
			    $(this).tab('show');
			    return false;
			}); 
			$("input,select,textarea").not("[type=submit]").jqBootstrapValidation();
		});

		$(document).ready(function(){
			var anchor = window.location.hash;
			if(!anchor && isSignUpPage){
				$('#signUpOrLoginTab a[href=#signUpTab]').tab('show') 
			}
			$('#signUpOrLoginTab a[href="'+anchor+'"]').tab('show') 
		}); 


		$("#sign-up").click(function(){
			var pwd = $("#regPassword").val();
			var pwdAgain = $("#passwordAgain").val();
			if(pwd != pwdAgain) {
				$("#pwdAgainBlock").html("<ul role='alert' class='alert alert-danger fade in'><li><strong>These passwords don't match.</strong></li></ui>");
				return false;
			}
		});

})(jQuery);