(function($){
	
		$(".bodyContent").ready(function(){
			$('#signUpOrLoginTab a').click(function (e) {
			  // e.preventDefault();
			    $(this).tab('show');
			    return false;
			}); 
			$("input,select,textarea").not("[type=submit]").jqBootstrapValidation();
			
			$('#sign-in').on('click', function() {
				if(ga) {
					try {
						ga('send', 'event', 'button', 'click', 'signin');
					} catch(e) {
						console.log("send signin with error");
					}
				}
			});
			$('#sign-up').on('click', function() {
				if(ga) {
					try {
						ga('send', 'event', 'button', 'click', 'signup');
					} catch(e) {
						console.log("send signup with error");
					}
				}
			});
		});

		$(document).ready(function(){
			var anchor = window.location.hash;
			if(!anchor && isSignUpPage){
				$('#signUpOrLoginTab a[href=#signUpTab]').tab('show');
			}
			$('#signUpOrLoginTab a[href="'+anchor+'"]').tab('show');
		});
		
		

})(jQuery);