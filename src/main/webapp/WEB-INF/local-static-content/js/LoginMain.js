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
				$('#signUpOrLoginTab a[href=#signUpTab]').tab('show');
			}
			$('#signUpOrLoginTab a[href="'+anchor+'"]').tab('show');
		}); 

})(jQuery);