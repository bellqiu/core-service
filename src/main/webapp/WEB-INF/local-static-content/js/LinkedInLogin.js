(function($){
	$(document).ready(function() {
		var loginFlag = false;
		onLinkedInLoad = function onLinkedInLoad() {
		    IN.Event.on(IN, "auth", onLinkedInAuth);
			$("#linkedInLogin").click(function(){
				if(IN.User.isAuthorized()) {
					IN.User.logout(function(){
						IN.UI.Authorize().place();
					});
				} else {
					IN.UI.Authorize().params({immediate: false}).place();
				}
			});
		}
		function onLinkedInAuth() {
			window.location.href=window.location.protocol+"//"+window.location.host + "/ac/login?type=linkedIn&token=" + IN.ENV.auth.oauth_token;
		}
		
	    $.getScript("http://platform.linkedin.com/in.js?async=true", function success() {
	        IN.init({
	            onLoad: "onLinkedInLoad",
	            api_key: "7530oj8n5n1fan",
	            authorize: false
	        });
	    });
	});
	
})(jQuery);