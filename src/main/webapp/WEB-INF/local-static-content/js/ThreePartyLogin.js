(function($){
	
	window.fbAsyncInit = function() {
		// Start for Facebook Login
		FB.init({
			appId      : '277233412302753',
			status     : true, // check login status
		    cookie     : true, // enable cookies to allow the server to access the session
		    xfbml      : true  // parse XFBML
		});
		
		FB.Event.subscribe('auth.authResponseChange', function(response) {
			if (response.status === 'connected') {
			}
		}); 
		
		$("#facebookLoginWithAnotherLinkId").click(function(){
			if(!FB.getAuthResponse()){	  
				FB.login(function(rs){
					if(rs.authResponse) {
						var userId = rs.authResponse.userID;
						var token = rs.authResponse.accessToken;
						login(userId, token);
					}
				},{scope: 'email,user_about_me,user_status'});
			}else{
				FB.logout(function(response) {
					FB.login(function(rs){
						if(rs.authResponse) {
							var userId = rs.authResponse.userID;
							var token = rs.authResponse.accessToken;
							login(userId, token);
						}
					},{scope: 'email,user_about_me,user_status'}); 
				});
			}
		});
		// End for Facebook Login
		
		// Start for Google Login
		var additionalParams = {
				'callback': signinCallback,
				'clientid' : '573965665808.apps.googleusercontent.com',
				'cookiepolicy' : 'single_host_origin',
				'scope' : 'https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email',
				'requestvisibleactions' : 'http://schemas.google.com/AddActivity',
				'accesstype' : 'offline',
				'approvalprompt' : 'force'
		};
		$("#googleLoginId").click(function(){
			gapi.auth.signIn(additionalParams);
		});
		function signinCallback(authResult) {
			if (authResult['status']['signed_in']) {
				window.location.href=window.location.protocol+"//"+window.location.host + "/ac/login?type=google&token=" + authResult.access_token;
			} else {
				console.log('Sign-in state: ' + authResult['error']);
			}
		};
		// End for Google Login
		
		// Start for LinkedIn Login
			
		/*IN.init({
			onLoad:"onLinkedInLoad",
			api_key:"7530oj8n5n1fan",
			authorize: false
		});*/
			
		IN.Event.on(IN, "auth", onLinkedInAuth);
		onLinkedInLoad = function onLinkedInLoad() {
		};
		$("#linkedInLogin").click(function(){
			if(!IN || !IN.User) {
				return false;
			}
			if(IN.User.isAuthorized()) {
				IN.User.logout(function(){
					IN.UI.Authorize().place();
				});
			} else {
				IN.UI.Authorize().params({immediate: false,authorize:false}).place();
			}
		});
			
		function onLinkedInAuth() {
			window.location.href=window.location.protocol+"//"+window.location.host + "/ac/login?type=linkedIn&token=" + IN.ENV.auth.oauth_token;
		};
	};
	
	// End for LinkedIn Login
	
	// Facebook login function
	function login(userID, token){
		console.log("userID:["+userID+"], "+"token:["+token+"]");
		FB.api("/"+userID+"?fields=email,username,name", function(response) { 
			console.log(response);
			console.log(domain);
			window.location.href=window.location.protocol+"//"+window.location.host + "/ac/login?type=facebook&userId=" + userID + "&token=" + token;  
		});
	}
	
	// Load the SDK asynchronously
	(function(d){
		var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
		if (!d.getElementById(id)) {
			js = d.createElement('script'); js.id = id; js.async = true;
			js.src = "//connect.facebook.net/en_US/all.js";
			ref.parentNode.insertBefore(js, ref);
		}
	   
		var po = d.createElement('script');
		po.type = 'text/javascript'; po.async = true;
		po.src = 'https://apis.google.com/js/client:plusone.js';
		ref.parentNode.insertBefore(po, ref);
	   
		id = 'linkedin-jssdk';
		if (!d.getElementById(id)) {
			js = d.createElement('script'); js.id = id; js.async = true;
			js.src = "//platform.linkedin.com/in.js";
			ref.parentNode.insertBefore(js, ref);
		}
	}(document));

})(jQuery);