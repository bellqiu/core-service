(function($){

	(function() {
		var po = document.createElement('script');
		po.type = 'text/javascript'; po.async = true;
		po.src = 'https://apis.google.com/js/client:plusone.js?onload=render';
		var s = document.getElementsByTagName('script')[0];
		s.parentNode.insertBefore(po, s);
	})();

	render = function render() {

		var additionalParams = {
			 'callback': signinCallback,
		     'clientid' : '573965665808.apps.googleusercontent.com',
		     'cookiepolicy' : 'single_host_origin',
		     'scope' : 'https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email',
		     'requestvisibleactions' : 'http://schemas.google.com/AddActivity',
		     'accesstype' : 'offline',
		     'approvalprompt' : 'force'
		};

		// Attach a click listener to a button to trigger the flow.
		$("#googleLoginId").click(function(){
			gapi.auth.signOut();
			gapi.auth.signIn(additionalParams);
		});
		
		function signinCallback(authResult) {
			if (authResult['status']['signed_in']) {
				window.location.href=window.location.protocol+"//"+window.location.host + "/ac/login?type=google&token=" + authResult.access_token;
			} else {
				console.log('Sign-in state: ' + authResult['error']);
			}
		}
	}
	
})(jQuery);